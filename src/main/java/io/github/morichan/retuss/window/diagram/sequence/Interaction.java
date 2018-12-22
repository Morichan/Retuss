package io.github.morichan.retuss.window.diagram.sequence;

import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.window.diagram.OperationGraphic;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Interaction {

    MessageOccurrenceSpecification message;
    Class umlClass;
    OperationGraphic operationGraphic;

    public Interaction(Class umlClass, OperationGraphic og) {
        setUmlClass(umlClass);
        setOperationGraphic(og);
    }

    public void setUmlClass(Class umlClass) {
        this.umlClass = umlClass;
    }

    public void setOperationGraphic(OperationGraphic operationGraphic) {
        this.operationGraphic = operationGraphic;
    }

    public MessageOccurrenceSpecification getMessage() {
        return message;
    }

    public void draw(GraphicsContext gc) {
        Lifeline lifeline = new Lifeline();
        lifeline.setUmlClass(umlClass);

        message = new MessageOccurrenceSpecification();
        message.setOperationGraphic(operationGraphic);
        message.setLifeline(lifeline);
        message.calculatePoint();
        message.draw(gc);
    }

    @Deprecated
    private void drawOriginal(GraphicsContext gc) {
        Lifeline lifeline = new Lifeline();
        lifeline.setUmlClass(umlClass);

        Class umlClass = new Class("ClassName");
        Lifeline another = new Lifeline();
        another.setUmlClass(umlClass);
        Lifeline another2 = new Lifeline();
        another2.setUmlClass(new Class("ClassName2"));
        // Lifeline another3 = new Lifeline();
        // another3.setUmlClass(umlClass);

        MessageOccurrenceSpecification otherMessage = new MessageOccurrenceSpecification();
        otherMessage.setOperationGraphic(new OperationGraphic(new Operation(new Name("reprint"))));
        otherMessage.setLifeline(another);

        MessageOccurrenceSpecification anotherMessage = new MessageOccurrenceSpecification();
        anotherMessage.setOperationGraphic(new OperationGraphic(new Operation(new Name("reprint2"))));
        anotherMessage.setLifeline(another2);
        // otherMessage.addMessage(anotherMessage);

        MessageOccurrenceSpecification another3Message = new MessageOccurrenceSpecification();
        another3Message.setOperationGraphic(new OperationGraphic(new Operation(new Name("reprint3"))));
        another3Message.setLifeline(another);

        message = new MessageOccurrenceSpecification();
        message.setOperationGraphic(operationGraphic);
        message.setLifeline(lifeline);
        message.addMessage(otherMessage);
        message.addMessage(anotherMessage);
        message.addMessage(another3Message);
        message.calculatePoint();
        message.draw(gc);
    }
}
