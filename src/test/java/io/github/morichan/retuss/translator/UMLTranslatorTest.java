package io.github.morichan.retuss.translator;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.multiplicity.Bounder;
import io.github.morichan.fescue.feature.multiplicity.MultiplicityRange;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.feature.parameter.Parameter;
import io.github.morichan.fescue.feature.value.DefaultValue;
import io.github.morichan.fescue.feature.value.expression.OneIdentifier;
import io.github.morichan.fescue.feature.visibility.Visibility;
import io.github.morichan.retuss.language.java.*;
import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.language.uml.Package;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class UMLTranslatorTest {

    UMLTranslator obj;

    @Nested
    class Javaからクラス図に変換する場合 {

        @Nested
        class クラスが1つの際に {

            @BeforeEach
            void setup() {
                obj = new UMLTranslator();
            }

            @Test
            void クラス名を持つパッケージを返す() {
                Package expected = new Package();
                expected.addClass(new Class("JavaClassName"));

                Java java = new Java();
                java.addClass(new io.github.morichan.retuss.language.java.Class("JavaClassName"));

                Package actual = obj.translate(java);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void クラス名と継承クラス名を持つパッケージを返す() {
                Package expected = new Package();
                Class classClass = new Class("ClassName");
                Class generalizationClassClass = new Class("GeneralizationClass");
                classClass.setGeneralizationClass(generalizationClassClass);
                expected.addClass(classClass);
                expected.addClass(generalizationClassClass);

                Java java = new Java();
                io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                io.github.morichan.retuss.language.java.Class generalizationJavaClass = new io.github.morichan.retuss.language.java.Class("GeneralizationClass");
                javaClass.setExtendsClass(generalizationJavaClass);
                java.addClass(javaClass);
                java.addClass(generalizationJavaClass);

                Package actual = obj.translate(java);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Nested
            class 属性について {

                @BeforeEach
                void setup() {
                    obj = new UMLTranslator();
                }

                @Test
                void クラス名とフィールドを1つ持つJavaコードを返す() {
                    Package expected = new Package();
                    Class classClass = new Class("ClassName");
                    Attribute attribute = new Attribute(new Name("number"));
                    attribute.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                    attribute.setVisibility(Visibility.Private);
                    classClass.addAttribute(attribute);
                    expected.addClass(classClass);

                    Java java = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    javaClass.addField(new Field(new Type("int"), "number"));
                    java.addClass(javaClass);

                    Package actual = obj.translate(java);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void クラス名とフィールドを3つ持つJavaコードを返す() {
                    Package expected = new Package();
                    Class classClass = new Class("ClassName");
                    Attribute attribute1 = new Attribute(new Name("number"));
                    attribute1.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                    attribute1.setVisibility(Visibility.Private);
                    attribute1.setMultiplicityRange(new MultiplicityRange(new Bounder(new OneIdentifier(6))));
                    Attribute attribute2 = new Attribute(new Name("x"));
                    attribute2.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                    attribute2.setVisibility(Visibility.Private);
                    Attribute attribute3 = new Attribute(new Name("pi"));
                    attribute3.setType(new io.github.morichan.fescue.feature.type.Type("float"));
                    attribute3.setDefaultValue(new DefaultValue(new OneIdentifier("3.1415926535")));
                    attribute3.setVisibility(Visibility.Private);
                    classClass.addAttribute(attribute1);
                    classClass.addAttribute(attribute2);
                    classClass.addAttribute(attribute3);
                    expected.addClass(classClass);

                    Java java = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    Field field1 = new Field(new Type("int"), "number");
                    field1.setArrayLength(new ArrayLength(6));
                    javaClass.addField(field1);
                    javaClass.addField(new Field(new Type("double"), "x"));
                    Field field = new Field(new Type("float"), "pi");
                    field.setValue("3.1415926535");
                    javaClass.addField(field);
                    java.addClass(javaClass);

                    Package actual = obj.translate(java);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 属性の可視性からフィールドのアクセス修飾子に変換する() {
                    Package expected = new Package();
                    Class classClass = new Class("ClassName");
                    Attribute publicAttribute = new Attribute(new Name("publicField"));
                    Attribute protectedAttribute = new Attribute(new Name("protectedField"));
                    Attribute packageAttribute = new Attribute(new Name("packageField"));
                    Attribute privateAttribute = new Attribute(new Name("privateField"));
                    publicAttribute.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                    protectedAttribute.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                    packageAttribute.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                    privateAttribute.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                    publicAttribute.setVisibility(Visibility.Public);
                    protectedAttribute.setVisibility(Visibility.Protected);
                    packageAttribute.setVisibility(Visibility.Package);
                    privateAttribute.setVisibility(Visibility.Private);
                    classClass.setAttributes(Arrays.asList(publicAttribute, protectedAttribute, packageAttribute, privateAttribute));
                    expected.addClass(classClass);

                    Java java = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    Field publicField = new Field(new Type("int"), "publicField");
                    Field protectedField = new Field(new Type("int"), "protectedField");
                    Field packageField = new Field(new Type("int"), "packageField");
                    Field privateField = new Field(new Type("int"), "privateField");
                    publicField.setAccessModifier(AccessModifier.Public);
                    protectedField.setAccessModifier(AccessModifier.Protected);
                    packageField.setAccessModifier(AccessModifier.Package);
                    privateField.setAccessModifier(AccessModifier.Private);
                    javaClass.setFields(Arrays.asList(publicField, protectedField, packageField, privateField));
                    java.addClass(javaClass);

                    Package actual = obj.translate(java);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }
            }

            @Nested
            class メソッドについて {

                @BeforeEach
                void setup() {
                    obj = new UMLTranslator();
                }

                @Test
                void クラス名とメソッドを1つ持つJavaコードを返す() {
                    Package expected = new Package();
                    Class classClass = new Class("ClassName");
                    Operation operation = new Operation(new Name("print"));
                    operation.setReturnType(new io.github.morichan.fescue.feature.type.Type("void"));
                    operation.setVisibility(Visibility.Public);
                    classClass.addOperation(operation);
                    expected.addClass(classClass);

                    Java java = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    javaClass.addMethod(new Method(new Type("void"), "print"));
                    java.addClass(javaClass);

                    Package actual = obj.translate(java);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void クラス名とメソッドを3つ持つJavaコードを返す() {
                    Package expected = new Package();
                    Class classClass = new Class("ClassName");
                    Operation operation1 = new Operation(new Name("print"));
                    operation1.setReturnType(new io.github.morichan.fescue.feature.type.Type("void"));
                    operation1.setVisibility(Visibility.Public);
                    Operation operation2 = new Operation(new Name("isTrue"));
                    operation2.setReturnType(new io.github.morichan.fescue.feature.type.Type("boolean"));
                    operation2.setVisibility(Visibility.Public);
                    Operation operation3 = new Operation(new Name("calculate"));
                    operation3.setReturnType(new io.github.morichan.fescue.feature.type.Type("double"));
                    operation3.setVisibility(Visibility.Public);
                    Parameter param1 = new Parameter(new Name("x"));
                    param1.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                    Parameter param2 = new Parameter(new Name("y"));
                    param2.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                    operation3.addParameter(param1);
                    operation3.addParameter(param2);
                    classClass.addOperation(operation1);
                    classClass.addOperation(operation2);
                    classClass.addOperation(operation3);
                    expected.addClass(classClass);

                    Java java = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    javaClass.addMethod(new Method(new Type("void"), "print"));
                    javaClass.addMethod(new Method(new Type("boolean"), "isTrue"));
                    Method method = new Method(new Type("double"), "calculate");
                    method.setArguments(Arrays.asList(new Argument(new Type("double"), "x"), new Argument(new Type("double"), "y")));
                    javaClass.addMethod(method);
                    java.addClass(javaClass);

                    Package actual = obj.translate(java);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 操作の可視性からメソッドのアクセス修飾子に変換する() {
                    Package expected = new Package();
                    Class classClass = new Class("ClassName");
                    Operation publicOperation = new Operation(new Name("publicMethod"));
                    Operation protectedOperation = new Operation(new Name("protectedMethod"));
                    Operation packageOperation = new Operation(new Name("packageMethod"));
                    Operation privateOperation = new Operation(new Name("privateMethod"));
                    publicOperation.setReturnType(new io.github.morichan.fescue.feature.type.Type("int"));
                    protectedOperation.setReturnType(new io.github.morichan.fescue.feature.type.Type("int"));
                    packageOperation.setReturnType(new io.github.morichan.fescue.feature.type.Type("int"));
                    privateOperation.setReturnType(new io.github.morichan.fescue.feature.type.Type("int"));
                    publicOperation.setVisibility(Visibility.Public);
                    protectedOperation.setVisibility(Visibility.Protected);
                    packageOperation.setVisibility(Visibility.Package);
                    privateOperation.setVisibility(Visibility.Private);
                    classClass.setOperations(Arrays.asList(publicOperation, protectedOperation, packageOperation, privateOperation));
                    expected.addClass(classClass);

                    Java java = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    Method publicMethod = new Method(new Type("int"), "publicMethod");
                    Method protectedMethod = new Method(new Type("int"), "protectedMethod");
                    Method packageMethod = new Method(new Type("int"), "packageMethod");
                    Method privateMethod = new Method(new Type("int"), "privateMethod");
                    publicMethod.setAccessModifier(AccessModifier.Public);
                    protectedMethod.setAccessModifier(AccessModifier.Protected);
                    packageMethod.setAccessModifier(AccessModifier.Package);
                    privateMethod.setAccessModifier(AccessModifier.Private);
                    javaClass.setMethod(Arrays.asList(publicMethod, protectedMethod, packageMethod, privateMethod));
                    java.addClass(javaClass);

                    Package actual = obj.translate(java);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }
            }

            @Test
            void クラス名とフィールド2つメソッド2つ持つJavaコードを返す() {
                Package expected = new Package();
                Class classClass = new Class("ClassName");
                Attribute attribute1 = new Attribute(new Name("number"));
                attribute1.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                attribute1.setVisibility(Visibility.Private);
                Attribute attribute2 = new Attribute(new Name("pi"));
                attribute2.setType(new io.github.morichan.fescue.feature.type.Type("float"));
                attribute2.setDefaultValue(new DefaultValue(new OneIdentifier("3.1415926535")));
                attribute2.setVisibility(Visibility.Private);
                classClass.addAttribute(attribute1);
                classClass.addAttribute(attribute2);
                Operation operation1 = new Operation(new Name("isTrue"));
                operation1.setReturnType(new io.github.morichan.fescue.feature.type.Type("boolean"));
                operation1.setVisibility(Visibility.Public);
                Operation operation2 = new Operation(new Name("calculate"));
                operation2.setReturnType(new io.github.morichan.fescue.feature.type.Type("double"));
                operation2.setVisibility(Visibility.Public);
                Parameter param1 = new Parameter(new Name("x"));
                param1.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                Parameter param2 = new Parameter(new Name("y"));
                param2.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                Parameter param3 = new Parameter(new Name("z"));
                param3.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                operation2.addParameter(param1);
                operation2.addParameter(param2);
                operation2.addParameter(param3);
                classClass.addOperation(operation1);
                classClass.addOperation(operation2);
                expected.addClass(classClass);

                Java java = new Java();
                io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                javaClass.addField(new Field(new Type("int"), "number"));
                Field field = new Field(new Type("float"), "pi");
                field.setValue("3.1415926535");
                javaClass.addField(field);
                javaClass.addMethod(new Method(new Type("boolean"), "isTrue"));
                Method method = new Method(new Type("double"), "calculate");
                method.setArguments(Arrays.asList(
                        new Argument(new Type("double"), "x"),
                        new Argument(new Type("double"), "y"),
                        new Argument(new Type("double"), "z")));
                javaClass.addMethod(method);
                java.addClass(javaClass);

                Package actual = obj.translate(java);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }

        @Nested
        class クラスが複数の際に {

            @BeforeEach
            void setup() {
                obj = new UMLTranslator();
            }

            @Test
            void クラス名を持つJavaコードを返す() {
                Package expected = new Package();
                expected.addClass(new Class("ClassName1"));
                expected.addClass(new Class("ClassName2"));
                expected.addClass(new Class("ClassName3"));

                Java java = new Java();
                java.addClass(new io.github.morichan.retuss.language.java.Class("ClassName1"));
                java.addClass(new io.github.morichan.retuss.language.java.Class("ClassName2"));
                java.addClass(new io.github.morichan.retuss.language.java.Class("ClassName3"));

                Package actual = obj.translate(java);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 継承関係にあたる複数のクラスを持つJavaコードを返す() {
                Package expected = new Package();
                Class classClass1 = new Class("ClassName1");
                Class classClass2 = new Class("ClassName2");
                Class classClass3 = new Class("ClassName3");
                classClass1.setGeneralizationClass(classClass2);
                classClass2.setGeneralizationClass(classClass3);
                expected.addClass(classClass1);
                expected.addClass(classClass2);
                expected.addClass(classClass3);

                Java java = new Java();
                io.github.morichan.retuss.language.java.Class javaClass1 = new io.github.morichan.retuss.language.java.Class("ClassName1");
                io.github.morichan.retuss.language.java.Class javaClass2 = new io.github.morichan.retuss.language.java.Class("ClassName2");
                io.github.morichan.retuss.language.java.Class javaClass3 = new io.github.morichan.retuss.language.java.Class("ClassName3");
                javaClass1.setExtendsClass(javaClass2);
                javaClass2.setExtendsClass(javaClass3);
                java.addClass(javaClass1);
                java.addClass(javaClass2);
                java.addClass(javaClass3);

                Package actual = obj.translate(java);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 継承関係にあたる複数のクラスと関係ないクラスを持つJavaコードを返す() {
                Package expected = new Package();
                Class carClass = new Class("Car");
                Class priusClass = new Class("Prius");
                Class crownClass = new Class("Crown");
                Class tireClass = new Class("Tire");
                Attribute tireAttribute = new Attribute(new Name(tireClass.getName()));
                tireAttribute.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                tireAttribute.setVisibility(Visibility.Private);
                carClass.addAttribute(tireAttribute);
                priusClass.setGeneralizationClass(carClass);
                crownClass.setGeneralizationClass(carClass);
                expected.addClass(carClass);
                expected.addClass(priusClass);
                expected.addClass(crownClass);
                expected.addClass(tireClass);

                Java java = new Java();
                io.github.morichan.retuss.language.java.Class car = new io.github.morichan.retuss.language.java.Class("Car");
                io.github.morichan.retuss.language.java.Class prius = new io.github.morichan.retuss.language.java.Class("Prius");
                io.github.morichan.retuss.language.java.Class crown = new io.github.morichan.retuss.language.java.Class("Crown");
                io.github.morichan.retuss.language.java.Class tire = new io.github.morichan.retuss.language.java.Class("Tire");
                car.addField(new Field(new Type("int"), tire.getName()));
                prius.setExtendsClass(car);
                crown.setExtendsClass(car);
                java.addClass(car);
                java.addClass(prius);
                java.addClass(crown);
                java.addClass(tire);

                Package actual = obj.translate(java);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }
    }
}