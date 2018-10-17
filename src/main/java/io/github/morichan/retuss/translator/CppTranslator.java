package io.github.morichan.retuss.translator;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.parameter.Parameter;
import io.github.morichan.fescue.feature.visibility.Visibility;
import io.github.morichan.retuss.language.cpp.*;
import io.github.morichan.retuss.language.cpp.Class;
import io.github.morichan.retuss.language.uml.Package;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> Cpp翻訳者クラス </p>
 */
public class CppTranslator {

    private Cpp cpp;

    /**
     * <p> クラス図のパッケージからCppに翻訳します </p>
     *
     * @param classPackage クラス図のクラスリスト
     * @return Cppソースコード
     */
    public Cpp translate(Package classPackage) {
        cpp = new Cpp();

        for (io.github.morichan.retuss.language.uml.Class cc : classPackage.getClasses()) {
            cpp.addClass(createCppClass(cc));
        }

        searchGeneralizationClass(classPackage.getClasses());

        return cpp;
    }

    private Class createCppClass(io.github.morichan.retuss.language.uml.Class classClass) {
        Class cppClass = new Class(classClass.getName());

        for (Attribute attribute : classClass.getAttributes()) {
            MemberVariable memberVariable = new MemberVariable(new Type(attribute.getType().toString()), attribute.getName().toString());
            try {
                memberVariable.setAccessSpecifier(convert(attribute.getVisibility()));
            } catch (IllegalStateException e) {
                memberVariable.setAccessSpecifier(AccessSpecifier.Private);
            }
            try {
                memberVariable.setValue(attribute.getDefaultValue().toString());
            } catch (IllegalStateException e) {
                memberVariable.setValue(null);
            }
            cppClass.addMemberVariable(memberVariable);
        }

        for (Operation operation : classClass.getOperations()) {
            MemberFunction memberFunction = new MemberFunction(new Type(operation.getReturnType().toString()), operation.getName().toString());
            try {
                memberFunction.setAccessSpecifier(convert(operation.getVisibility()));
            } catch (IllegalStateException e) {
                memberFunction.setAccessSpecifier(AccessSpecifier.Public);
            }
            try {
                for (Parameter param : operation.getParameters()) {
                    memberFunction.addArgument(new Argument(new Type(param.getType().toString()), param.getName().toString()));
                }
            } catch (IllegalStateException e) {
                memberFunction.emptyArguments();
            }
            cppClass.addMemberFunction(memberFunction);
        }



/**
 * コンポジションｎ￥の変換
 *
 */
        for (Attribute relation : classClass.getRelations()) {
            MemberVariable memberVariable = new MemberVariable(new Type(relation.getType().toString()), relation.getName().toString());
            try {
                memberVariable.setAccessSpecifier(convert(relation.getVisibility()));
            } catch (IllegalStateException e) {
                memberVariable.setAccessSpecifier(AccessSpecifier.Private);
            }
            memberVariable.setValue("new " + relation.getType().toString());
            cppClass.addMemberVariable(memberVariable);
        }

        return cppClass;
    }

    private AccessSpecifier convert(Visibility visibility) {
        if (visibility == Visibility.Public) {
            return AccessSpecifier.Public;
        } else if (visibility == Visibility.Protected) {
            return AccessSpecifier.Protected;
        } else if (visibility == Visibility.Package) {
            return AccessSpecifier.Package;
        } else {
            return AccessSpecifier.Private;
        }
    }

    /**
     * <p> 汎化関係にあたるクラスを {@link #cpp} から探して {@link #cpp} の各クラスに格納します </p>
     *
     * <p>
     *     {@link #translate(Package)} 内の最後で用いています。
     * </p>
     *
     * <p>
     *     例えば、 {@code A -|> B -|> C} のような関係になっているとします。
     *     なお、 {@code -|>} は汎化関係にあたります。
     * </p>
     *
     * <p>
     *     このメソッドを利用する前には {@link #translate(Package)} 内で、
     *     {@link #cpp} にそれぞれクラスA、クラスB、クラスCが継承関係なしの状態で格納されています。
     * </p>
     *
     * <pre>
     *     {@code
     *     {"cpp":
     *         [
     *             {"A": {"extends": null}},
     *             {"B": {"extends": null}},
     *             {"C": {"extends": null}}
     *         ]
     *     }
     *     }
     * </pre>
     *
     * <p>
     *     まず、クラスAを見ると、クラスBが汎化関係にあることが {@code classClasses} からわかります。
     *     そこで、クラスBという名前と一致する {@link #cpp} 内におけるクラスBを抽出します
     *     （これを {@code cpp.getClasses.stream().filter(...).collection(Collectors.toList())} によるラムダ式で表現しています）。
     *     名前が一致するクラスBは唯一のはずですから、そのまま {@code oneGeneralizationCppClass} リストに存在する0番目の要素を抽出し、
     *     {@link #cpp} のクラスAの継承クラスに格納します。
     *     参照渡しのため、{@code cpp.A.extends == cpp.B} です。
     * </p>
     *
     * <pre>
     *     {@code
     *     {"cpp":
     *         [
     *             {"A": {"extends": {"B": {"extends": null}}}},
     *             {"B": {"extends": null}},
     *             {"C": {"extends": null}}
     *         ]
     *     }
     *     }
     * </pre>
     *
     * <p>
     *     次に、クラスBを見ると、クラスCが汎化関係にあることが {@code classClasses} からわかります。
     *     そこで、クラスCという名前と一致する {@link #cpp} 内におけるクラスCを抽出します（同上）。
     *     名前が一致するクラスCもやはり唯一のはずですから、そのまま {@code oneGeneralizationCppClass} リストに存在する0番目の要素を抽出し、
     *     {@link #cpp} のクラスBの継承クラスに格納します。
     *     参照渡しのため、 {@code cpp.B.extends == cpp.C} です。
     * </p>
     *
     * <pre>
     *     {@code
     *     {"cpp":
     *         [
     *             {"A": {"extends": {"B": {"extends": null}}}},
     *             {"B": {"extends": {"C": {"extends": null}}}},
     *             {"C": {"extends": null}}
     *         ]
     *     }
     *     }
     * </pre>
     *
     * <p>
     *     さて、どちらも参照渡しですから、 {@code cpp.B.extends} に {@code cpp.C} の参照が入った瞬間、なんと {@code cpp.A.extends.B.extends} にも参照渡しが入ります。
     * </p>
     *
     * <pre>
     *     {@code
     *     {"cpp":
     *         [
     *             {"A": {"extends": {"B": {"extends": {"C": {"extends": null}}}}}},
     *             {"B": {"extends": {"C": {"extends": null}}}},
     *             {"C": {"extends": null}}
     *         ]
     *     }
     *     }
     * </pre>
     *
     * <p>
     *     これにより、どのクラスから見ても継承関係がわかるような構造になりました。
     *     この手法は、どのクラスがどのような順序で格納していたとしても上手く処理できます。
     * </p>
     *
     * @param classClasses クラス図のクラスのリスト
     */
    private void searchGeneralizationClass(List<io.github.morichan.retuss.language.uml.Class> classClasses) {
        for (int i = 0; i < classClasses.size(); i++) {
            if (classClasses.get(i).getGeneralizationClass() != null) {
                int finalI = i;
                List<Class> oneGeneralizationCppClass =
                        cpp.getClasses().stream().filter(
                                jc -> jc.getName().equals(classClasses.get(finalI).getGeneralizationClass().getName())
                        ).collect(Collectors.toList());
                cpp.getClasses().get(finalI).setExtendsClass(oneGeneralizationCppClass.get(0));
            }
        }
    }
}
