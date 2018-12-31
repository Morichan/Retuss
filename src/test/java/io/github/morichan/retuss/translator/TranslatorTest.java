package io.github.morichan.retuss.translator;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.feature.parameter.Parameter;
import io.github.morichan.fescue.feature.value.DefaultValue;
import io.github.morichan.fescue.feature.value.expression.OneIdentifier;
import io.github.morichan.retuss.language.java.*;
import io.github.morichan.retuss.language.uml.Package;
import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.window.diagram.OperationGraphic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    Translator obj;

    @Nested
    class クラス図からJavaへ変換する場合 {

        @BeforeEach
        void setup() {
            obj = new Translator();
        }

        @Test
        void クラス名とフィールド2つメソッド2つ持つJavaコードを返す() {
            String expected = "class ClassName {\n    private int number;\n    private float pi = 3.1415926535;\n\n    public boolean isTrue() {}\n    public double calculate(double x, double y, double z) {}\n}\n";

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

            obj.translate(classPackage);
            Java actual = obj.getJava();

            assertThat(actual.getClasses().get(0)).hasToString(expected);
        }
    }

    @Nested
    class Javaからクラス図に変換する場合 {

        @BeforeEach
        void setup() {
            obj = new Translator();
        }

        @Test
        void クラス名とフィールド2つメソッド2つ持つクラスを返す() {
            List<String> expected = Arrays.asList(
                    "ClassName",
                    "- number : int",
                    "- pi : float = 3.1415926535",
                    "+ isTrue() : boolean",
                    "+ calculate(x : double, y : double, z : double) : double"
            );
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

            obj.translate(java);
            Package actual = obj.getPackage();

            assertAll(
                    () -> assertThat(actual.getClasses().get(0).getName()).hasToString(expected.get(0)),
                    () -> assertThat(actual.getClasses().get(0).extractAttributes().get(0)).hasToString(expected.get(1)),
                    () -> assertThat(actual.getClasses().get(0).extractAttributes().get(1)).hasToString(expected.get(2)),
                    () -> assertThat(actual.getClasses().get(0).extractOperations().get(0)).hasToString(expected.get(3)),
                    () -> assertThat(actual.getClasses().get(0).extractOperations().get(1)).hasToString(expected.get(4))
            );
        }
    }
}