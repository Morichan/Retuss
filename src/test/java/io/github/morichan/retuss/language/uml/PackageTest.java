package io.github.morichan.retuss.language.uml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PackageTest {

    Package obj;

    @Nested
    class 正しい使い方で使っている場合 {

        @Nested
        class パッケージ名について {

            @BeforeEach
            void setup() {
                obj = new Package();
            }

            @Test
            void デフォルトの名前を返す() {
                String expected = "main";

                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した名前を返す() {
                String expected = "package";

                obj.setName("package");
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void nullを設定するとデフォルトの名前を返す() {
                String expected = "main";

                obj.setName(null);
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 空文字を設定するとデフォルトの名前を返す() {
                String expected = "main";

                obj.setName("");
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class クラスについて {

            @BeforeEach
            void setup() {
                obj = new Package();
            }

            @Test
            void クラスが全くない初期値として要素数0のリストを返す() {

                List<Class> actual = obj.getClasses();

                assertThat(actual).isEmpty();
            }

            @Test
            void 設定したクラスを1つ返す() {
                Class expected = new Class("ExpectedClass");

                obj.addClass(expected);
                List<Class> actual = obj.getClasses();

                assertThat(actual).containsOnly(expected);
            }

            @Test
            void 設定したクラスを3つ返す() {
                List<Class> expected = Arrays.asList(
                        new Class("ClassName1"),
                        new Class("ClassName2"),
                        new Class("ClassName3"));

                obj.setClasses(expected);
                List<Class> actual = obj.getClasses();

                assertThat(actual).containsSequence(expected);
            }

            @Test
            void nullを設定すると要素数0のリストを返す() {
                obj.setClasses(null);

                List<Class> actual = obj.getClasses();

                assertThat(actual).isEmpty();
            }

            @Test
            void リセットすると要素数0のリストを返す() {
                obj.emptyClasses();

                List<Class> actual = obj.getClasses();

                assertThat(actual).isEmpty();
            }
        }
    }
}