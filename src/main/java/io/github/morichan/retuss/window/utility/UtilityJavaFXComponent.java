package io.github.morichan.retuss.window.utility;

import io.github.morichan.retuss.window.ClassDiagramDrawer;
import io.github.morichan.retuss.window.diagram.ContentType;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> JavaFXコンポーネントのユーティリティクラス </p>
 *
 * <p>
 *     JavaFXコンポーネントにまつわる様々なユーティリティを提供します。
 * </p>
 */
public class UtilityJavaFXComponent {

    /**
     * <p> 表示しているボタンの内、選択したボタンをtrueに、それ以外のボタンをfalseにします </p>
     *
     * @param buttons ボタンリスト
     * @param buttonSetsToTrue trueにセットするボタン
     * @return 1つだけ {@code true} になっているボタンリスト
     */
    public List<Button> bindAllButtonsFalseWithout(List<Button> buttons, Button buttonSetsToTrue) {
        for (Button button : buttons) {
            if (button.equals(buttonSetsToTrue)) {
                button.setDefaultButton(true);
            } else {
                button.setDefaultButton(false);
            }
        }

        return buttons;
    }

    /**
     * <p> 選択されているボタンを返します </p>
     *
     * @param buttons ボタンリスト（1つだけが {@code true} になっている）
     * @return ボタンリストのうち {@code true} になっているボタン
     */
    public Button searchSelectedButtonIn(List<Button> buttons) {
        int count = 0;

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isDefaultButton()) {
                count = i;
                break;
            }
        }
        return buttons.get(count);
    }

    /**
     * <p> {@link ClassDiagramDrawer} クラスにおけるエッジの種類を元にコンテキストメニューを作成します </p>
     *
     * @param edgeName エッジの関連端名（{@link ContentType#Composition} の場合は空文字）
     * @param edgeType エッジの種類
     * @return エッジの種類に応じたコンテキストメニュー <br> {@code null} なし
     */
    public ContextMenu createClassContextMenuInCD(String edgeName, ContentType edgeType) {
        ContextMenu popup = new ContextMenu();

        if (edgeType == ContentType.Composition) {
            popup.getItems().add(new MenuItem(edgeName + " の変更"));
            popup.getItems().add(new MenuItem(edgeName + " の削除"));
        } else if (edgeType == ContentType.Generalization) {
            popup.getItems().add(new MenuItem("汎化の削除"));
        }

        return popup;
    }

    /**
     * <p> {@link ClassDiagramDrawer} クラスにおけるノード名の種類を元にコンテキストメニューを作成します </p>
     *
     * @param nodeName ノード名 （タイトルや内容など）
     * @param nodeType ノードの種類
     * @param nodeContents1 ノードが持つコンテンツの文字列リスト1
     * @param nodeContents2 ノードが持つコンテンツの文字列リスト2
     * @param nodeContents3 ノードが持つコンテンツの文字列リスト3
     * @param nodeContents4 ノードが持つコンテンツの文字列リスト4
     * @return ノードの種類に応じたコンテキストメニュー <br> {@code null} なし
     */
    public ContextMenu createClassContextMenuInCD(String nodeName, ContentType nodeType, List<String> nodeContents1, List<String> nodeContents2, List<Boolean> nodeContents3, List<Boolean> nodeContents4) {
        ContextMenu popup = new ContextMenu();

        if ( nodeType == ContentType.Class ) {
            popup.getItems().add(new MenuItem(nodeName + "クラスの名前の変更"));
            popup.getItems().add(new MenuItem(nodeName + "クラスをモデルから削除"));
            popup.getItems().add(new SeparatorMenuItem());

            Menu attributeMenu = new Menu("属性");
            Menu operationMenu = new Menu("操作");
            MenuItem addAttributeMenuItem = new MenuItem("追加");
            Menu changeAttributeMenu = new Menu("変更");
            Menu deleteAttributeMenu = new Menu("削除");
            Menu displayAttributeMenu = new Menu("表示選択");
            MenuItem addOperationMenuItem = new MenuItem("追加");
            Menu changeOperationMenu = new Menu("変更");
            Menu deleteOperationMenu = new Menu("削除");
            Menu displayOperationMenu = new Menu("表示選択");

            if (nodeContents1.size() > 0) {
                for (int i = 0; i < nodeContents1.size(); i++) {
                    changeAttributeMenu.getItems().add(new MenuItem(nodeContents1.get(i)));
                    deleteAttributeMenu.getItems().add(new MenuItem(nodeContents1.get(i)));
                    CheckMenuItem checkMenuItem = new CheckMenuItem(nodeContents1.get(i));
                    checkMenuItem.setSelected(nodeContents3.get(i));
                    displayAttributeMenu.getItems().add(checkMenuItem);
                }
            } else {
                MenuItem hasNotChangeAttributeMenuItem = new MenuItem("なし");
                MenuItem hasNotDeleteAttributeMenuItem = new MenuItem("なし");
                CheckMenuItem hasNotDisplayAttributeCheckMenuItem = new CheckMenuItem("なし");
                hasNotChangeAttributeMenuItem.setDisable(true);
                hasNotDeleteAttributeMenuItem.setDisable(true);
                hasNotDisplayAttributeCheckMenuItem.setDisable(true);
                changeAttributeMenu.getItems().add(hasNotChangeAttributeMenuItem);
                deleteAttributeMenu.getItems().add(hasNotDeleteAttributeMenuItem);
                displayAttributeMenu.getItems().add(hasNotDisplayAttributeCheckMenuItem);
            }

            if (nodeContents2.size() > 0) {
                for (int i = 0; i < nodeContents2.size(); i++) {
                    changeOperationMenu.getItems().add(new MenuItem(nodeContents2.get(i)));
                    deleteOperationMenu.getItems().add(new MenuItem(nodeContents2.get(i)));
                    CheckMenuItem checkMenuItem = new CheckMenuItem(nodeContents2.get(i));
                    checkMenuItem.setSelected(nodeContents4.get(i));
                    displayOperationMenu.getItems().add(checkMenuItem);
                }
            } else {
                MenuItem hasNotChangeOperationMenuItem = new MenuItem("なし");
                MenuItem hasNotDeleteOperationMenuItem = new MenuItem("なし");
                CheckMenuItem hasNotDisplayOperationCheckMenuItem = new CheckMenuItem("なし");
                hasNotChangeOperationMenuItem.setDisable(true);
                hasNotDeleteOperationMenuItem.setDisable(true);
                hasNotDisplayOperationCheckMenuItem.setDisable(true);
                changeOperationMenu.getItems().add(hasNotChangeOperationMenuItem);
                deleteOperationMenu.getItems().add(hasNotDeleteOperationMenuItem);
                displayOperationMenu.getItems().add(hasNotDisplayOperationCheckMenuItem);
            }

            attributeMenu.getItems().add(addAttributeMenuItem);
            attributeMenu.getItems().add(changeAttributeMenu);
            attributeMenu.getItems().add(deleteAttributeMenu);
            attributeMenu.getItems().add(displayAttributeMenu);
            operationMenu.getItems().add(addOperationMenuItem);
            operationMenu.getItems().add(changeOperationMenu);
            operationMenu.getItems().add(deleteOperationMenu);
            operationMenu.getItems().add(displayOperationMenu);

            popup.getItems().add(attributeMenu);
            popup.getItems().add(operationMenu);
        } else {
            //popup.getItems().add( new MenuItem( "内容の変更" ) );
            //popup.getItems().add( new MenuItem( "モデルから削除" ) );
        }

        return popup;
    }

    /**
     * <p> 任意の点が多角形の内部に存在しているか否かを確認します </p>
     *
     * <p>
     *     アルゴリズムはWinding Number Algorithmを利用しています。
     *     また、多角形の点上に存在する点は多角形内部に存在するとして真を返します。
     * </p>
     *
     * <p>
     *     Winding Number Algorithmは、Crossing Number Algorithmの欠点である多角形の自己交差内における内外判定を克服したアルゴリズムです。
     *     多角形の自己交差とは、次のような多角形の点Aが存在する領域を示します。
     * </p>
     *
     * <p>
     *     凡例
     * </p>
     *
     * <ul>
     *     <li> - : 多角形の領域外（多角形が作る穴 == Holeは除く） </li>
     *     <li> O : 多角形の領域内（自己交差内は除く） </li>
     *     <li> X : 多角形が作る穴 == Hole（多角形の領域外） </li>
     *     <li> A : 多角形の自己交差内の領域（多角形の領域内） </li>
     * </ul>
     *
     * <p>
     *     多角形の自己交差の図形 <br>
     *     --- --- --- --- --- --- <br>
     *     --- --- OOO --- --- --- <br>
     *     --- OOO AAA OOO OOO --- <br>
     *     --- --- OOO XXX OOO --- <br>
     *     --- --- OOO OOO OOO --- <br>
     *     --- --- --- --- --- --- <br>
     * </p>
     *
     * <p>
     *     この時、XXXは範囲外であるがAAAは範囲内であるという判定が一般的です。
     *     しかし、Crossing Number AlgorithmではAAAを範囲外として判定してしまいます。
     *     Winding Number AlgorithmではAAAを範囲内として判定します。
     * </p>
     *
     * <p>
     *     また、Winding Number Algorithmは本来であれば逆三角関数を使うため計算コストの点で効果ですが、このメソッドでは単純な座標軸の正負の向きによりカウント変数を増減することで判定します。
     *     そのため、Crossing Number Algorithmとほぼ変わらない計算コストにより判定を行います。
     * </p>
     *
     * @param targetPolygon 多角形の各点のXY軸のポイントのリスト
     * @param targetPoint 多角形の内部に存在するか否かを確認する対象のポイント
     * @return 任意の点が多角形の内部に存在しているか否か <br> 要素が3未満の場合はfalseを返す。
     */
    public boolean isInsidePointFromPolygonUsingWNA(List<Point2D> targetPolygon, Point2D targetPoint) {
        boolean isInsidePoint = false;
        int wn = 0;
        List<Point2D> polygon = new ArrayList<>();

        // 多角形でない場合
        if (targetPolygon.size() < 3) return false;

        for (Point2D point : targetPolygon) {
            polygon.add(point);
            if(point.equals(targetPoint)) return true; // ターゲット点が多角形の点と等しい場合
        }

        // 多角形における最後の点と最初の点が異なる場合（例えば四角形ABCDの点配列は ABCDA でなければならないため）
        if (! targetPolygon.get(0).equals(targetPolygon.get(targetPolygon.size() - 1))) polygon.add(targetPolygon.get(0));

        /*
         * ルール1.　上向きの辺は、開始点を含み終点を含まない。
         * ルール2.　下向きの辺は、開始点を含まず終点を含む。
         * ルール3.　水平線Rと辺Snが水平でない (SnがRと重ならない) 。
         * ルール4.　水平線Rと辺Snの交点は厳密に点Pの右側になくてはならない。
         */
        for (int i = 1; i < polygon.size(); i++) {
            // 上向きの辺、下向きの辺によって処理が分かれる。
            // 上向きの辺。点Pがy軸方向について、始点と終点の間にある。ただし、終点は含まない。(ルール1)
            if ((polygon.get(i - 1).getY() <= targetPoint.getY()) && (polygon.get(i).getY() > targetPoint.getY())) {
                // 辺は点pよりも右側にある。ただし、重ならない。(ルール4)
                // 辺が点pと同じ高さになる位置を特定し、その時のxの値と点pのxの値を比較する。
                double vt = (targetPoint.getY() - polygon.get(i - 1).getY()) / (polygon.get(i).getY() - polygon.get(i - 1).getY());

                if (targetPoint.getX() < (polygon.get(i - 1).getX() + (vt * (polygon.get(i).getX() - polygon.get(i - 1).getX())))) {
                    ++wn;  // ここが重要。上向きの辺と交差した場合は +1
                }
            }
            // 下向きの辺。点Pがy軸方向について、始点と終点の間にある。ただし、始点は含まない。(ルール2)
            else if ((polygon.get(i - 1).getY() > targetPoint.getY()) && (polygon.get(i).getY() <= targetPoint.getY())) {
                // 辺は点pよりも右側にある。ただし、重ならない。(ルール4)
                // 辺が点pと同じ高さになる位置を特定し、その時のxの値と点pのxの値を比較する。
                double vt = (targetPoint.getY() - polygon.get(i - 1).getY()) / (polygon.get(i).getY() - polygon.get(i - 1).getY());

                if (targetPoint.getX() < (polygon.get(i - 1).getX() + (vt * (polygon.get(i).getX() - polygon.get(i - 1).getX())))) {
                    --wn;  // ここが重要。下向きの辺と交差した場合は -1
                }
            }
            // ルール1,ルール2を確認することで、ルール3も確認できている。
        }

        if (wn != 0) isInsidePoint = true;

        return isInsidePoint;
    }

    /**
     * <p> 任意の点が多角形の内部に存在しているか否かを確認します </p>
     *
     * <p>
     *     内側の定義やアルゴリズムは{@link java.awt.Polygon#contains(int, int)}に依存します。
     *     また、多角形の境界線上に存在する点においては別のアルゴリズムを用いて存在しているとして真を返します。
     * </p>
     *
     * @param polygonPoints 多角形の各点のXY軸のポイントのリスト
     * @param targetPoint 多角形の内部に存在するか否かを確認する対象のポイント
     * @return 任意の点が多角形の内部に存在しているか否か <br> 要素が3未満の場合はfalseを返します。
     *
     * @deprecated 現在このメソッドは、次の点により使用を推奨しません。
     *
     * <ul>
     *     <li> {@link Polygon} クラスの各 {@code contains} メソッドのアルゴリズムが不明瞭である点（特に {@link java.awt.Shape} に記述している判定計算の負荷が非常に大きい場合は {@code false} を返す恐れがあるとの解釈が不安材料） </li>
     *     <li> {@link Polygon#contains(int, int)} の引数が {@code int} である点（ {@link javafx.geometry.Point2D} のXY軸のポイントはどちらも {@code double} 型） </li>
     *     <li> {@link javafx.scene.shape.Polygon} クラスではなく {@link java.awt.Polygon} を使っており名称被りを起こす可能性がある点（もしこのクラス内で {@link javafx.scene.shape.Polygon} を用いることがあればこのメソッドを削除してよい） </li>
     *     <li> 多角形の自己交差内のポイントを多角形外に存在すると判定する点（Crossing Number Algorithmを用いていると予測するが正確なアルゴリズムは不明） </li>
     * </ul>
     *
     * <p>
     *     今後は {@link #isInsidePointFromPolygonUsingWNA(List, Point2D)} の利用を推奨します。
     * </p>
     */
    @Deprecated
    public boolean isInsidePointFromPolygonUsingJavaAwtPolygonMethod(List<Point2D> polygonPoints, Point2D targetPoint) {
        boolean isInside;
        boolean isOnLine = false;
        boolean isOnEdge = false;

        // 多角形でない場合
        if (polygonPoints.size() < 3) return false;

        Polygon polygon = new Polygon();
        for (Point2D point : polygonPoints) {
            polygon.addPoint((int) point.getX(), (int) point.getY());
        }

        for (int i = 1; i < polygonPoints.size(); i++) {
            Point2D startPoint = polygonPoints.get(i - 1);
            Point2D endPoint = polygonPoints.get(i);
            if (startPoint.getX() > endPoint.getX()) {
                Point2D tmp = startPoint;
                startPoint = endPoint;
                endPoint = tmp;
            }
            if (startPoint.equals(targetPoint) || endPoint.equals(targetPoint)) isOnEdge = true; // ターゲット点が多角形の点と等しい場合

            isOnLine = startPoint.getX() <= targetPoint.getX() && targetPoint.getX() <= endPoint.getX() &&
                    ((startPoint.getY() <= endPoint.getY() && startPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= endPoint.getY()) ||
                            (startPoint.getY() > endPoint.getY() && endPoint.getY() <= targetPoint.getY() && targetPoint.getY() <= startPoint.getY()))
                    && (targetPoint.getY() - startPoint.getY()) * (endPoint.getX() - startPoint.getX()) == (endPoint.getY() - startPoint.getY()) * (targetPoint.getX() - startPoint.getX());

            if (isOnLine) break;
            if (isOnEdge) break;
        }

        if (isOnLine || isOnEdge) {
            isInside = true;
        } else {
            isInside = polygon.contains(targetPoint.getX(), targetPoint.getY());
        }

        return isInside;
    }

    /**
     * <p> ノードの頂点のリストを計算します </p>
     *
     * <p> リストは右上から時計回り4隅です。 </p>
     *
     * @param center ノードの中心のポイント
     * @param width ノードの幅
     * @param height ノードの高さ
     * @return ノードの頂点のリスト <br> sizeは4限定とします。
     */
    public List<Point2D> calculateTopListFromNode(Point2D center, double width, double height) {
        List<Point2D> tops = new ArrayList<>();
        double halfWidth = width / 2;
        double halfHeight = height / 2;

        tops.add(new Point2D(center.getX() + halfWidth, center.getY() - halfHeight)); // 右上
        tops.add(new Point2D(center.getX() + halfWidth, center.getY() + halfHeight)); // 右下
        tops.add(new Point2D(center.getX() - halfWidth, center.getY() + halfHeight)); // 左下
        tops.add(new Point2D(center.getX() - halfWidth, center.getY() - halfHeight)); // 左上

        return tops;
    }

    /**
     * <p> 2つの線分の交点を計算します </p>
     *
     * <p>
     *     一部のアルゴリズムは <a href="https://www.sist.ac.jp/~suganuma/index.html"> 静岡理工科大学の菅沼研究室のサイト </a> の
     *     <a href="https://www.sist.ac.jp/~suganuma/cpp/2-bu/7-sho/Java/basic_j.htm#point_line_re"> 基本アルゴリズム（その1） </a> を採用しています。
     *     一部出典元より変数名などの変更があります。
     * </p>
     *
     * @param firstLineStartPoint 1つ目の線分の始点
     * @param firstLineEndPoint 1つ目の線分の終点
     * @param secondLineStartPoint 2つ目の線分の始点
     * @param secondLineEndPoint 2つ目の線分の終点
     * @return
     * <p>
     *     2つの線分の交点のポイント<br>
     *     片方の線分の始点または終点が、もう片方の線上あるいは始点または終点に存在する場合は、そのポイントを返します。
     *     交点が存在しない場合は {@code null} を返します。
     * </p>
     */
    public Point2D calculateCrossPoint(Point2D firstLineStartPoint, Point2D firstLineEndPoint, Point2D secondLineStartPoint, Point2D secondLineEndPoint) {
        Point2D cross = null;

        // 線分の始点または終点が同じ位置にある場合
        if (firstLineStartPoint.equals(secondLineStartPoint)) {
            cross = new Point2D(firstLineStartPoint.getX(), firstLineStartPoint.getY());
        } else if (firstLineStartPoint.equals(secondLineEndPoint)) {
            cross = new Point2D(firstLineStartPoint.getX(), firstLineStartPoint.getY());
        } else if (firstLineEndPoint.equals(secondLineStartPoint)) {
            cross = new Point2D(firstLineEndPoint.getX(), firstLineEndPoint.getY());
        } else if (firstLineEndPoint.equals(secondLineEndPoint)) {
            cross = new Point2D(firstLineEndPoint.getX(), firstLineEndPoint.getY());

        } else {
            // 基本アルゴリズム（その1）より採用した箇所
            double EPS = 1.0e-10;
            Point2D subtractPointFirstFromSecondPoint = new Point2D(secondLineStartPoint.getX() - firstLineStartPoint.getX(), secondLineStartPoint.getY() - firstLineStartPoint.getY());
            double denominator = (firstLineEndPoint.getX() - firstLineStartPoint.getX() ) * ( secondLineEndPoint.getY() - secondLineStartPoint.getY())
                    - (firstLineEndPoint.getY() - firstLineStartPoint.getY()) * (secondLineEndPoint.getX() - secondLineStartPoint.getX());
            if (Math.abs(denominator) > EPS) {
                double r = ((secondLineEndPoint.getY() - secondLineStartPoint.getY()) * subtractPointFirstFromSecondPoint.getX() - (secondLineEndPoint.getX() - secondLineStartPoint.getX()) * subtractPointFirstFromSecondPoint.getY()) / denominator;
                double s = ((firstLineEndPoint.getY() - firstLineStartPoint.getY()) * subtractPointFirstFromSecondPoint.getX() - (firstLineEndPoint.getX() - firstLineStartPoint.getX()) * subtractPointFirstFromSecondPoint.getY()) / denominator;
                if(r > -EPS && r < 1.0 + EPS && s > -EPS && s < 1.0 + EPS)
                    cross = new Point2D(firstLineStartPoint.getX() + r * (firstLineEndPoint.getX() - firstLineStartPoint.getX()), firstLineStartPoint.getY() + r * (firstLineEndPoint.getY() - firstLineStartPoint.getY()));
            }
        }

        return cross;
    }

    /**
     * <p> 2つの線分に交点が存在するか否かを確認します。 </p>
     *
     * <p>
     *     2つの線分が交わっている場合は真を返します。
     *     2つの線分のうち、あるいは両方とも線分の端が接している場合は真を返します。
     * </p>
     *
     * <p>
     *     アルゴリズムは <a href="https://www.sist.ac.jp/~suganuma/index.html"> 静岡理工科大学の菅沼研究室のサイト </a> の
     *     <a href="https://www.sist.ac.jp/~suganuma/cpp/2-bu/7-sho/Java/basic_j.htm#point_line_re"> 基本アルゴリズム（その1） </a> を採用しています。
     * </p>
     *
     * @param firstLineStartPoint 1つ目の線分の始点
     * @param firstLineEndPoint 1つ目の線分の終点
     * @param secondLineStartPoint 2つ目の線分の始点
     * @param secondLineEndPoint 2つ目の線分の終点
     * @return 真偽値 <br> 交点の存在の有無の定義は説明文の通りです。
     */
    public boolean isIntersected(Point2D firstLineStartPoint, Point2D firstLineEndPoint, Point2D secondLineStartPoint, Point2D secondLineEndPoint) {
        boolean isIntersected = false;

        if (ccw(firstLineStartPoint, firstLineEndPoint, secondLineStartPoint) * ccw(firstLineStartPoint, firstLineEndPoint, secondLineEndPoint) <= 0
                && ccw(secondLineStartPoint, secondLineEndPoint, firstLineStartPoint) * ccw(secondLineStartPoint, secondLineEndPoint, firstLineEndPoint) <= 0)
            isIntersected = true;

        return isIntersected;
    }

    /**
     * <p> 点と線分の関係を算出します </p>
     *
     * <p>
     *     アルゴリズムは <a href="https://www.sist.ac.jp/~suganuma/index.html"> 静岡理工科大学の菅沼研究室のサイト </a> の
     *     <a href="https://www.sist.ac.jp/~suganuma/cpp/2-bu/7-sho/Java/basic_j.htm#point_line_re"> 基本アルゴリズム（その1） </a> を採用しています。
     *     一部出典元より変数名などの変更があります。
     * </p>
     *
     * @param lineStartPoint 線分の始点
     * @param lineEndPoint 線分の終点
     * @param anyPoint 線分との関係を算出する任意のポイント
     * @return 点と線分の関係
     *     <ul>
     *         <li> 1 == 任意のポイントは線分の反時計方向に存在 </li>
     *         <li> -1 == 任意のポイントは線分の時計方向に存在 </li>
     *         <li> 2 == 任意のポイントは線分の手前に存在 </li>
     *         <li> -2 == 任意のポイントは線分の先に存在 </li>
     *         <li> 0 == 任意のポイントは線分上に存在 </li>
     *     </ul>
     */
    private int ccw(Point2D lineStartPoint, Point2D lineEndPoint, Point2D anyPoint) {
        int sw = 0;   // 点p2は線分p0-p1の上
        double EPS = 1.0e-10;

        Point2D a = new Point2D(lineEndPoint.getX() - lineStartPoint.getX(), lineEndPoint.getY() - lineStartPoint.getY());
        Point2D b = new Point2D(anyPoint.getX() - lineStartPoint.getX(), anyPoint.getY() - lineStartPoint.getY());

        if (cross(a, b) > EPS)   // 点p2は線分p0-p1の反時計方向
            sw = 1;
        else if (cross(a, b) < -EPS)   // 点p2は線分p0-p1の時計方向
            sw = -1;
        else if (dot(a, b) < -EPS)   // 点p2は線分p0-p1の手前
            sw = 2;
        else if (norm(a) < norm(b))   // 点p2は線分p0-p1の先
            sw = -2;

        return sw;
    }

    /**
     * <p> 2つのベクトルの外積（z成分）を計算します </p>
     *
     * <p>
     *     アルゴリズムは <a href="https://www.sist.ac.jp/~suganuma/index.html"> 静岡理工科大学の菅沼研究室のサイト </a> の
     *     <a href="https://www.sist.ac.jp/~suganuma/cpp/2-bu/7-sho/Java/basic_j.htm#point_line_re"> 基本アルゴリズム（その1） </a> を採用しています。
     *     一部出典元より変数名などの変更があります。
     * </p>
     *
     * @param a 1つ目のベクトル
     * @param b 2つ目のベクトル
     * @return ベクトルの外積（z成分）
     */
    private double cross(Point2D a, Point2D b) {
        return a.getX() * b.getY() - a.getY() * b.getX();
    }

    /**
     * <p> 2つのベクトルの内積を計算します </p>
     *
     * <p>
     *     アルゴリズムは <a href="https://www.sist.ac.jp/~suganuma/index.html"> 静岡理工科大学の菅沼研究室のサイト </a> の
     *     <a href="https://www.sist.ac.jp/~suganuma/cpp/2-bu/7-sho/Java/basic_j.htm#point_line_re"> 基本アルゴリズム（その1） </a> を採用しています。
     *     一部出典元より変数名などの変更があります。
     * </p>
     *
     * @param a 1つ目のベクトル
     * @param b 2つ目のベクトル
     * @return ベクトルの内積
     */
    private double dot(Point2D a, Point2D b) {
        return a.getX() * b.getX() + a.getY() * b.getY();
    }

    /**
     * <p> ベクトルの大きさの二乗を計算します </p>
     *
     * <p>
     *     アルゴリズムは <a href="https://www.sist.ac.jp/~suganuma/index.html"> 静岡理工科大学の菅沼研究室のサイト </a> の
     *     <a href="https://www.sist.ac.jp/~suganuma/cpp/2-bu/7-sho/Java/basic_j.htm#point_line_re"> 基本アルゴリズム（その1） </a> を採用しています。
     *     一部出典元より変数名などの変更があります。
     * </p>
     *
     * @param x 任意のベクトル
     * @return 任意のベクトルの大きさの二乗
     */
    private double norm(Point2D x) {
        return x.getX() * x.getX() + x.getY() * x.getY();
    }
}
