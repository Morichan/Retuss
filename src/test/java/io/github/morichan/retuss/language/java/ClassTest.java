package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClassTest {

    Class obj;

    @BeforeEach
    void setup() {
        obj = new Class();
    }

    @Nested
    class コードを出力する場合 {

        @Nested
        class クラス名のみの場合 {

            @Test
            void コードを返す() {
                String expected = "class ClassName {\n}\n";

                obj.manufacture();
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したクラス名のコードを返す() {
                String expected = "class NewClassName {\n}\n";

                obj.setName("NewClassName");
                obj.manufacture();
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 継承クラスを含む場合 {

            @Test
            void コードを返す() {
                String expected = "class ClassName extends SuperClass {\n}\n";

                Class extendedClass = new Class();
                extendedClass.setName("SuperClass");
                obj.setExtendsClass(extendedClass);
                obj.manufacture();
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定したクラス名のコードを返す() {
                String expected = "class SubClass extends SuperClass {\n}\n";

                Class extendedClass = new Class();
                extendedClass.setName("SuperClass");
                obj.setName("SubClass");
                obj.setExtendsClass(extendedClass);
                obj.manufacture();
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しい使い方で使っている際に {

        @Nested
        class クラス名の場合 {

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

            @Test
            void デフォルトでは存在しないために空文字を返す() {
                assertThat(obj.getExtendsClassName()).isEmpty();
            }

            @Test
            void 設定した継承クラス名を返す() {
                String expected = "ExtendedClassName";

                Class extendedClass = new Class();
                extendedClass.setName("ExtendedClassName");

                obj.setExtendsClass(extendedClass);
                String actual = obj.getExtendsClassName();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しくない使い方で使っている際に {

        @Nested
        class クラス名の場合 {

            @Test
            void 空文字を入力すると例外を投げる() {
                assertThatThrownBy(() -> obj.setName("")).isInstanceOf(IllegalArgumentException.class);
            }
        }
    }
}