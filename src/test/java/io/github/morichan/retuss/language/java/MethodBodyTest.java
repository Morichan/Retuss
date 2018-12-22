package io.github.morichan.retuss.language.java;

import org.antlr.v4.parse.ANTLRParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MethodBodyTest {

    MethodBody obj;

    @Nested
    class 中身が何も存在しない場合 {

        @BeforeEach
        void setup() {
            obj = new MethodBody();
        }

        @Test
        void 初期値は要素数0である() {

            int actual = obj.getStatements().size();

            assertThat(actual).isZero();
        }
    }

    @Nested
    class ローカル変数宣言文が存在する場合 {

        @BeforeEach
        void setup() {
            obj = new MethodBody();
        }

        @Test
        void 既定値を1つ取得する() {
            BlockStatement expected = new LocalValiableDeclaration();

            obj.addStatement(new LocalValiableDeclaration());
            BlockStatement actual = obj.getStatements().get(0);

            assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
        }

        @Test
        void 既定値を3つ取得する() {
            List<BlockStatement> expectedList = Arrays.asList(
                    new LocalValiableDeclaration(),
                    new LocalValiableDeclaration(new Type("double"), "x"),
                    new LocalValiableDeclaration(new Type("Double"), "y", "new Double()"));

            obj.addStatement(new LocalValiableDeclaration());
            obj.addStatement(new LocalValiableDeclaration(new Type("double"), "x"));
            obj.addStatement(new LocalValiableDeclaration(new Type("Double"), "y", "new Double()"));
            List<BlockStatement> actualList = obj.getStatements();

            assertThat(actualList.size()).isEqualTo(expectedList.size());
            for (int i = 0; i < actualList.size(); i++) {
                assertThat(actualList.get(i)).isEqualToComparingFieldByFieldRecursively(expectedList.get(i));
            }
        }
    }
}