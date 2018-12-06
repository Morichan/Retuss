package io.github.morichan.retuss.window.diagram.sequence;

import javafx.geometry.Point2D;

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

    private Point2D headCenterPoint = Point2D.ZERO;

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
}
