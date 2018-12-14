package io.github.morichan.retuss.language.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ArrayLengthTest {

    ArrayLength obj;

    @BeforeEach
    void setup() {
        obj = new ArrayLength();
    }

    @Test
    void 既定値を返す() {
        int expected = 0;

        int actual = obj.getLength();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 設定値を返す() {
        int expected = 3;

        obj.setLength(3);
        int actual = obj.getLength();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void コンストラクタ設定値を返す() {
        int expected = 3;

        obj = new ArrayLength(3);
        int actual = obj.getLength();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 既定では表示しない() {

        boolean actual = obj.isEnabled();

        assertThat(actual).isFalse();
    }

    @Test
    void 設定によって出力する() {

        obj.setEnabled(true);
        boolean actual = obj.isEnabled();

        assertThat(actual).isTrue();
    }

    @Test
    void 配列の文字列を返す() {
        String expected = "[2]";

        obj.setLength(2);
        String actual = obj.toString();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 要素数が0の場合は要素数が空の文字列を返す() {
        String expected = "[]";

        obj.setEnabled(true);
        String actual = obj.toString();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void デフォルトでは出力しない() {
        String expected = "";

        String actual = obj.toString();

        assertThat(actual).isEqualTo(expected);
    }
}