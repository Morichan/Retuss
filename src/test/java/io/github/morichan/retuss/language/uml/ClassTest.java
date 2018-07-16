package io.github.morichan.retuss.language.uml;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ClassTest {

    Class obj;

    @Nested
    class 正しい使い方で使っている場合 {

        @Nested
        class クラス名について {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void デフォルトの名前を返す() {
                String expected = "ClassName";

                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した名前を返す() {
                String expected = "SetClassName";

                obj.setName("SetClassName");
                String actual = obj.getName();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 属性について {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void 属性が全くない初期値として要素数0のリストを返す() {

                List<Attribute> actual = obj.getAttributes();

                assertThat(actual).isEmpty();
            }

            @Test
            void 属性を1つ返す() {
                Attribute expected = new Attribute(new Name("attribute"));

                obj.addAttribute(expected);
                List<Attribute> actual = obj.getAttributes();

                assertThat(actual).containsOnly(expected);
            }

            @Test
            void 属性を3つ返す() {
                List<Attribute> expected = Arrays.asList(
                        new Attribute(new Name("attribute0")),
                        new Attribute(new Name("attribute1")),
                        new Attribute(new Name("attribute2")));

                obj.setAttributes(expected);
                List<Attribute> actual = obj.getAttributes();

                assertThat(actual).containsSequence(expected);
            }

            @Test
            void nullを設定すると要素数0のリストを返す() {
                obj.setAttributes(null);

                List<Attribute> actual = obj.getAttributes();

                assertThat(actual).isEmpty();
            }

            @Test
            void リセットすると要素数0のリストを返す() {
                obj.emptyAttribute();

                List<Attribute> actual = obj.getAttributes();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        class 操作について {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void 操作が全くない初期値として要素数0のリストを返す() {

                List<Operation> actual = obj.getOperations();

                assertThat(actual).isEmpty();
            }

            @Test
            void 操作を1つ返す() {
                Operation expected = new Operation(new Name("operation"));

                obj.addOperation(expected);
                List<Operation> actual = obj.getOperations();

                assertThat(actual).containsOnly(expected);
            }

            @Test
            void 操作を3つ返す() {
                List<Operation> expected = Arrays.asList(
                        new Operation(new Name("operation0")),
                        new Operation(new Name("operation1")),
                        new Operation(new Name("operation2")));

                obj.setOperations(expected);
                List<Operation> actual = obj.getOperations();

                assertThat(actual).containsSequence(expected);
            }

            @Test
            void nullを設定すると要素数0のリストを返す() {
                obj.setOperations(null);

                List<Operation> actual = obj.getOperations();

                assertThat(actual).isEmpty();
            }

            @Test
            void リセットすると要素数0のリストを返す() {
                obj.emptyOperation();

                List<Operation> actual = obj.getOperations();

                assertThat(actual).isEmpty();
            }
        }
    }

    @Nested
    class 正しい使い方で使っていない場合 {

        @Nested
        class クラス名について {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void nullを入力すると例外を投げる() {
                assertThatThrownBy(() -> obj.setName(null)).isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            void 空文字を入力すると例外を投げる() {
                assertThatThrownBy(() -> obj.setName("")).isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        class 属性について {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void nullを設定しようとしても反映しない() {

                obj.addAttribute(null);
                List<Attribute> actual = obj.getAttributes();

                assertThat(actual).isEmpty();
            }

            @Test
            void nullを含む属性のリストを設定しようとするとnullが抜けた2つのリストを返す() {
                List<Attribute> expected = Arrays.asList(
                        new Attribute(new Name("attribute0")),
                        null,
                        new Attribute(new Name("attribute2")));

                obj.setAttributes(expected);
                List<Attribute> actual = obj.getAttributes();
                    assertThat(actual).doesNotContainNull();
            }
        }

        @Nested
        class 操作について {

            @BeforeEach
            void setup() {
                obj = new Class();
            }

            @Test
            void nullを設定しようとしても反映しない() {

                obj.addOperation(null);
                List<Operation> actual = obj.getOperations();

                assertThat(actual).isEmpty();
            }

            @Test
            void nullを含む操作のリストを設定しようとするとnullが抜けた2つのリストを返す() {
                List<Operation> expected = Arrays.asList(
                        new Operation(new Name("operation0")),
                        null,
                        new Operation(new Name("operation2")));

                obj.setOperations(expected);
                List<Operation> actual = obj.getOperations();
                assertThat(actual).doesNotContainNull();
            }
        }
    }
}