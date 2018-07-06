package io.github.morichan.retuss.retuss;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

/**
 * クラス図キャンバスにおけるノードに関するクラス
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
     * クラス図キャンバスにおける任意のポイントに、このノードを描画しているか否かの真偽値を取得する。
     *
     * @param x 任意のポイントのX軸
     * @param y 任意のポイントのY軸
     * @return 任意のポイントにこのノードを描画しているか否かの真偽値
     */
    abstract public boolean isAlreadyDrawnNode( double x, double y );

    /**
     * クラス図キャンバスにおいてノードを選択しているか否かの真偽値を設定する。
     *
     * @param isChosen ノードを選択しているか否かの真偽値
     */
    abstract public void setChosen( boolean isChosen );

    /**
     * クラス図キャンバスにおいてノードを生成、またはノードの内容を追加する。
     * 描画は行わない。
     *
     * @param type ノードの種類、またはノードの内容の種類
     * @param text ノード名、またはノードの内容のテキスト
     */
    abstract public void createNodeText( ContentType type, String text );

    /**
     * クラス図キャンバスにおいて描画済みのノードの内容を変更する。
     * ノード名の変更、またはノードの内容の変更ができる。
     *
     * @param type ノードの内容の種類
     * @param number ノードの内容が複数ある場合は、その番号
     * @param text ノードの内容のテキスト
     */
    abstract public void changeNodeText( ContentType type, int number, String text );

    /**
     * ノードの内容を削除する。
     * ノード自体の削除は、このインスタンスのリストを持つ {@link ClassDiagramDrawer} が行う。
     *
     * @param type ノードの内容の種類
     * @param number ノードの内容の番号
     */
    abstract public void deleteNodeText( ContentType type, int number );

    /**
     * ノードの任意の内容のテキストを取得する。
     *
     * @param type ノードの内容の種類
     * @param number ノードの内容の番号
     * @return ノードの内容のテキスト
     */
    abstract public String getNodeContentText( ContentType type, int number );

    /**
     * ノードの任意の内容のテキストのリストを取得する。
     *
     * @param type ノードの内容の種類
     * @return ノードの内容のテキストのリスト
     */
    abstract public List< String > getNodeContents( ContentType type );

    /**
     * ノードの任意の種類の任意の内容に真偽値を設定する。
     * 正確には、ノードの任意の種類 {@code type} における番号 {@code contentNumber} の内容の種類 {@code subtype} に真偽値 {@code isChecked} を設定する。
     * 例えば、クラス図の属性の表示しているか否かを設定する場合は、 {@code type} に {@link ContentType#Attribute} 、 {@code subtype} に {@link ContentType#Indication} を引数に入れる。
     *
     * @param type ノードの任意の種類
     * @param subtype 内容の種類
     * @param contentNumber ノードの任意の番号
     * @param isChecked ノードの内容の真偽値
     */
    abstract public void setNodeContentBoolean( ContentType type, ContentType subtype, int contentNumber, boolean isChecked );

    /**
     * ノードの任意の種類の任意の内容の真偽値のリストを取得する。
     * 例えば、クラス図の属性の表示しているか否かのリストを取得する場合は、 {@code type} に {@link ContentType#Attribute} 、 {@code subtype} に {@link ContentType#Indication} を引数に入れる。
     *
     * @param type ノードの任意の種類
     * @param subtype 内容の種類
     * @return ノードの任意の種類の任意の内容の真偽値のリスト
     */
    abstract public List< Boolean > getNodeContentsBoolean( ContentType type, ContentType subtype );

    /**
     * クラス図キャンバスにおいてノードを描画する。
     *
     * {@link NodeDiagram#gc} が存在しない場合は {@link NullPointerException} を返す。
     */
    abstract public void draw();

    NodeDiagram() {
        nodeId = nodeCount;
        nodeCount++;
    }

    /**
     * 静的カウント変数をリセットする。
     * テストコードで主に用いる。
     */
    static void resetNodeCount() {
        nodeCount = 0;
    }

    /**
     * {@link ClassDiagramDrawer} クラスから {@link javafx.scene.canvas.Canvas} クラスのインスタンスが持つ {@link GraphicsContext} クラスのインスタンスを受け取る。
     * 受け取るのは最初にインスタンスを生成した時である。
     *
     * @param gc {@link Controller} クラスから受け取るグラフィックスコンテキスト
     */
    public void setGraphicsContext( GraphicsContext gc ) {
        this.gc = gc;
    }

    /**
     * クラス図キャンバスにおいて操作しているマウスの位置を受け取る。
     *
     * @param x マウスのX軸
     * @param y マウスのY軸
     */
    public void setMouseCoordinates( double x, double y ) {
        currentPoint = new Point2D( x, y );
    }

    /**
     * ノードの内容のテキストを取得する。
     *
     * @return ノードの内容のテキスト
     */
    public String getNodeText() {
        return nodeText;
    }

    /**
     * ノードの種類を取得する。
     *
     * @return ノードの種類
     */
    public ContentType getNodeType() {
        return ContentType.Class;
    }

    /**
     * ノードのIDを取得する。
     * ここにおけるIDとは、ノードを生成した順番に1ずつ増加する整数である。
     *
     * @return ノードのID
     */
    public int getNodeId() {
        return nodeId;
    }

    /**
     * クラス図キャンバスにおけるノードのポイント（中心点）を取得する。
     *
     * @return ノードのポイント
     */
    public Point2D getPoint() {
        return currentPoint;
    }

    /**
     * ノードの描画している幅（横幅）を返す。
     *
     * @return ノードの幅
     */
    public double getWidth() {
        return width;
    }

    /**
     * ノードの描画している高さ（縦幅）を返す。
     *
     * @return ノードの高さ
     */
    public double getHeight() {
        return height;
    }
}
