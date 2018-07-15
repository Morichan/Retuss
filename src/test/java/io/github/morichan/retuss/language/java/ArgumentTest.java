package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ArgumentTest {

    Argument obj;

    @Nested
    class 正しい使い方で使っている場合 {

        @Nested
        class 名前のみの場合 {

            @BeforeEach
            void setup() {
                obj = new Argument();
            }

            @Test
            void デフォルトの名前を返す() {
                String expected = "argument";

                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した名前を返す() {
                String expected = "setArgument";

                obj.setName("setArgument");
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 型を持つ場合 {

            @BeforeEach
            void setup() {
                obj = new Argument();
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
                String expected = "int argument";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した型と名前を返す() {
                String expected = "double argument";

                obj.setType(new Type("double"));
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っていない場合 {

        @BeforeEach
        void setup() {
            obj = new Argument();
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
    }
}