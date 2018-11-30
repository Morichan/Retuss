package io.github.morichan.retuss.translator;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.parameter.Parameter;
import io.github.morichan.fescue.feature.visibility.Visibility;
import io.github.morichan.retuss.language.java.*;
import io.github.morichan.retuss.language.java.Class;
import io.github.morichan.retuss.language.uml.Package;
import io.github.morichan.retuss.window.diagram.OperationGraphic;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> Java翻訳者クラス </p>
 */
public class JavaTranslator {

    private Java java;

    /**
     * <p> クラス図のパッケージからJavaに翻訳します </p>
     *
     * @param classPackage クラス図のクラスリスト
     * @return Javaソースコード
     */
    public Java translate(Package classPackage) {
        java = new Java();

        for (io.github.morichan.retuss.language.uml.Class cc : classPackage.getClasses()) {
            java.addClass(createJavaClass(cc));
        }

        searchGeneralizationClass(classPackage.getClasses());

        return java;
    }

    private Class createJavaClass(io.github.morichan.retuss.language.uml.Class classClass) {
        Class javaClass = new Class(classClass.getName());

        for (Attribute attribute : classClass.extractAttributes()) {
            Field field = new Field(new Type(attribute.getType().toString()), attribute.getName().toString());
            try {
                field.setAccessModifier(convert(attribute.getVisibility()));
            } catch (IllegalStateException e) {
                field.setAccessModifier(AccessModifier.Private);
            }
            try {
                field.setValue(attribute.getDefaultValue().toString());
            } catch (IllegalStateException e) {
                field.setValue(null);
            }
            javaClass.addField(field);
        }

        for (Attribute relation : classClass.getRelations()) {
            Field field = new Field(new Type(relation.getType().toString()), relation.getName().toString());
            try {
                field.setAccessModifier(convert(relation.getVisibility()));
            } catch (IllegalStateException e) {
                field.setAccessModifier(AccessModifier.Private);
            }
            field.setValue("new " + relation.getType().toString());
            javaClass.addField(field);
        }

        for (OperationGraphic operationGraphic : classClass.getOperationGraphics()) {
            Operation operation = operationGraphic.getOperation();
            Method method = new Method(new Type(operation.getReturnType().toString()), operation.getName().toString());
            if (operationGraphic.isAbstract()) {
                method.setAbstract(true);
                javaClass.setAbstract(true);
            }
            try {
                method.setAccessModifier(convert(operation.getVisibility()));
            } catch (IllegalStateException e) {
                method.setAccessModifier(AccessModifier.Public);
            }
            try {
                for (Parameter param : operation.getParameters()) {
                    method.addArgument(new Argument(new Type(param.getType().toString()), param.getName().toString()));
                }
            } catch (IllegalStateException e) {
                method.emptyArguments();
            }
            javaClass.addMethod(method);
        }

        return javaClass;
    }

    private AccessModifier convert(Visibility visibility) {
        if (visibility == Visibility.Public) {
            return AccessModifier.Public;
        } else if (visibility == Visibility.Protected) {
            return AccessModifier.Protected;
        } else if (visibility == Visibility.Package) {
            return AccessModifier.Package;
        } else {
            return AccessModifier.Private;
        }
    }

    /**
     * <p> 汎化関係にあたるクラスを {@link #java} から探して {@link #java} の各クラスに格納します </p>
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
     *     {@link #java} にそれぞれクラスA、クラスB、クラスCが継承関係なしの状態で格納されています。
     * </p>
     *
     * <pre>
     *     {@code
     *     {"java":
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
     *     そこで、クラスBという名前と一致する {@link #java} 内におけるクラスBを抽出します
     *     （これを {@code java.getClasses.stream().filter(...).collection(Collectors.toList())} によるラムダ式で表現しています）。
     *     名前が一致するクラスBは唯一のはずですから、そのまま {@code oneGeneralizationJavaClass} リストに存在する0番目の要素を抽出し、
     *     {@link #java} のクラスAの継承クラスに格納します。
     *     参照渡しのため、{@code java.A.extends == java.B} です。
     * </p>
     *
     * <pre>
     *     {@code
     *     {"java":
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
     *     そこで、クラスCという名前と一致する {@link #java} 内におけるクラスCを抽出します（同上）。
     *     名前が一致するクラスCもやはり唯一のはずですから、そのまま {@code oneGeneralizationJavaClass} リストに存在する0番目の要素を抽出し、
     *     {@link #java} のクラスBの継承クラスに格納します。
     *     参照渡しのため、 {@code java.B.extends == java.C} です。
     * </p>
     *
     * <pre>
     *     {@code
     *     {"java":
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
     *     さて、どちらも参照渡しですから、 {@code java.B.extends} に {@code java.C} の参照が入った瞬間、なんと {@code java.A.extends.B.extends} にも参照渡しが入ります。
     * </p>
     *
     * <pre>
     *     {@code
     *     {"java":
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
                List<io.github.morichan.retuss.language.java.Class> oneGeneralizationJavaClass =
                        java.getClasses().stream().filter(
                                jc -> jc.getName().equals(classClasses.get(finalI).getGeneralizationClass().getName())
                        ).collect(Collectors.toList());
                java.getClasses().get(finalI).setExtendsClass(oneGeneralizationJavaClass.get(0));
            }
        }
    }
}
