package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MethodTest {

    Method obj;

    @Nested
    class コードを出力する場合 {

        @Nested
        class 名前と型のみの場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void デフォルトの型と名前を返す() {
                String expected = "public void method() {}";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した型と名前を返す() {
                String expected = "public double method() {}";

                obj.setType(new Type("double"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 引数を含む場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void 引数が1つのメソッドを返す() {
                String expected = "public void method(int argument) {}";

                obj.addArgument(new Argument(new Type("int"), "argument"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 引数が3つのメソッドを返す() {
                String expected = "public int calculate(double x, double y, float pi) {}";

                obj.setType(new Type("int"));
                obj.setName("calculate");
                obj.setArguments(Arrays.asList(
                        new Argument(new Type("double"), "x"),
                        new Argument(new Type("double"), "y"),
                        new Argument(new Type("float"), "pi")));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class アクセス修飾子を変更する場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void プロテクテッドなメソッドを返す() {
                String expected = "protected void method(int argument) {}";

                obj.setAccessModifier(AccessModifier.Protected);
                obj.addArgument(new Argument(new Type("int"), "argument"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void パッケージなメソッドを返す() {
                String expected = "void method(int argument) {}";

                obj.setAccessModifier(AccessModifier.choose(""));
                obj.addArgument(new Argument(new Type("int"), "argument"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っている場合 {

        @Nested
        class 名前のみの場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void デフォルトの名前を返す() {
                String expected = "method";

                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した名前を返す() {
                String expected = "setMethod";

                obj.setName("setMethod");
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 型を持つ場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void デフォルトの型を返す() {
                Type expected = new Type("void");

                Type actual = obj.getType();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 設定した型を返す() {
                Type expected = new Type("double");

                obj.setType(new Type("double"));
                Type actual = obj.getType();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }

        @Nested
        class 引数を持つ場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void デフォルトでは要素数0を返す() {

                List<Argument> actual = obj.getArguments();

                assertThat(actual).isEmpty();
            }

            @Test
            void 引数を1つ返す() {
                Argument expected = new Argument(new Type("int"), "number");

                obj.addArgument(expected);
                List<Argument> actual = obj.getArguments();

                assertThat(actual).containsOnly(expected);
            }

            @Test
            void 引数を3つ返す() {
                List<Argument> expected = Arrays.asList(
                        new Argument(new Type("int"), "number"),
                        new Argument(new Type("double"), "x"),
                        new Argument(new Type("float"), "y"));

                obj.setArguments(expected);
                List<Argument> actual = obj.getArguments();

                assertThat(actual).containsSequence(expected);
            }

            @Test
            void nullを設定すると要素数0のリストを返す() {
                obj.setArguments(null);

                List<Argument> actual = obj.getArguments();

                assertThat(actual).isEmpty();
            }

            @Test
            void リセットすると要素数0のリストを返す() {
                obj.emptyArguments();

                List<Argument> actual = obj.getArguments();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        class アクセス修飾子を持つ場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void デフォルトのアクセス修飾子を返す() {
                AccessModifier expected = AccessModifier.Public;

                AccessModifier actual = obj.getAccessModifier();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したアクセス修飾子を返す() {
                AccessModifier expected = AccessModifier.Private;

                obj.setAccessModifier(AccessModifier.Private);
                AccessModifier actual = obj.getAccessModifier();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っていない場合 {

        @Nested
        class メソッド名の場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
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
        class 引数の場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void nullを設定しようとしても反映しない() {

                obj.addArgument(null);
                List<Argument> actual = obj.getArguments();

                assertThat(actual).isEmpty();
            }

            @Test
            void nullを含む引数のリストを設定しようとするとnullが抜けた2つのリストを返す() {
                List<Argument> expected = Arrays.asList(
                        new Argument(new Type("int"), "number"),
                        null,
                        new Argument(new Type("float"), "y"));

                obj.setArguments(expected);
                List<Argument> actual = obj.getArguments();

                assertThat(actual).doesNotContainNull();
            }
        }

        @Nested
        class アクセス修飾子の場合 {

            @BeforeEach
            void setup() {
                obj = new Method();
            }

            @Test
            void nullを入力すると例外を投げる() {
                assertThatThrownBy(() -> obj.setAccessModifier(null)).isInstanceOf(IllegalArgumentException.class);
            }
        }
    }
}