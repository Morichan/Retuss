package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClassTest {

    Class obj;

    @Nested
    class コードを出力する場合 {

        @Nested
        class クラス名のみの場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void コードを返す() {
                String expected = "class ClassName {\n}\n";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したクラス名のコードを返す() {
                String expected = "class NewClassName {\n}\n";

                obj.setName("NewClassName");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 継承クラスを含む場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void コードを返す() {
                String expected = "class ClassName extends SuperClass {\n}\n";

                obj.setExtendsClass(new Class("SuperClass"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したクラス名のコードを返す() {
                String expected = "class SubClass extends SuperClass {\n}\n";

                obj.setName("SubClass");
                obj.setExtendsClass(new Class("SuperClass"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class フィールドを含む場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void フィールドが1つのコードを返す() {
                String expected = "class ClassName {\n    String text = \"\";\n}\n";

                obj.addField(new Field(new Type("String"), "text", "\"\""));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void フィールドが3つのコードを返す() {
                String expected = "class SubClass extends SuperClass {\n    int number;\n    double pi = 3.1415926535;\n    String text;\n}\n";

                obj.setName("SubClass");
                obj.setExtendsClass(new Class("SuperClass"));
                obj.setFields(Arrays.asList(
                        new Field(new Type("int"), "number"),
                        new Field(new Type("double"), "pi", "3.1415926535"),
                        new Field(new Type("String"), "text")));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class メソッドを含む場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void メソッドが1つのコードを返す() {
                String expected = "class ClassName {\n    void method() {}\n}\n";

                obj.addMethod(new Method());
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void フィールドが3つのコードを返す() {
                String expected = "class ClassName {\n    double calculate(double x, double y) {}\n    void print() {}\n    String toString() {}\n}\n";

                obj.setName("ClassName");
                obj.setMethod(Arrays.asList(
                        new Method(new Type("double"), "calculate",
                                new Argument(new Type("double"), "x"),
                                new Argument(new Type("double"), "y")),
                        new Method(new Type("void"), "print"),
                        new Method(new Type("String"), "toString")));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class フィールドとメソッドを含む場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void フィールドとメソッドの間に空行を含むコードを返す() {
                String expected = "class ClassName {\n    int x;\n    int y;\n\n    void method() {}\n    void print() {}\n}\n";

                obj.setFields(Arrays.asList(new Field(new Type("int"), "x"), new Field(new Type("int"), "y")));
                obj.setMethod(Arrays.asList(new Method(new Type("void"), "method"), new Method(new Type("void"), "print")));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っている際に {

        @Nested
        class クラス名の場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void デフォルトのクラス名を返す() {
                String expected = "ClassName";

                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したクラス名を返す() {
                String expected = "ChangedClassName";

                obj.setName("ChangedClassName");
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 継承クラス名の場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void デフォルトの継承クラスとしてnullを返す() {

                String actual = obj.getExtendsClassName();

                assertThat(actual).isNull();
            }

            @Test
            void 設定した継承クラス名を返す() {
                String expected = "ExtendedClassName";

                obj.setExtendsClass(new Class("ExtendedClassName"));
                String actual = obj.getExtendsClassName();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class フィールドの場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void フィールドが全くない初期値として要素数0のリストを返す() {

                List<Field> actual = obj.getFields();

                assertThat(actual).isEmpty();
            }

            @Test
            void フィールドを1つ返す() {
                Field expected = new Field(new Type("int"), "number");

                obj.addField(expected);
                List<Field> actual = obj.getFields();

                assertThat(actual).containsOnly(expected);
            }

            @Test
            void フィールドを3つ返す() {
                List<Field> expected = Arrays.asList(
                        new Field(new Type("int"), "number"),
                        new Field(new Type("double"), "x", "0.0"),
                        new Field(new Type("float"), "y", "-1.0"));

                obj.setFields(expected);
                List<Field> actual = obj.getFields();

                assertThat(actual).containsSequence(expected);
            }

            @Test
            void nullを設定すると要素数0のリストを返す() {
                obj.setFields(null);

                List<Field> actual = obj.getFields();

                assertThat(actual).isEmpty();
            }

            @Test
            void リセットすると要素数0のリストを返す() {
                obj.emptyFields();

                List<Field> actual = obj.getFields();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        class メソッドの場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void メソッドが全くない初期値として要素数0のリストを返す() {

                List<Method> actual = obj.getMethods();

                assertThat(actual).isEmpty();
            }

            @Test
            void メソッドを1つ返す() {
                Method expected = new Method(new Type("int"), "number");

                obj.addMethod(expected);
                List<Method> actual = obj.getMethods();

                assertThat(actual).containsOnly(expected);
            }

            @Test
            void メソッドを3つ返す() {
                List<Method> expected = Arrays.asList(
                        new Method(new Type("int"), "number"),
                        new Method(new Type("double"), "x"),
                        new Method(new Type("float"), "y"));

                obj.setMethod(expected);
                List<Method> actual = obj.getMethods();

                assertThat(actual).containsSequence(expected);
            }

            @Test
            void nullを設定すると要素数0のリストを返す() {
                obj.addMethod(new Method(new Type("int"), "number"));

                obj.setMethod(null);
                List<Method> actual = obj.getMethods();

                assertThat(actual).isEmpty();
            }

            @Test
            void リセットすると要素数0のリストを返す() {
                obj.addMethod(new Method(new Type("int"), "number"));

                obj.emptyMethods();
                List<Method> actual = obj.getMethods();

                assertThat(actual).isEmpty();
            }
        }
    }

    @Nested
    class 正しくない使い方で使っている際に {

        @Nested
        class クラス名の場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void 空文字を入力すると例外を投げる() {
                assertThatThrownBy(() -> obj.setName("")).isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            void nullを入力すると例外を投げる() {
                assertThatThrownBy(() -> obj.setName(null)).isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        class フィールドの場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void nullを設定しようとしても反映しない() {

                obj.addField(null);
                List<Field> actual = obj.getFields();

                assertThat(actual).isEmpty();
            }

            @Test
            void nullを含むフィールドのリストを設定しようとするとnullが抜けた2つのリストを返す() {
                List<Field> expected = Arrays.asList(
                        new Field(new Type("int"), "number"),
                        null,
                        new Field(new Type("float"), "y"));

                obj.setFields(expected);
                List<Field> actual = obj.getFields();

                assertThat(actual).doesNotContainNull();
            }
        }

        @Nested
        class メソッドの場合 {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void nullを設定しようとしても反映しない() {

                obj.addMethod(null);
                List<Method> actual = obj.getMethods();

                assertThat(actual).isEmpty();
            }

            @Test
            void nullを含むメソッドのリストを設定しようとするとnullが抜けた2つのリストを返す() {
                List<Method> expected = Arrays.asList(
                        new Method(new Type("int"), "number"),
                        null,
                        new Method(new Type("float"), "y"));

                obj.setMethod(expected);
                List<Method> actual = obj.getMethods();

                assertThat(actual).doesNotContainNull();
            }
        }
    }
}