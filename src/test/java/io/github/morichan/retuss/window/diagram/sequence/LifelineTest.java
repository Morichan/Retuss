package io.github.morichan.retuss.window.diagram.sequence;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LifelineTest {

    Lifeline obj = new Lifeline();

    @Test
    void 既定の座標を返す() {
        Point2D expected = new Point2D(0, 7);

        Point2D actual = obj.getHeadCenterPoint();

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void メッセージ発生の座標を設定するとX軸が等しくY軸が固定の座標を返す() {
        Point2D expected = new Point2D(80, 70);

        obj.setOccurrenceSpecificationPoint(new Point2D(80, 50));
        Point2D actual = obj.getHeadCenterPoint();

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}