package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    Type obj;
    @Nested
    class 正しい使い方で使っている際に {

        @Nested
        class 名前のみの場合 {

            @BeforeEach
            void setup() {
                obj = new Type();
            }

            @Test
            void デフォルトの型名を返す() {
                String expected = "int";

                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した型名を返す() {
                String expected = "double";

                obj.addTypeName("double");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 型名をリセットして新しく設定しなおした型名を返す() {
                String expected = "float";

                obj.addTypeName("double");
                obj.resetTypes();
                obj.addTypeName("float");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 型が3つ続く型名を返す() {
                String expected = "Type.In.Class";

                obj.addTypeName("Type");
                obj.addTypeName("In");
                obj.addTypeName("Class");
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 配列の場合 {

            @BeforeEach
            void setup() {
                obj = new Type();
            }

            @Test
            void デフォルトの型名の配列を返す() {
                String expected = "int[]";

                obj.setArrangementCount(1);
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 設定した型名の二重配列を返す() {
                String expected = "double[][]";

                obj.addTypeName("double");
                obj.setArrangementCount(2);
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class データ型を持つ場合 {

            @BeforeEach
            void setup() {
                obj = new Type();
            }

            @Test
            void データ型を1つ持つ型名を返す() {
                String expected = "List<String>";
                Type type = new Type();
                type.addTypeName("String");

                obj.addTypeName("List");
                obj.addDataType(type);
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void データ型を2つ持つ型名を返す() {
                String expected = "HashMap<String, Integer>";
                Type stringType = new Type("String");
                Type integerType = new Type("Integer");

                obj.addTypeName("HashMap");
                obj.addDataType(stringType);
                obj.addDataType(integerType);
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void データ型をリセットして新しく設定したデータ型を2つ持つ型名を返す() {
                String expected = "HashMap<String, Integer>";
                Type deletedType = new Type("List");
                Type stringType = new Type("String");
                Type integerType = new Type("Integer");

                obj.addTypeName("HashMap");
                obj.addDataType(deletedType);
                obj.resetDataTypes();
                obj.addDataType(stringType);
                obj.addDataType(integerType);
                String actual = obj.toString();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }
}