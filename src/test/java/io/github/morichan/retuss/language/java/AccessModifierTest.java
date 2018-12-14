package io.github.morichan.retuss.language.java;

import org.antlr.v4.parse.ANTLRParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class AccessModifierTest {

    AccessModifier obj;

    @Nested
    class 正しい使い方で使っている場合 {

        @Nested
        class パブリックについて {

            @BeforeEach
            void setup() {
                obj = AccessModifier.Public;
            }

            @Test
            void 文字列を返す() {
                String expected = "public";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 真を返す() {

                boolean actual = obj.is("public");

                assertThat(actual).isTrue();
            }

            @Test
            void 偽を返す() {

                boolean actual = obj.is("protected");

                assertThat(actual).isFalse();
            }

            @Test
            void 自身を返す() {
                AccessModifier expected = AccessModifier.Public;

                AccessModifier actual = AccessModifier.choose("public");

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class プロテクテッドについて {

            @BeforeEach
            void setup() {
                obj = AccessModifier.Protected;
            }

            @Test
            void 文字列を返す() {
                String expected = "protected";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 真を返す() {

                boolean actual = obj.is("protected");

                assertThat(actual).isTrue();
            }

            @Test
            void 偽を返す() {

                boolean actual = obj.is("");

                assertThat(actual).isFalse();
            }

            @Test
            void 自身を返す() {
                AccessModifier expected = AccessModifier.Protected;

                AccessModifier actual = AccessModifier.choose("protected");

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class パッケージについて {

            @BeforeEach
            void setup() {
                obj = AccessModifier.Package;
            }

            @Test
            void 文字列を返す() {

                String actual = obj.toString();

                assertThat(actual).isEmpty();
            }

            @Test
            void 真を返す() {

                boolean actual = obj.is("");

                assertThat(actual).isTrue();
            }

            @Test
            void 偽を返す() {

                boolean actual = obj.is("private");

                assertThat(actual).isFalse();
            }

            @Test
            void 自身を返す() {
                AccessModifier expected = AccessModifier.Package;

                AccessModifier actual = AccessModifier.choose("");

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class プライベートについて {

            @BeforeEach
            void setup() {
                obj = AccessModifier.Private;
            }

            @Test
            void 文字列を返す() {
                String expected = "private";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 真を返す() {

                boolean actual = obj.is("private");

                assertThat(actual).isTrue();
            }

            @Test
            void 偽を返す() {

                boolean actual = obj.is("public");

                assertThat(actual).isFalse();
            }

            @Test
            void 自身を返す() {
                AccessModifier expected = AccessModifier.Private;

                AccessModifier actual = AccessModifier.choose("private");

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っていない場合 {

        @Test
        void nullで検索しようとすると例外を投げる() {
            assertThatThrownBy(() -> AccessModifier.choose(null)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 変な文字列で検索しようとすると例外を投げる() {
            assertThatThrownBy(() -> AccessModifier.choose("whatIs")).isInstanceOf(IllegalArgumentException.class);
        }
    }
}