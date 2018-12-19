package io.github.morichan.retuss.window.diagram.sequence;

import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.window.diagram.OperationGraphic;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

/**
 * <p> ライフラインを表現するクラス </p>
 *
 * <p>
 * シーケンス図におけるライフラインをJavaFXで表現するクラスです。
 * ライフラインの詳細は、UMLの仕様書を参照してください。
 * </p>
 *
 * <p>
 * ライフラインは、次の要素を保持しています。
 * </p>
 *
 * <ul>
 * <li> 頭部にあたるIDを記した矩形 </li>
 * <li> 生存期間を表す縦線 </li>
 * </ul>
 */
public class Lifeline {

    private Point2D headCenterPoint = new Point2D(0, 70.0);
    private Class umlClass;
    private OperationGraphic op;

    /**
     * <p> ライフラインの頭部にあたるIDを記した矩形の中央座標を取得します </p>
     *
     * @return ライフラインの頭部にあたるIDを記した矩形の中央座標
     */
    public Point2D getHeadCenterPoint() {
        return headCenterPoint;
    }

    /**
     * <p> ライフラインに接続するメッセージの終点の座標を設定します </p>
     *
     * <p>
     * これにより、 {@link #headCenterPoint} の座標を計算する {@link #calculateHeadCenterPoint(Point2D)} を呼出します。
     * </p>
     *
     * @param point ライフラインに接続するメッセージの終点の座標
     */
    public void setOccurrenceSpecificationPoint(Point2D point) {
        calculateHeadCenterPoint(point);
    }

    /**
     * <p> ライフラインに接続するメッセージの終点の座標から {@link #headCenterPoint} を計算します </p>
     *
     * <p>
     * ライフラインに接続するメッセージの終点の座標におけるX軸と等しいX軸、かつ
     * 以前のライフラインの頭部にあたるIDを記した矩形の中央座標のY軸を設定します。
     * </p>
     *
     * @param point ライフラインに接続するメッセージの終点の座標
     */
    private void calculateHeadCenterPoint(Point2D point) {
        headCenterPoint = new Point2D(point.getX(), headCenterPoint.getY());
    }

    public void setUmlClass(Class umlClass) {
        this.umlClass = umlClass;
    }

    public void setOperationGraphic(OperationGraphic op) {
        this.op = op;
    }

    public void draw(GraphicsContext gc) {
        double lifelineNameFontSize = 15.0;
        String diagramFont = "Consolas";

        Text lifelineNameText = new Text(": " + umlClass.getName());
        lifelineNameText.setFont(Font.font(diagramFont, FontWeight.LIGHT, lifelineNameFontSize));
        double maxWidth = lifelineNameText.getLayoutBounds().getWidth() + 10.0;
        double maxHeight = 30.0;
        Point2D topLeftCorner = calculateTopLeftCorner(maxWidth);
        Point2D bottomRightCorner = calculateBottomRightCorner(maxWidth);

        gc.setFill(Color.BEIGE);
        gc.fillRect(topLeftCorner.getX(), topLeftCorner.getY(), maxWidth, maxHeight);

        gc.setStroke(Color.BLACK);
        gc.strokeRect(topLeftCorner.getX(), topLeftCorner.getY(), maxWidth, maxHeight);

        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(lifelineNameText.getFont());
        gc.fillText(lifelineNameText.getText(), headCenterPoint.getX(), headCenterPoint.getY());
        gc.strokeLine(topLeftCorner.getX() + 5.0, bottomRightCorner.getY() - 8.0, bottomRightCorner.getX() - 5.0, bottomRightCorner.getY() - 8.0);

        gc.setLineDashes(10.0, 10.0);
        gc.strokeLine(headCenterPoint.getX(), bottomRightCorner.getY(), headCenterPoint.getX(), bottomRightCorner.getY() + 500.0);
        gc.setLineDashes(null);
    }

    private Point2D calculateTopLeftCorner(double maxWidth) {
        return new Point2D(headCenterPoint.getX() - maxWidth / 2, headCenterPoint.getY() - 20.0);
    }

    private Point2D calculateBottomRightCorner(double maxWidth) {
        return new Point2D(headCenterPoint.getX() + maxWidth / 2, headCenterPoint.getY() + 10.0);
    }
}
