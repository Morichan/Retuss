package io.github.morichan.retuss.window.diagram.sequence;

import io.github.morichan.retuss.window.diagram.OperationGraphic;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MessageOccurrenceSpecification {

    private Point2D beginPoint = Point2D.ZERO;
    private Point2D endPoint = Point2D.ZERO;

    private OperationGraphic og;

    public Point2D getBeginPoint() {
        return beginPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public void setBeginPoint(Point2D beginPoint) {
        this.beginPoint = beginPoint;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    public void setOperationGraphic(OperationGraphic og) {
        this.og = og;
    }

    public void draw(GraphicsContext gc, Lifeline lifeline) {
        lifeline.setOccurrenceSpecificationPoint(beginPoint);
        lifeline.draw(gc);

        double width = 10.0;
        double height = endPoint.getY() - beginPoint.getY();
        String diagramFont = "Consolas";
        double textSize = 12.0;
        Point2D first = new Point2D(5.0, beginPoint.getY());
        Point2D last = new Point2D(5.0, endPoint.getY());

        gc.setFill(Color.WHITE);
        gc.fillRect(beginPoint.getX() - width / 2, beginPoint.getY(), width, height);

        gc.setStroke(Color.BLACK);
        gc.strokeRect(beginPoint.getX() - width / 2, beginPoint.getY(), width, height);

        Text operationText = new Text(og.getOperation().toString());
        operationText.setFont(Font.font(diagramFont, FontWeight.LIGHT, textSize));

        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(operationText.getFont());
        gc.fillText(operationText.getText(), first.getX() + 5, first.getY() - 5);

        gc.strokeLine(first.getX(), first.getY(), beginPoint.getX() - width / 2, beginPoint.getY());
        double[] arrowTipX = {beginPoint.getX() - width / 2 - 5.0, beginPoint.getX() - width / 2 - 5.0, beginPoint.getX() - width / 2};
        double[] arrowTipY = {beginPoint.getY() - 3.0, beginPoint.getY() + 3.0, beginPoint.getY()};
        gc.strokeLine(beginPoint.getX() - width / 2 - 5.0, beginPoint.getY() - 3.0, beginPoint.getX() - width / 2, beginPoint.getY());
        gc.strokeLine(beginPoint.getX() - width / 2 - 5.0, beginPoint.getY() + 3.0, beginPoint.getX() - width / 2, beginPoint.getY());
        gc.fillPolygon(arrowTipX, arrowTipY, 3);

        gc.setLineDashes(5.0, 5.0);
        gc.strokeLine(endPoint.getX() - width / 2, endPoint.getY(), last.getX(), last.getY());
        gc.setLineDashes(null);
        gc.strokeLine(last.getX() + 5.0, last.getY() - 3.0, last.getX(), last.getY());
        gc.strokeLine(last.getX() + 5.0, last.getY() + 3.0, last.getX(), last.getY());
    }
}
