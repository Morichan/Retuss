package io.github.morichan.retuss.window.diagram.sequence;

import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.window.diagram.OperationGraphic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InteractionTest {

    Interaction obj;

    @Nested
    class 初期値について {

        @BeforeEach
        void setup() {
            obj = new Interaction(new Class(), new OperationGraphic());
        }

        @Test
        void フィールドにおけるMessageOccurrenceSpecificationオブジェクトはnullを返す() {

            MessageOccurrenceSpecification actual = obj.getMessage();

            assertThat(actual).isNull();
        }
    }
}