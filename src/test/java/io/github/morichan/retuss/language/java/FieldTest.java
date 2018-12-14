package io.github.morichan.retuss.language.java;

import org.antlr.v4.parse.ANTLRParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    Field obj;

    @Nested
    class 正しい使い方で使っている際に {

        @Nested
        class 名前のみの場合 {

            @BeforeEach
            void setup() {
                obj = new Field();
            }

            @Test
            void デフォルトの名前を返す() {
                String expected = "field";

                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した名前を返す() {
                String expected = "private int newField;";

                obj.setName("newField");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 型を持つ場合 {

            @BeforeEach
            void setup() {
                obj = new Field();
            }

            @Test
            void デフォルトの型を返す() {
                Type expected = new Type("int");

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

            @Test
            void デフォルトの型と名前を返す() {
                String expected = "private int field;";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した型と名前を返す() {
                String expected = "private double field;";

                obj.setType(new Type("double"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 既定値を持つ場合 {

            @BeforeEach
            void setup() {
                obj = new Field();
            }

            @Test
            void 設定した数値を含むフィールドを返す() {
                String expected = "private int number = 0;";

                obj.setName("number");
                obj.setValue("0");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した文字を含むフィールドを返す() {
                String expected = "private char chara = 't';";

                obj.setType(new Type("char"));
                obj.setName("chara");
                obj.setValue("'t'");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した文字列を含むフィールドを返す() {
                String expected = "private String text = \"This is string.\";";

                obj.setType(new Type("String"));
                obj.setName("text");
                obj.setValue("\"This is string.\"");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した空文字を含むフィールドを返す() {
                String expected = "private String nullText = \"\";";

                obj.setType(new Type("String"));
                obj.setName("nullText");
                obj.setValue("\"\"");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したnullを含むフィールドを返す() {
                String expected = "private String nullText = null;";

                obj.setType(new Type("String"));
                obj.setName("nullText");
                obj.setValue("null");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void nullを設定した場合はnullを返す() {

                obj.setValue(null);
                Value actual = obj.getValue();

                assertThat(actual).isNull();
            }
        }

        @Nested
        class アクセス修飾子を持つ場合 {

            @BeforeEach
            void setup() {
                obj = new Field();
            }

            @Test
            void デフォルトのアクセス修飾子を返す() {
                AccessModifier expected = AccessModifier.Private;

                AccessModifier actual = obj.getAccessModifier();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したアクセス修飾子を返す() {
                AccessModifier expected = AccessModifier.Public;

                obj.setAccessModifier(AccessModifier.Public);
                AccessModifier actual = obj.getAccessModifier();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void デフォルトでないアクセス修飾子と型と名前を返す() {
                String expected = "protected int field;";

                obj.setAccessModifier(AccessModifier.choose("protected"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したアクセス修飾子と型と名前を返す() {
                String expected = "int field;";

                obj.setAccessModifier(AccessModifier.Package);
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っていない場合 {

        @BeforeEach
        void setup() {
            obj = new Field();
        }

        @Test
        void 名前にnullを設定したら例外を投げる() {
            assertThatThrownBy(() -> obj.setName(null)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 名前に空文字を設定したら例外を投げる() {
            assertThatThrownBy(() -> obj.setName("")).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 型にnullを設定したら例外を投げる() {
            assertThatThrownBy(() -> obj.setType(null)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void アクセス修飾子にnullを設定したら例外を投げる() {
            assertThatThrownBy(() -> obj.setAccessModifier(null)).isInstanceOf(IllegalArgumentException.class);
        }
    }
}