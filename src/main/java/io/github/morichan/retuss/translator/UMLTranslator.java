package io.github.morichan.retuss.translator;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.multiplicity.MultiplicityRange;
import io.github.morichan.fescue.feature.multiplicity.Bounder;
import io.github.morichan.fescue.feature.value.expression.Expression;
import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.feature.parameter.Parameter;
import io.github.morichan.fescue.feature.type.Type;
import io.github.morichan.fescue.feature.value.DefaultValue;
import io.github.morichan.fescue.feature.value.expression.OneIdentifier;
import io.github.morichan.fescue.feature.visibility.Visibility;
import io.github.morichan.retuss.language.java.*;
import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.language.uml.Package;

import  io.github.morichan.retuss.language.cpp.Cpp;
import  io.github.morichan.retuss.language.cpp.MemberVariable;
import  io.github.morichan.retuss.language.cpp.MemberFunction;
import  io.github.morichan.retuss.language.cpp.AccessSpecifier;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> UML翻訳者クラス </p>
 */
public class UMLTranslator {

    private Package classPackage;

    /**
     * <p> Javaからクラス図のパッケージに翻訳します </p>
     *
     * @param java Javaソースコード
     * @return クラス図のパッケージ
     */
    public Package translate(Java java) {
        classPackage = new Package();

        for (io.github.morichan.retuss.language.java.Class javaClass : java.getClasses()) {
            classPackage.addClass(createClass(javaClass));
        }

        searchGeneralizationClass(java.getClasses());

        return classPackage;
    }

    /**
     * <p> Cppからクラス図のパッケージに翻訳します </p>
     *
     * @param cpp C++ソースコード
     * @return クラス図のパッケージ
     */
    public Package translate(Cpp cpp) {
      //  List<Boolean> flagOperationImplementations = classPackage.getClasses().get(0).getFlagOperationsImplementations();
        classPackage = new Package();

        for (io.github.morichan.retuss.language.cpp.Class cppClass : cpp.getClasses()) {
            classPackage.addClass(createClass(cppClass));
        }

        searchGeneralizationClass_Cpp(cpp.getClasses());

        return classPackage;
    }

    private Class createClass(io.github.morichan.retuss.language.java.Class javaClass) {
        Class classClass = new Class(javaClass.getName());

        for (Field field : javaClass.getFields()) {
            Attribute attribute = new Attribute(new Name(field.getName()));
            attribute.setType(new Type(field.getType().toString()));
            attribute.setVisibility(convert(field.getAccessModifier()));
            if (field.getValue() != null) {
                attribute.setDefaultValue(new DefaultValue(new OneIdentifier(field.getValue().toString())));
            }
            classClass.addAttribute(attribute);
        }
        for (Method method : javaClass.getMethods()) {
            Operation operation = new Operation(new Name(method.getName()));
            operation.setReturnType(new Type(method.getType().toString()));
            operation.setVisibility(convert(method.getAccessModifier()));
            for (Argument argument : method.getArguments()) {
                Parameter parameter = new Parameter(new Name(argument.getName()));
                parameter.setType(new Type(argument.getType().toString()));
                operation.addParameter(parameter);
            }
            classClass.addOperation(operation, method.isAbstract());
        }

        return classClass;
    }

    private Visibility convert(AccessModifier accessModifier) {
        if (accessModifier == AccessModifier.Public) {
            return Visibility.Public;
        } else if (accessModifier == AccessModifier.Protected) {
            return Visibility.Protected;
        } else if (accessModifier == AccessModifier.Package) {
            return Visibility.Package;
        } else {
            return Visibility.Private;
        }
    }

    /**
     * <p> 汎化関係のクラスをディープコピーします </p>
     *
     * <p>
     *     手法について詳しくは {@link JavaTranslator#searchGeneralizationClass(List)} を参照してください。
     * </p>
     *
     * @param javaClasses Javaのクラスリスト
     */
    private void searchGeneralizationClass(List<io.github.morichan.retuss.language.java.Class> javaClasses) {
        for (int i = 0; i < javaClasses.size(); i++) {
            if (javaClasses.get(i).getExtendsClassName() != null) {
                int finalI = i;
                List<io.github.morichan.retuss.language.uml.Class> oneExtendsClass =
                        classPackage.getClasses().stream().filter(
                                cp -> cp.getName().equals(javaClasses.get(finalI).getExtendsClassName())
                        ).collect(Collectors.toList());
                try {
                    io.github.morichan.retuss.language.uml.Class oneClass = oneExtendsClass.get(0);
                    classPackage.getClasses().get(finalI).setGeneralizationClass(oneClass);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("This is Set Error because same class wasn't had, so don't set.");
                }
            }
        }
    }

    //C++の変換

    private Class createClass(io.github.morichan.retuss.language.cpp.Class cppClass) {
        Class classClass = new Class(cppClass.getName());

        for (MemberVariable memberVariable : cppClass.getMemberVariables()) {

            Attribute attribute = new Attribute(new Name(memberVariable.getName()));
            if(memberVariable.getConstantExpression() != null) {
                MultiplicityRange multiplicityRange = new MultiplicityRange(new Bounder(memberVariable.getConstantExpression()));
                attribute.setMultiplicityRange(multiplicityRange);
            }
            if(memberVariable.getType().toString().equals( "string")) {
                attribute.setType(new Type("String"));
            }else {
                attribute.setType(new Type(memberVariable.getType().toString()));
            }
            attribute.setVisibility(convert(memberVariable.getAccessSpecifier()));
            if (memberVariable.getValue() != null) {
                attribute.setDefaultValue(new DefaultValue(new OneIdentifier(memberVariable.getValue().toString())));
            }

            classClass.addAttribute(attribute);
        }
        for (MemberFunction memberFunction : cppClass.getMemberFunctions()) {
            Operation operation = new Operation(new Name(memberFunction.getName()));
            operation.setReturnType(new Type(memberFunction.getType().toString()));
            operation.setVisibility(convert(memberFunction.getAccessSpecifier()));
            for (io.github.morichan.retuss.language.cpp.Argument argument : memberFunction.getArguments()) {
                Parameter parameter = new Parameter(new Name(argument.getName()));
                parameter.setType(new Type(argument.getType().toString()));
                operation.addParameter(parameter);
            }
           // Boolean flagOperationsImplementation = memberFunction.getFlagImplementation();
            Boolean flagOperationsImplementation = Boolean.valueOf(memberFunction.getFlagImplementation());
           // classClass.addFlagOperationsImplementation(flagOperationsImplementation);
            classClass.addOperation(operation,memberFunction.isAbstract());
        }

        return classClass;
    }


    private Visibility convert(AccessSpecifier accessSpecifier) {
        if (accessSpecifier == AccessSpecifier.Public) {
            return Visibility.Public;
        } else if (accessSpecifier == AccessSpecifier.Protected) {
            return Visibility.Protected;
        }
//        else if (accessSpecifier == AccessSpecifier.Package) {
//            return Visibility.Package;
//        }
        else {
            return Visibility.Private;
        }
    }

    /**
     * <p> 汎化関係のクラスをディープコピーします </p>
     *
     * <p>
     *     手法について詳しくは {@link CppTranslator#searchGeneralizationClass(List)} を参照してください。
     * </p>
     *
     * @param cppClasses Cppのクラスリスト
     */
    private void searchGeneralizationClass_Cpp(List<io.github.morichan.retuss.language.cpp.Class> cppClasses) {
        for (int i = 0; i < cppClasses.size(); i++) {
            if (cppClasses.get(i).getExtendsClassName() != null) {
                int finalI = i;
                List<io.github.morichan.retuss.language.uml.Class> oneExtendsClass =
                        classPackage.getClasses().stream().filter(
                                cp -> cp.getName().equals(cppClasses.get(finalI).getExtendsClassName())
                        ).collect(Collectors.toList());
                try {
                    io.github.morichan.retuss.language.uml.Class oneClass = oneExtendsClass.get(0);
                    classPackage.getClasses().get(finalI).setGeneralizationClass(oneClass);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("This is Set Error because same class wasn't had, so don't set.");
                }
            }
        }
    }
}
