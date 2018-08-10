package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ValueTest {

    Value obj;

    @Nested
    class 文字列として出力する場合 {

        @Nested
        class 名前のみの場合 {

            @BeforeEach
            void setup() {
                obj = new Value();
            }

            @Test
            void 文字列を返す() {
                String expected = "id";

                obj.setName("id");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class new文の場合 {

            @BeforeEach
            void setup() {
                obj = new Value();
            }

            @Test
            void 引数のない文字列を返す() {
                String expected = "new ClassName()";

                obj.setName("new ClassName");
                String actual = obj.toString();

                assertThat(actual).endsWith(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っている場合 {

        @Nested
        class 名前のみの場合 {

            @BeforeEach
            void setup() {
                obj = new Value();
            }

            @Test
            void 設定した名前を返す() {
                String expected = "id";

                obj.setName("id");
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void デフォルトの名前を返す() {
                String expected = "identifier";

                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class new文の場合 {

            @BeforeEach
            void setup() {
                obj = new Value();
            }

            @Test
            void 設定した文字列からnew文と判定して名前を返す() {
                String expected = "ClassName";

                obj.setName("new ClassName");
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っていない場合 {

        @BeforeEach
        void setup() {
            obj = new Value();
        }

        @Test
        void nullを設定しようとすると例外を投げる() {
            assertThatThrownBy(() -> obj.setName(null)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 空文字を設定しようとすると例外を投げる() {
            assertThatThrownBy(() -> obj.setName("")).isInstanceOf(IllegalArgumentException.class);
        }
    }
}