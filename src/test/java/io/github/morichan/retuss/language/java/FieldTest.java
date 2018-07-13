package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
                String expected = "int newField;";

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
            void デフォルトの型と名前を返す() {
                String expected = "int field;";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した型と名前を返す() {
                String expected = "double field;";

                obj.setType(new Type("double"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }
}