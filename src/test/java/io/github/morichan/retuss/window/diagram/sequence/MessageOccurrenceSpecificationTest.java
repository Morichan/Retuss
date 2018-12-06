package io.github.morichan.retuss.window.diagram.sequence;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MessageOccurrenceSpecificationTest {

    MessageOccurrenceSpecification obj = new MessageOccurrenceSpecification();

    @Test
    void 既定の開始座標を取得する() {
        Point2D expected = Point2D.ZERO;

        Point2D actual = obj.getBeginPoint();

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void 既定の終了座標を取得する() {
        Point2D expected = Point2D.ZERO;

        Point2D actual = obj.getEndPoint();

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}