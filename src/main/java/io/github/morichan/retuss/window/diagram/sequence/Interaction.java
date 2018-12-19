package io.github.morichan.retuss.window.diagram.sequence;

import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.window.diagram.OperationGraphic;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Interaction {

    MessageOccurrenceSpecification message;

    public MessageOccurrenceSpecification getMessage() {
        return message;
    }

    public void draw(GraphicsContext gc, Class umlClass, OperationGraphic og) {
        Lifeline lifeline = new Lifeline();
        lifeline.setUmlClass(umlClass);

        message = new MessageOccurrenceSpecification();
        message.setBeginPoint(new Point2D(70.0, 100.0));
        message.setEndPoint(new Point2D(70.0, 200.0));
        message.setOperationGraphic(og);
        message.draw(gc, lifeline);
    }
}
