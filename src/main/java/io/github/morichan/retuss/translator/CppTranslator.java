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



        //コンポジションｎ￥の変換
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
     *     実装自体はほぼ {@link JavaTranslator#searchGeneralizationClass(List)} と同じです。
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
