package io.github.morichan.retuss.window.diagram;

import io.github.morichan.retuss.window.ClassDiagramDrawer;
import io.github.morichan.retuss.window.MainController;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

/**
 * <p> クラス図キャンバスにおけるノードクラス </p>
 */
abstract public class NodeDiagram {
    private static int nodeCount = 0;
    protected static GraphicsContext gc = null;

    protected Point2D currentPoint = Point2D.ZERO;
    protected int nodeId;
    protected String nodeText = "";
    protected String diagramFont = "Consolas";
    protected boolean isChosen = false;

    protected double width = 0.0;
    protected double height = 0.0;

    /**
     * <p> クラス図キャンバスにおける任意のポイントに、このノードを描画しているか否かの真偽値を取得します </p>
     *
     * @param x 任意のポイントのX軸
     * @param y 任意のポイントのY軸
     * @return 任意のポイントにこのノードを描画しているか否かの真偽値
     */
    abstract public boolean isAlreadyDrawnNode(double x, double y);

    /**
     * <p> クラス図キャンバスにおける任意の座標に移動します </p>
     *
     * @param point 任意の座標
     */
    abstract public void moveTo(Point2D point);

    /**
     * <p> クラス図キャンバスにおいてノードを選択している場合は真を返す真偽値を設定します </p>
     *
     * @param isChosen ノードを選択しているか否かの真偽値
     */
    abstract public void setChosen(boolean isChosen);

    /**
     * <p> クラス図キャンバスにおいてノードを生成、またはノードの内容を追加します </p>
     *
     * <p> 描画は行いません。 </p>
     *
     * @param type ノードの種類またはノードの内容の種類
     * @param text ノード名またはノードの内容のテキスト
     */
    abstract public void createNodeText(ContentType type, String text);

    /**
     * <p> クラス図キャンバスにおいて描画済みのノードの内容を変更します </p>
     *
     * <p>
     *     ノード名の変更、またはノードの内容の変更ができます。
     * </p>
     *
     * @param type ノードの内容の種類
     * @param number ノードの内容が複数ある場合はその番号
     * @param text ノードの内容のテキスト
     */
    abstract public void changeNodeText(ContentType type, int number, String text);

    /**
     * <p> ノードの内容を削除する。 </p>
     *
     * <p>
     *     ノード自体の削除は、このインスタンスのリストを持つ {@link ClassDiagramDrawer} が行います。
     * </p>
     *
     * @param type ノードの内容の種類
     * @param number ノードの内容の番号
     */
    abstract public void deleteNodeText(ContentType type, int number);

    /**
     * <p> ノードの任意の内容のテキストを取得します </p>
     *
     * @param type ノードの内容の種類
     * @param number ノードの内容の番号
     * @return ノードの内容のテキスト
     */
    abstract public String getNodeContentText(ContentType type, int number);

    /**
     * <p> ノードの任意の内容のテキストのリストを取得します </p>
     *
     * @param type ノードの内容の種類
     * @return ノードの内容のテキストのリスト
     */
    abstract public List< String > getNodeContents(ContentType type);

    /**
     * <p> ノードの任意の種類の任意の内容に真偽値を設定します </p>
     *
     * <p>
     *     正確には、ノードの任意の種類 {@code type} における番号 {@code contentNumber} の内容の種類 {@code subtype} に真偽値 {@code isChecked} を設定します。
     *     例えば、クラス図の属性の表示しているか否かを設定する場合は、 {@code type} に {@link ContentType#Attribute} 、 {@code subtype} に {@link ContentType#Indication} を引数に入れます。
     * </p>
     *
     * @param type ノードの任意の種類
     * @param subtype 内容の種類
     * @param contentNumber ノードの任意の番号
     * @param isChecked ノードの内容の真偽値
     */
    abstract public void setNodeContentBoolean(ContentType type, ContentType subtype, int contentNumber, boolean isChecked);

    /**
     * <p> ノードの任意の種類の任意の内容の真偽値のリストを取得します </p>
     *
     * <p>
     *     例えば、クラス図の属性の表示しているか否かのリストを取得する場合は、 {@code type} に {@link ContentType#Attribute} 、 {@code subtype} に {@link ContentType#Indication} を引数に入れます。
     * </p>
     *
     * @param type ノードの任意の種類
     * @param subtype 内容の種類
     * @return ノードの任意の種類の任意の内容の真偽値のリスト
     */
    abstract public List< Boolean > getNodeContentsBoolean(ContentType type, ContentType subtype);

    /**
     * <p> クラス図キャンバスにおいてノードを描画します </p>
     *
     * {@link NodeDiagram#gc} が存在しない場合は {@link NullPointerException} を返す。
     */
    abstract public void draw();

    NodeDiagram() {
        nodeId = nodeCount;
        nodeCount++;
    }

    /**
     * <p> 静的カウント変数をリセットします </p>
     *
     * <p>
     *     テストコードで主に用います。
     * </p>
     */
    public static void resetNodeCount() {
        nodeCount = 0;
    }

    /**
     * <p> {@link ClassDiagramDrawer} クラスから {@link javafx.scene.canvas.Canvas} クラスのインスタンスが持つ {@link GraphicsContext} クラスのインスタンスを受け取ります </p>
     *
     * <p>
     *     受け取るのは最初にインスタンスを生成した時です。
     * </p>
     *
     * @param gc {@link MainController} クラスから受け取るグラフィックスコンテキスト
     */
    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * <p> クラス図キャンバスにおいて操作しているマウスの位置を受け取ります </p>
     *
     * @param x マウスのX軸
     * @param y マウスのY軸
     */
    public void setMouseCoordinates(double x, double y) {
        currentPoint = new Point2D(x, y);
    }

    /**
     * <p> ノードの内容のテキストを取得します </p>
     *
     * @return ノードの内容のテキスト
     */
    public String getNodeText() {
        return nodeText;
    }

    /**
     * <p> ノードの種類を取得します </p>
     *
     * @return ノードの種類
     */
    public ContentType getNodeType() {
        return ContentType.Class;
    }

    /**
     * <p> ノードのIDを取得します </p>
     *
     * <p>
     *     ここにおけるIDとは、ノードを生成した順番に1ずつ増加する整数です。
     * </p>
     *
     * @return ノードのID
     */
    public int getNodeId() {
        return nodeId;
    }

    /**
     * <p> クラス図キャンバスにおけるノードのポイント（中心点）を取得します </p>
     *
     * @return ノードのポイント
     */
    public Point2D getPoint() {
        return currentPoint;
    }

    /**
     * <p> ノードの描画している幅（横幅）を返します </p>
     *
     * @return ノードの幅
     */
    public double getWidth() {
        return width;
    }

    /**
     * <p> ノードの描画している高さ（縦幅）を返します </p>
     *
     * @return ノードの高さ
     */
    public double getHeight() {
        return height;
    }
}
