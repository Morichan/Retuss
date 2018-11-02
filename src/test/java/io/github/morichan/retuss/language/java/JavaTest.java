package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JavaTest {

    Java obj;

    @Nested
    class 正しい使い方で使っている場合 {

        @BeforeEach
        void setup() {
            obj = new Java();
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

    @Nested
    class 正しい使い方で使っていない場合 {

        @BeforeEach
        void setup() {
            obj = new Java();
        }

        @Test
        void nullを設定しようとしても反映しない() {

            obj.addClass(null);
            List<Class> actual = obj.getClasses();

            assertThat(actual).isEmpty();
        }

        @Test
        void nullを含むフィールドのリストを設定しようとするとnullが抜けた2つのリストを返す() {
            List<Class> expected = Arrays.asList(
                    new Class("ClassName0"),
                    null,
                    new Class("ClassName2"));

            obj.setClasses(expected);
            List<Class> actual = obj.getClasses();

            assertThat(actual).doesNotContainNull();
        }
    }
}