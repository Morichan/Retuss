package io.github.morichan.retuss.translator;

import io.github.morichan.fescue.feature.Attribute;
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
            classClass.addOperation(operation);
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
                                cp -> cp.getName().equals(javaClasses.get(finalI).getExtendsClassName().toString())
                        ).collect(Collectors.toList());
                classPackage.getClasses().get(finalI).setGeneralizationClass(oneExtendsClass.get(0));
            }
        }
    }
}
