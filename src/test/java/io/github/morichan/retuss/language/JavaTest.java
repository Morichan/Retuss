package io.github.morichan.retuss.language;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JavaTest {

    Java obj = new Java();

    @Test
    void 必要最低限の文字列を返す() {
        String expected = "class ClassName {\n}\n";

        String actual = obj.toString();

        assertThat(actual).isEqualTo(expected);
    }
}