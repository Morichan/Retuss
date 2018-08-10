package io.github.morichan.retuss.window;

import io.github.morichan.retuss.window.diagram.*;
import io.github.morichan.retuss.window.utility.UtilityJavaFXComponent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;

/**
 * <p> クラス図描画クラス </p>
 *
 * <p>
 * このクラスは、クラス図を描画する際にキャンバスの操作などを行うクラスです。
 * </p>
 */
public class ClassDiagramDrawer {
    GraphicsContext gc;
    private List<NodeDiagram> nodes = new ArrayList<>();
    private EdgeDiagram relations = new EdgeDiagram();

    private int currentNodeNumber = -1;
    private double mouseX = 0.0;
    private double mouseY = 0.0;
    private String nodeText = "";
    private ContentType nowStateType = ContentType.Undefined;

    /**
     * 操作中のノード番号を返す。
     *
     * @return 操作中のノード番号 何も操作していない場合は -1 を返す。
     */
    public int getCurrentNodeNumber() {
        return currentNodeNumber;
    }

    /**
     * {@link Controller} クラスからretussMain.fxmlの {@link javafx.scene.canvas.Canvas} クラスのインスタンスが持つ {@link GraphicsContext} クラスのインスタンスを受け取り、 {@link EdgeDiagram} クラスのインスタンスに渡す。
     * また、キャンバスの縁を描画する。
     *
     * @param gc {@link Controller} クラスから受け取るグラフィックスコンテキスト
     */
    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
        relations.setGraphicsContext(this.gc);
        drawDiagramCanvasEdge();
    }

    /**
     * クラス図キャンバスにおいて操作しているマウスの位置を受け取る。
     *
     * @param x マウスのX軸
     * @param y マウスのY軸
     */
    public void setMouseCoordinates(double x, double y) {
        mouseX = x;
        mouseY = y;
    }

    /**
     * クラス図においてダイアログなどで取得したテキストを受け取る。
     *
     * @param text 名前や内容の文字列
     */
    public void setNodeText(String text) {
        nodeText = text;
    }

    /**
     * クラス図キャンバスにおける全てのノード、エッジおよびキャンバスの縁を描画する。
     * 上書きするのを防ぐために、最初にキャンバスをまっさらにする。
     */
    public void allReDrawCanvas() {
        gc.clearRect(0.0, 0.0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        drawDiagramCanvasEdge();
        allReDrawEdge();
        allReDrawNode();
    }

    /**
     * クラス図キャンバスにおける全てのノードを描画する。
     */
    public void allReDrawNode() {
        for (int i = 0; i < nodes.size(); i++) {
            drawNode(i);
        }
    }

    /**
     * クラス図キャンバスにおける全てのエッジ（関係）を描画する。
     */
    public void allReDrawEdge() {
        for (int i = 0; i < relations.getCompositionsCount(); i++) {
            int relationId = nodes.get(relations.getRelationId(ContentType.Composition, i)).getNodeId();
            int relationSourceId = nodes.get(relations.getRelationSourceId(ContentType.Composition, i)).getNodeId();
            double relationWidth = nodes.get(relationId).getWidth();
            double relationHeight = nodes.get(relationId).getHeight();
            double relationSourceWidth = nodes.get(relationSourceId).getWidth();
            double relationSourceHeight = nodes.get(relationSourceId).getHeight();

            relations.draw(relationWidth, relationHeight, relationSourceWidth, relationSourceHeight, i);
        }
    }

    /**
     * クラス図キャンバスにおけるノードの初期化を行う。
     *
     * @param number 任意のノード番号
     */
    public void setupDrawnNode(int number) {
        if (nodeText.length() <= 0) return;

        nodes.get(number).setGraphicsContext(gc);
        nodes.get(number).setMouseCoordinates(mouseX, mouseY);
        nodes.get(number).createNodeText(ContentType.Title, nodeText);
        nodes.get(number).setChosen(false);
    }

    /**
     * ノードリストにおける任意のノードを描画する。
     *
     * @param number 任意のノード番号
     */
    public void drawNode(int number) {
        nodes.get(number).draw();
    }

    /**
     * ノードリストにおける任意のノードの幅を返す。
     *
     * @param number 任意のノード番号
     * @return ノードの幅 <br> ノードの内容などによっては、幅を計算した後でないと正しい結果を返さない恐れがある。
     */
    public double getNodeWidth(int number) {
        return nodes.get(number).getWidth();
    }

    /**
     * ノードリストにおける任意のノードの高さを返す。
     *
     * @param number 任意のノード番号
     * @return ノードの高さ <br> ノードの内容などによっては、高さを計算した後でないと正しい結果を返さない恐れがある。
     */
    public double getNodeHeight(int number) {
        return nodes.get(number).getHeight();
    }

    /**
     * <p>
     * ノードを追加する。
     * 追加する際には、RetussMainウィンドウにおけるどのボタンを押されていたかに応じて処理を変更するため、
     * {@link UtilityJavaFXComponent#bindAllButtonsFalseWithout(List, Button)} で生成したボタンのリストを入力する必要がある。
     * </p>
     *
     * @param buttons {@link UtilityJavaFXComponent#bindAllButtonsFalseWithout(List, Button)} で生成したボタンのリスト <br> どのボタンも押されていなかった場合は何も動作しない。
     */
    public void addDrawnNode(List<Button> buttons) {
        for (Button button : buttons) {
            if (button.isDefaultButton()) {
                setupDrawnNode(button);
                break;
            }
        }
    }

    /**
     * <p> エッジを追加する </p>
     *
     * <p>
     * 追加する際には、RetussMainウィンドウにおけるどのボタンを押されていたかに応じて処理を変更するため、
     * {@link UtilityJavaFXComponent#bindAllButtonsFalseWithout(List, Button)} で生成したボタンのリストを入力する必要がある。
     * {@link #createDrawnEdge(Button, String, double, double)}を用いる。
     * </p>
     *
     * @param buttons  {@link UtilityJavaFXComponent#bindAllButtonsFalseWithout(List, Button)} で生成したボタンのリスト <br> どのボタンも押されていなかった場合は何も動作しない。
     * @param name     追加したいエッジ名 <br> 追加したいエッジによっては空文字では追加しない可能性がある。
     * @param toMouseX 追加したい関係先のマウスのX軸
     * @param toMouseY 追加したい関係先のマウスのY軸
     */
    public void addDrawnEdge(List<Button> buttons, String name, double toMouseX, double toMouseY) {
        for (Button button : buttons) {
            if (button.isDefaultButton()) {
                createDrawnEdge(button, name, toMouseX, toMouseY);
                break;
            }
        }
    }

    /**
     * 描画済みの任意のノードの内容を文字列のリストで取得する。
     *
     * @param nodeNumber 任意のノード番号
     * @param type       任意のノードにおける内容の種類
     * @return 任意のノードの内容の文字列リスト
     */
    public List<String> getDrawnNodeTextList(int nodeNumber, ContentType type) {
        return nodes.get(nodeNumber).getNodeContents(type);
    }

    /**
     * <p>
     * 描画済みの任意のノードに真偽値を設定する。
     * 設定する内容における大枠の種類を {@code parent} で指定し、設定する内容の種類を {@code child} で指定する。
     * また、 {@code nodeNumber} は描画済みの任意のノード番号であり、 {@code contentNumber} は設定する内容の種類における任意の設定したい内容の番号である。
     * </p>
     *
     * @param nodeNumber    描画済みの任意のノード番号
     * @param parent        設定する内容における大枠の種類
     * @param child         設定する内容の種類
     * @param contentNumber 設定する内容の種類における任意の番号
     * @param isChecked     設定値
     */
    public void setDrawnNodeContentBoolean(int nodeNumber, ContentType parent, ContentType child, int contentNumber, boolean isChecked) {
        nodes.get(nodeNumber).setNodeContentBoolean(parent, child, contentNumber, isChecked);
    }


    /**
     * <p>
     * 描画済みの任意のノードにおける真偽値のリストを取得する。
     * 取得する内容における大枠の種類を {@code parent} で指定し、取得する内容の種類を {@code child} で指定する。
     * また、 {@code nodeNumber} は描画済みの任意のノード番号であり、 {@code contentNumber} は取得する内容の種類における任意の取得したい内容の番号である。
     * </p>
     *
     * @param nodeNumber 描画済みの任意のノード番号
     * @param parent     取得する内容における大枠の種類
     * @param child      取得する内容の種類
     * @return 描画済みの任意のノードにおける真偽値のリスト
     */
    public List<Boolean> getDrawnNodeContentsBooleanList(int nodeNumber, ContentType parent, ContentType child) {
        return nodes.get(nodeNumber).getNodeContentsBoolean(parent, child);
    }

    /**
     * 描画済みの任意のノードにおける内容のテキストを追加する。
     *
     * @param number 描画済みの任意のノード番号
     * @param type   描画済みの任意のノードにおける追加したい内容の種類
     * @param text   追加するテキスト
     */
    public void addDrawnNodeText(int number, ContentType type, String text) {
        if (text.length() <= 0) return;
        nodes.get(number).createNodeText(type, text);
    }

    /**
     * 描画済みの任意のノードにおける任意の内容のテキストを変更する。
     *
     * @param nodeNumber    描画済みの任意のノード番号
     * @param type          描画済みの任意のノードにおける追加したい内容の種類
     * @param contentNumber 描画済みの任意のノードにおける追加したい種類の番号
     * @param text          変更するテキスト
     */
    public void changeDrawnNodeText(int nodeNumber, ContentType type, int contentNumber, String text) {
        if (text.length() <= 0) return;
        nodes.get(nodeNumber).changeNodeText(type, contentNumber, text);
    }

    /**
     * 描画済みの任意のノードを削除する。
     *
     * @param number 描画済みの任意のノード番号
     */
    public void deleteDrawnNode(int number) {
        nodes.remove(number);
    }

    /**
     * 描画済みの任意のノードにおける任意の内容を削除する。
     *
     * @param nodeNumber    描画済みの任意のノード番号
     * @param type          描画済みの任意のノードにおける追加したい内容の種類
     * @param contentNumber 描画済みの任意のノードにおける追加したい種類の番号
     */
    public void deleteDrawnNodeText(int nodeNumber, ContentType type, int contentNumber) {
        nodes.get(nodeNumber).deleteNodeText(type, contentNumber);
    }

    /**
     * 追加するノードの初期化を行う。
     *
     * @param button 追加したいノードとしてRetussWindow上で押しているボタン
     */
    private void setupDrawnNode(Button button) {
        if (nodeText.length() <= 0) return;

        if (button.getText().equals("Class")) {
            ClassNodeDiagram classNodeDiagram = new ClassNodeDiagram();
            nodes.add(classNodeDiagram);
            currentNodeNumber = nodes.size() - 1;
            setupDrawnNode(currentNodeNumber);
        } else if (button.getText().equals("Note")) {
            NoteNodeDiagram noteNodeDiagram = new NoteNodeDiagram();
            nodes.add(noteNodeDiagram);
            currentNodeNumber = nodes.size() - 1;
            setupDrawnNode(currentNodeNumber);
        }
    }

    /**
     * <p>
     * 追加するエッジの初期化を行う。
     * このメソッドを動かす前に {@link #setMouseCoordinates(double, double)} で関係元のノードのマウスのXY軸を設定しておかなければならない。
     * </p>
     *
     * @param button   追加したいエッジとしてRetussWindow上で押しているボタン
     * @param name     追加したいエッジ名 <br> 追加したいエッジによっては空文字では追加しない可能性がある。
     * @param toMouseX 追加したい関係先のマウスのX軸
     * @param toMouseY 追加したい関係先のマウスのY軸
     */
    private void createDrawnEdge(Button button, String name, double toMouseX, double toMouseY) {
        if (nodeText.length() <= 0) return;

        if (button.getText().equals("Composition")) {
            getNodeDiagramId(mouseX, mouseY);
            int fromNodeId = currentNodeNumber;
            getNodeDiagramId(toMouseX, toMouseY);
            int toNodeId = currentNodeNumber;
            if (name.length() > 0) {
                relations.createEdgeText(ContentType.Composition, name);
                relations.setRelationId(ContentType.Composition, relations.getCompositionsCount() - 1, toNodeId);
                relations.setRelationSourceId(ContentType.Composition, relations.getCompositionsCount() - 1, fromNodeId);
                relations.setRelationPoint(ContentType.Composition, relations.getCompositionsCount() - 1, nodes.get(toNodeId).getPoint());
                relations.setRelationSourcePoint(ContentType.Composition, relations.getCompositionsCount() - 1, nodes.get(fromNodeId).getPoint());
            }

        } else if (button.getText().equals("Generalization")) {
            getNodeDiagramId(mouseX, mouseY);
            int fromNodeId = currentNodeNumber;
            getNodeDiagramId(toMouseX, toMouseY);
            int toNodeId = currentNodeNumber;
            relations.createEdgeText(ContentType.Generalization, name);
            relations.setRelationId(ContentType.Generalization, relations.getCompositionsCount() - 1, toNodeId);
            relations.setRelationSourceId(ContentType.Generalization, relations.getCompositionsCount() - 1, fromNodeId);
            relations.setRelationPoint(ContentType.Generalization, relations.getCompositionsCount() - 1, nodes.get(toNodeId).getPoint());
            relations.setRelationSourcePoint(ContentType.Generalization, relations.getCompositionsCount() - 1, nodes.get(fromNodeId).getPoint());
            relations.deleteGeneralizationFromSameRelationSourceNode(fromNodeId);
        }
    }

    /**
     * キャンバス上の任意のポイントにおける描画済みのノードのインスタンスを探索する。
     *
     * @param mouseX キャンバス上の任意のポイントのX軸
     * @param mouseY キャンバス上の任意のポイントのY軸
     * @return 探索できた描画済みのノードのインスタンス <br> 描画していなかった場合は {@code null} を返す。
     */
    public NodeDiagram findNodeDiagram(double mouseX, double mouseY) {
        int number = getNodeDiagramId(mouseX, mouseY);

        if (number == -1) return null;
        else return nodes.get(currentNodeNumber);
    }

    /**
     * <p>
     * キャンバス上の任意のポイントに図を描画済みか否かを取得する。
     * ノードでもエッジでも、とりあえず何かしらが描画済みであれば真を返す。
     * </p>
     *
     * @param mouseX キャンバス上の任意のポイントのX軸
     * @param mouseY キャンバス上の任意のポイントのY軸
     * @return キャンバス上の任意のポイントに任意の図を描画済みか否かの真偽値
     */
    public boolean isAlreadyDrawnAnyDiagram(double mouseX, double mouseY) {
        boolean act = false;

        for (NodeDiagram nodeDiagram : nodes) {
            if (nodeDiagram.isAlreadyDrawnNode(mouseX, mouseY)) {
                act = true;
                break;
            }
        }
        for (int i = 0; i < relations.getCompositionsCount(); i++) {
            if (relations.isAlreadyDrawnAnyEdge(ContentType.Composition, i, new Point2D(mouseX, mouseY))) {
                act = true;
                break;
            }
        }

        return act;
    }

    /**
     * キャンバス上の任意のポイントにおける描画済みのエッジのインスタンスを取得する。
     *
     * @param mouseX キャンバス上の任意のポイントのX軸
     * @param mouseY キャンバス上の任意のポイントのY軸
     * @return キャンバス上の任意のポイントにおける描画済みのエッジのインスタンス <br> キャンバス上の任意のポイントに何も描画していない場合は {@code null} を返す。
     */
    public RelationshipAttributeGraphic searchDrawnEdge(double mouseX, double mouseY) {
        RelationshipAttributeGraphic relation = relations.searchCurrentRelation(new Point2D(mouseX, mouseY));
        return relation;
    }

    /**
     * <p>
     * キャンバス上の任意のポイントにおける描画済みのエッジの内容のテキストを変更する。
     * キャンバス上の任意のポイントに何も描画していない場合は何もしない。
     * </p>
     *
     * @param mouseX  キャンバス上の任意のポイントのX軸
     * @param mouseY  キャンバス上の任意のポイントのY軸
     * @param content キャンバス上の任意のポイントに描画済みのエッジの変更したい内容のテキスト
     */
    public void changeDrawnEdge(double mouseX, double mouseY, String content) {
        relations.changeCurrentRelation(new Point2D(mouseX, mouseY), content);
    }

    /**
     * <p>
     * キャンバス上の任意のポイントにおける描画済みのエッジを削除する。
     * キャンバス上の任意のポイントに何も描画していない場合は何もしない。
     * </p>
     *
     * @param mouseX キャンバス上の任意のポイントのX軸
     * @param mouseY キャンバス上の任意のポイントのY軸
     */
    public void deleteDrawnEdge(double mouseX, double mouseY) {
        relations.deleteCurrentRelation(new Point2D(mouseX, mouseY));
    }

    /**
     * <p>
     * キャンバス上の任意のポイントに描画済みの図の種類を取得する。
     * ノードでもエッジでも、とりあえず何かしらが描画済みであればその図の種類を返す。
     * </p>
     *
     * @param mouseX キャンバス上の任意のポイントのX軸
     * @param mouseY キャンバス上の任意のポイントのY軸
     * @return キャンバス上の任意のポイントに描画済みの図の種類
     */
    public ContentType searchDrawnAnyDiagramType(double mouseX, double mouseY) {
        ContentType type = ContentType.Undefined;

        for (NodeDiagram nodeDiagram : nodes) {
            if (nodeDiagram.isAlreadyDrawnNode(mouseX, mouseY)) {
                type = nodeDiagram.getNodeType();
                break;
            }
        }
        for (int i = 0; i < relations.getCompositionsCount(); i++) {
            if (relations.isAlreadyDrawnAnyEdge(ContentType.Composition, i, new Point2D(mouseX, mouseY))) {
                type = relations.getContentType(i);
                break;
            }
        }

        return type;
    }

    /**
     * <p>
     * 正当な描画したい図を待機中か否かを返す。
     * 主にエッジを描画する際に用いる。
     * このメソッドにおける正当な描画したい図とは、GUI上で2度（関係元と関係先のノード）を選択した際に、2度とも同じ図を描画しようとしているか否かである。
     * 例えば1回目にFirstClassNodeを選択し、2回目にSecondClassNodeを選択した場合を考える。
     * この時、1回目と2回目で同じ関係（例えばコンポジション）を選択している場合は、2回目は正当な描画したい図を待機中であるとする。
     * もし1回目と2回目で異なる関係（例えば1回目にはコンポジション、2回目には汎化）を選択している場合は、2回目には正当な描画したい図を待機中でないとする。
     * なお、1回目は必ず正当な描画したい図を待機中でないとする。
     * </p>
     *
     * @param type   描画したい図の種類
     * @param mouseX キャンバス上の任意のポイントのX軸
     * @param mouseY キャンバス上の任意のポイントのY軸
     * @return 正当な描画したい図を待機中か否か <br> 判定基準については本文を参照
     */
    public boolean hasWaitedCorrectDrawnDiagram(ContentType type, double mouseX, double mouseY) {
        boolean act = false;

        if (isAlreadyDrawnAnyDiagram(mouseX, mouseY)) {
            if (relations.hasRelationSourceNodeSelected()) {
                act = true;
                if (type == nowStateType) {
                    relations.changeRelationSourceNodeSelectedState();
                    setNodeChosen(currentNodeNumber, false);
                    nowStateType = ContentType.Undefined;
                } else {
                    findNodeDiagram(mouseX, mouseY);
                    setNodeChosen(currentNodeNumber, true);
                    nowStateType = type;
                }
            } else {
                relations.changeRelationSourceNodeSelectedState();
                findNodeDiagram(mouseX, mouseY);
                setNodeChosen(currentNodeNumber, true);
                nowStateType = type;
            }
        } else {
            nowStateType = ContentType.Undefined;
        }

        return act;
    }

    /**
     * <p>
     * 正当な描画したい図の待機中をリセットする。
     * エッジを描画完了した際や、エッジ描画中にノード描画を行おうとした際などに呼び出さなければならない。
     * </p>
     *
     * @param number エッジを描画しようとしていた関係元のノード番号
     */
    public void resetNodeChosen(int number) {
        nodes.get(number).setChosen(false);
        nowStateType = ContentType.Undefined;
        relations.resetRelationSourceNodeSelectedState();
    }

    /**
     * <p>
     * 任意のノードを選択中か否かを設定する。
     * エッジを描画中である場合などに関係元のノードとして設定する。
     * </p>
     *
     * @param number   任意のノード番号
     * @param isChosen 選択中か否か
     */
    public void setNodeChosen(int number, boolean isChosen) {
        nodes.get(number).setChosen(isChosen);
    }

    /**
     * 現在描画中のエッジの種類を取得する。
     *
     * @return 現在描画中のエッジの種類
     */
    public ContentType getNowStateType() {
        return nowStateType;
    }

    /**
     * <p>
     * キャンバス上の任意のポイントに描画済みのノードのIDを取得する。
     * キャンバス上の任意のポイントに複数の描画済みのノードが重なっている場合は、最後に描画した、すなわちキャンバス上では一番上に描画しているノードのIDを返す。
     * </p>
     *
     * @param mouseX キャンバス上の任意のポイントのX軸
     * @param mouseY キャンバス上の任意のポイントのY軸
     * @return キャンバス上の任意のポイントに描画済みのノードのID
     */
    public int getNodeDiagramId(double mouseX, double mouseY) {
        int act = -1;

        // 重なっているノードの内1番上に描画しているノードはnodesリストの1番後半に存在するため、1番上に描画しているノードを取るためには尻尾から見なければならない。
        for (int i = nodes.size() - 1; i >= 0; i--) {
            if (nodes.get(i).isAlreadyDrawnNode(mouseX, mouseY)) {
                act = nodes.get(i).getNodeId();
                currentNodeNumber = i;
                break;
            }
        }

        return act;
    }

    /**
     * クラス図キャンバスの縁を描画する。
     */
    private void drawDiagramCanvasEdge() {
        double space = 5.0;
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();
        gc.setStroke(Color.BLACK);
        gc.strokeRect(space, space, width - space * 2, height - space * 2);
    }

    /**
     * 生成したクラス図のノードのリストを返す。
     * テストコードで主に用いる。
     *
     * @return 生成したノードリスト <br> sizeが0の可能性あり
     */
    List<NodeDiagram> getNodes() {
        return nodes;
    }

    /**
     * 生成したクラス図のエッジのインスタンスを返す。
     * テストコードで主に用いる。
     *
     * @return 生成したエッジのインスタンス <br> {@code null} の可能性は無いが、内容が空の可能性あり
     */
    EdgeDiagram getEdgeDiagram() {
        return relations;
    }
}
