package io.github.morichan.retuss.window;

import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.language.uml.Package;
import io.github.morichan.retuss.window.diagram.OperationGraphic;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SequenceDiagramDrawer {
    private String diagramFont = "Consolas";
    double space = 5.0;

    private TabPane tabPaneInSequenceTab;
    private Package umlPackage;

    public void setSequenceDiagramTabPane(TabPane tabPane) {
        tabPaneInSequenceTab = tabPane;
    }

    public TabPane getTabPaneInSequenceTab() {
        return tabPaneInSequenceTab;
    }

    public void setUmlPackage(Package umlPackage) {
        this.umlPackage = umlPackage;
    }

    public void draw() {
        int classIndex = 0;

        for (Tab classTab : tabPaneInSequenceTab.getTabs()) {
            if (classTab.isDisable()) {
                classIndex++;
                continue;
            }

            if (classTab.getContent() == null) break;

            int operationIndex = 0;

            for (Tab operationTab : ((TabPane) ((AnchorPane) classTab.getContent()).getChildren().get(0)).getTabs()) {
                if (operationTab.isDisable()) {
                    operationIndex++;
                    continue;
                }

                OperationGraphic og = umlPackage.getClasses().get(classIndex).getOperationGraphics().get(operationIndex);

                Canvas canvas = (Canvas) ((ScrollPane) ((AnchorPane) operationTab.getContent()).getChildren().get(0)).getContent();
                canvas.setWidth(tabPaneInSequenceTab.getWidth());
                canvas.setHeight(tabPaneInSequenceTab.getHeight());

                GraphicsContext gc = canvas.getGraphicsContext2D();
                drawDiagramCanvasEdge(gc);
                drawSequenceNameArea(gc, og.getOperation().toString());

                break;
            }

            break;
        }
    }

    public void createTabs() {

    }

    public void createTab(String text) {
        addTabInTab(new Tab("Text"));
    }

    private void addTabInTab(Tab tab) {
        tabPaneInSequenceTab.getTabs().add(tab);
    }

    private void drawSequenceNameArea(GraphicsContext gc, String text) {
        Text sd = new Text("sd");
        Text name = new Text(text);
        double titleFontSize = 15.0;

        sd.setFont(Font.font(diagramFont, FontWeight.BOLD, titleFontSize));
        name.setFont(Font.font(diagramFont, FontWeight.LIGHT, titleFontSize));

        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(sd.getFont());
        gc.fillText(sd.getText(), space * 2, space * 2 + sd.getLayoutBounds().getHeight());
        gc.setFont(name.getFont());
        gc.fillText(name.getText(), space * 3 + sd.getLayoutBounds().getWidth(), space * 2 + sd.getLayoutBounds().getHeight());

        Point2D rightBottomCorner = new Point2D(
                space * 3 + sd.getLayoutBounds().getWidth() + name.getLayoutBounds().getWidth(),
                space * 2 + sd.getLayoutBounds().getHeight());
        gc.setStroke(Color.BLACK);
        gc.strokeLine(space, rightBottomCorner.getY() + space * 2,
                rightBottomCorner.getX(), rightBottomCorner.getY() + space * 2);
        gc.strokeLine(rightBottomCorner.getX() + space * 2, space,
                rightBottomCorner.getX() + space * 2, rightBottomCorner.getY());
        gc.strokeLine(rightBottomCorner.getX(), rightBottomCorner.getY() + space * 2,
                rightBottomCorner.getX() + space * 2, rightBottomCorner.getY());
    }

    /**
     * クラス図キャンバスの縁を描画する。
     */
    private void drawDiagramCanvasEdge(GraphicsContext gc) {
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();
        gc.setStroke(Color.BLACK);
        gc.strokeRect(space, space, width - space * 2, height - space * 2);
    }
}
