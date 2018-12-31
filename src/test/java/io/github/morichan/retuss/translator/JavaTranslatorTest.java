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
import io.github.morichan.retuss.window.diagram.OperationGraphic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class JavaTranslatorTest {

    JavaTranslator obj;

    @Nested
    class クラス図からJavaへ変換する場合 {

        @Nested
        class クラスが1つの際に {

            @BeforeEach
            void setup() {
                obj = new JavaTranslator();
            }

            @Test
            void クラス名を持つJavaコードを返す() {
                Java expected = new Java();
                expected.addClass(new io.github.morichan.retuss.language.java.Class("ClassName"));

                Package classPackage = new Package();
                classPackage.addClass(new Class("ClassName"));

                Java actual = obj.translate(classPackage);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void クラス名と継承クラス名を持つJavaコードを返す() {
                Java expected = new Java();
                io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                io.github.morichan.retuss.language.java.Class generalizationJavaClass = new io.github.morichan.retuss.language.java.Class("GeneralizationClass");
                javaClass.setExtendsClass(generalizationJavaClass);
                expected.addClass(javaClass);
                expected.addClass(generalizationJavaClass);

                Package classPackage = new Package();
                Class classClass = new Class("ClassName");
                Class generalizationClassClass = new Class("GeneralizationClass");
                classClass.setGeneralizationClass(generalizationClassClass);
                classPackage.addClass(classClass);
                classPackage.addClass(generalizationClassClass);

                Java actual = obj.translate(classPackage);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Nested
            class フィールドについて {

                @BeforeEach
                void setup() {
                    obj = new JavaTranslator();
                }

                @Test
                void クラス名とフィールドを1つ持つJavaコードを返す() {
                    Java expected = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    Field field = new Field(new Type("int"), "number");
                    field.setArrayLength(new ArrayLength(3));
                    javaClass.addField(field);
                    expected.addClass(javaClass);

                    Package classPackage = new Package();
                    Class classClass = new Class("ClassName");
                    Attribute attribute = new Attribute(new Name("number"));
                    attribute.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                    attribute.setMultiplicityRange(new MultiplicityRange(new Bounder(new OneIdentifier(3))));
                    classClass.addAttribute(attribute);
                    classPackage.addClass(classClass);

                    Java actual = obj.translate(classPackage);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void クラス名とフィールドを3つ持つJavaコードを返す() {
                    Java expected = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    javaClass.addField(new Field(new Type("int"), "number"));
                    javaClass.addField(new Field(new Type("double"), "x"));
                    Field field = new Field(new Type("float"), "pi");
                    field.setValue("3.1415926535");
                    javaClass.addField(field);
                    expected.addClass(javaClass);

                    Package classPackage = new Package();
                    Class classClass = new Class("ClassName");
                    Attribute attribute1 = new Attribute(new Name("number"));
                    attribute1.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                    Attribute attribute2 = new Attribute(new Name("x"));
                    attribute2.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                    Attribute attribute3 = new Attribute(new Name("pi"));
                    attribute3.setType(new io.github.morichan.fescue.feature.type.Type("float"));
                    attribute3.setDefaultValue(new DefaultValue(new OneIdentifier("3.1415926535")));
                    classClass.addAttribute(attribute1);
                    classClass.addAttribute(attribute2);
                    classClass.addAttribute(attribute3);
                    classPackage.addClass(classClass);

                    Java actual = obj.translate(classPackage);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 属性の可視性からフィールドのアクセス修飾子に変換する() {
                    Java expected = new Java();
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
                    expected.addClass(javaClass);

                    Package classPackage = new Package();
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
                    classPackage.addClass(classClass);

                    Java actual = obj.translate(classPackage);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }
            }

            @Nested
            class メソッドについて {

                @BeforeEach
                void setup() {
                    obj = new JavaTranslator();
                }

                @Test
                void クラス名とメソッドを1つ持つJavaコードを返す() {
                    Java expected = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    javaClass.addMethod(new Method(new Type("void"), "print"));
                    expected.addClass(javaClass);

                    Package classPackage = new Package();
                    Class classClass = new Class("ClassName");
                    Operation operation = new Operation(new Name("print"));
                    operation.setReturnType(new io.github.morichan.fescue.feature.type.Type("void"));
                    classClass.addOperation(new OperationGraphic(operation));
                    classPackage.addClass(classClass);

                    Java actual = obj.translate(classPackage);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void クラス名とメソッドを3つ持つJavaコードを返す() {
                    Java expected = new Java();
                    io.github.morichan.retuss.language.java.Class javaClass = new io.github.morichan.retuss.language.java.Class("ClassName");
                    javaClass.addMethod(new Method(new Type("void"), "print"));
                    javaClass.addMethod(new Method(new Type("boolean"), "isTrue"));
                    Method method = new Method(new Type("double"), "calculate");
                    method.setArguments(Arrays.asList(new Argument(new Type("double"), "x"), new Argument(new Type("double"), "y")));
                    javaClass.addMethod(method);
                    expected.addClass(javaClass);

                    Package classPackage = new Package();
                    Class classClass = new Class("ClassName");
                    Operation operation1 = new Operation(new Name("print"));
                    operation1.setReturnType(new io.github.morichan.fescue.feature.type.Type("void"));
                    Operation operation2 = new Operation(new Name("isTrue"));
                    operation2.setReturnType(new io.github.morichan.fescue.feature.type.Type("boolean"));
                    Operation operation3 = new Operation(new Name("calculate"));
                    operation3.setReturnType(new io.github.morichan.fescue.feature.type.Type("double"));
                    Parameter param1 = new Parameter(new Name("x"));
                    param1.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                    Parameter param2 = new Parameter(new Name("y"));
                    param2.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                    operation3.addParameter(param1);
                    operation3.addParameter(param2);
                    classClass.addOperation(new OperationGraphic(operation1));
                    classClass.addOperation(new OperationGraphic(operation2));
                    classClass.addOperation(new OperationGraphic(operation3));
                    classPackage.addClass(classClass);

                    Java actual = obj.translate(classPackage);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 操作の可視性からメソッドのアクセス修飾子に変換する() {
                    Java expected = new Java();
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
                    expected.addClass(javaClass);

                    Package classPackage = new Package();
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
                    classPackage.addClass(classClass);

                    Java actual = obj.translate(classPackage);

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }
            }

            @Test
            void クラス名とフィールド2つメソッド2つ持つJavaコードを返す() {
                Java expected = new Java();
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
                expected.addClass(javaClass);

                Package classPackage = new Package();
                Class classClass = new Class("ClassName");
                Attribute attribute1 = new Attribute(new Name("number"));
                attribute1.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                Attribute attribute2 = new Attribute(new Name("pi"));
                attribute2.setType(new io.github.morichan.fescue.feature.type.Type("float"));
                attribute2.setDefaultValue(new DefaultValue(new OneIdentifier("3.1415926535")));
                classClass.addAttribute(attribute1);
                classClass.addAttribute(attribute2);
                Operation operation1 = new Operation(new Name("isTrue"));
                operation1.setReturnType(new io.github.morichan.fescue.feature.type.Type("boolean"));
                Operation operation2 = new Operation(new Name("calculate"));
                operation2.setReturnType(new io.github.morichan.fescue.feature.type.Type("double"));
                Parameter param1 = new Parameter(new Name("x"));
                param1.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                Parameter param2 = new Parameter(new Name("y"));
                param2.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                Parameter param3 = new Parameter(new Name("z"));
                param3.setType(new io.github.morichan.fescue.feature.type.Type("double"));
                operation2.addParameter(param1);
                operation2.addParameter(param2);
                operation2.addParameter(param3);
                classClass.addOperation(new OperationGraphic(operation1));
                classClass.addOperation(new OperationGraphic(operation2));
                classPackage.addClass(classClass);

                Java actual = obj.translate(classPackage);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }

        @Nested
        class クラスが複数の際に {

            @BeforeEach
            void setup() {
                obj = new JavaTranslator();
            }

            @Test
            void クラス名を持つJavaコードを返す() {
                Java expected = new Java();
                expected.addClass(new io.github.morichan.retuss.language.java.Class("ClassName1"));
                expected.addClass(new io.github.morichan.retuss.language.java.Class("ClassName2"));
                expected.addClass(new io.github.morichan.retuss.language.java.Class("ClassName3"));

                Package classPackage = new Package();
                classPackage.addClass(new Class("ClassName1"));
                classPackage.addClass(new Class("ClassName2"));
                classPackage.addClass(new Class("ClassName3"));

                Java actual = obj.translate(classPackage);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 継承関係にあたる複数のクラスを持つJavaコードを返す() {
                Java expected = new Java();
                io.github.morichan.retuss.language.java.Class javaClass1 = new io.github.morichan.retuss.language.java.Class("ClassName1");
                io.github.morichan.retuss.language.java.Class javaClass2 = new io.github.morichan.retuss.language.java.Class("ClassName2");
                io.github.morichan.retuss.language.java.Class javaClass3 = new io.github.morichan.retuss.language.java.Class("ClassName3");
                javaClass1.setExtendsClass(javaClass2);
                javaClass2.setExtendsClass(javaClass3);
                expected.addClass(javaClass1);
                expected.addClass(javaClass2);
                expected.addClass(javaClass3);

                Package classPackage = new Package();
                Class classClass1 = new Class("ClassName1");
                Class classClass2 = new Class("ClassName2");
                Class classClass3 = new Class("ClassName3");
                classClass1.setGeneralizationClass(classClass2);
                classClass2.setGeneralizationClass(classClass3);
                classPackage.addClass(classClass1);
                classPackage.addClass(classClass2);
                classPackage.addClass(classClass3);

                Java actual = obj.translate(classPackage);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 継承関係にあたる複数のクラスと関係ないクラスを持つJavaコードを返す() {
                Java expected = new Java();
                io.github.morichan.retuss.language.java.Class car = new io.github.morichan.retuss.language.java.Class("Car");
                io.github.morichan.retuss.language.java.Class prius = new io.github.morichan.retuss.language.java.Class("Prius");
                io.github.morichan.retuss.language.java.Class crown = new io.github.morichan.retuss.language.java.Class("Crown");
                io.github.morichan.retuss.language.java.Class tire = new io.github.morichan.retuss.language.java.Class("Tire");
                car.addField(new Field(new Type("int"), tire.getName()));
                prius.setExtendsClass(car);
                crown.setExtendsClass(car);
                expected.addClass(car);
                expected.addClass(prius);
                expected.addClass(crown);
                expected.addClass(tire);

                Package classPackage = new Package();
                Class carClass = new Class("Car");
                Class priusClass = new Class("Prius");
                Class crownClass = new Class("Crown");
                Class tireClass = new Class("Tire");
                Attribute tireAttribute = new Attribute(new Name(tireClass.getName()));
                tireAttribute.setType(new io.github.morichan.fescue.feature.type.Type("int"));
                carClass.addAttribute(tireAttribute);
                priusClass.setGeneralizationClass(carClass);
                crownClass.setGeneralizationClass(carClass);
                classPackage.addClass(carClass);
                classPackage.addClass(priusClass);
                classPackage.addClass(crownClass);
                classPackage.addClass(tireClass);

                Java actual = obj.translate(classPackage);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void コンポジション関係にあたる複数のクラスを持つJavaコードを返す() {
                Java expected = new Java();
                io.github.morichan.retuss.language.java.Class javaClass1 = new io.github.morichan.retuss.language.java.Class("ClassName1");
                io.github.morichan.retuss.language.java.Class javaClass2 = new io.github.morichan.retuss.language.java.Class("ClassName2");
                io.github.morichan.retuss.language.java.Class javaClass3 = new io.github.morichan.retuss.language.java.Class("ClassName3");
                Field javaClass1Field = new Field();
                javaClass1Field.setName("className2");
                javaClass1Field.setType(new Type("ClassName2"));
                javaClass1Field.setAccessModifier(AccessModifier.Private);
                javaClass1Field.setValue("new ClassName2()");
                Field javaClass2Field = new Field();
                javaClass2Field.setName("className3");
                javaClass2Field.setType(new Type("ClassName3"));
                javaClass2Field.setAccessModifier(AccessModifier.Private);
                javaClass2Field.setValue("new ClassName3()");
                javaClass1.addField(javaClass1Field);
                javaClass2.addField(javaClass2Field);
                expected.addClass(javaClass1);
                expected.addClass(javaClass2);
                expected.addClass(javaClass3);

                Package classPackage = new Package();
                Class classClass1 = new Class("ClassName1");
                Class classClass2 = new Class("ClassName2");
                Class classClass3 = new Class("ClassName3");
                Attribute classClass1Attribute = new Attribute(new Name("className2"));
                classClass1Attribute.setType(new io.github.morichan.fescue.feature.type.Type("ClassName2"));
                classClass1Attribute.setVisibility(Visibility.Private);
                classClass1Attribute.setDefaultValue(new DefaultValue(new OneIdentifier("new ClassName2()")));
                Attribute classClass2Attribute = new Attribute(new Name("className3"));
                classClass2Attribute.setType(new io.github.morichan.fescue.feature.type.Type("ClassName3"));
                classClass2Attribute.setVisibility(Visibility.Private);
                classClass2Attribute.setDefaultValue(new DefaultValue(new OneIdentifier("new ClassName3()")));
                classClass1.addRelation(classClass1Attribute);
                classClass2.addRelation(classClass2Attribute);
                classPackage.addClass(classClass1);
                classPackage.addClass(classClass2);
                classPackage.addClass(classClass3);

                Java actual = obj.translate(classPackage);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }
    }
}