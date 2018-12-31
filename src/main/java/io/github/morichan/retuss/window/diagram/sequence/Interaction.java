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

    public Interaction(MessageOccurrenceSpecification message) {
        this.message = message;
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

    public void resetLifelineFlag() {
        resetLifelineFlag(message);
    }

    public void resetLifelineFlag(MessageOccurrenceSpecification message) {
        for (MessageOccurrenceSpecification childMessage : message.getMessages()) {
            resetLifelineFlag(childMessage);
        }
        message.getLifeline().setCalculated(false);
        message.getLifeline().setDrawn(false);
    }

    public void draw(GraphicsContext gc) {
        // Lifeline lifeline = new Lifeline();
        // lifeline.setClassName(umlClass.getName());

        // message = new MessageOccurrenceSpecification();
        // message.setOperationName(operationGraphic.getOperation().toString());
        // message.setLifeline(lifeline);
        message.calculatePoint();
        message.draw(gc);
    }

    @Deprecated
    private void drawOriginal(GraphicsContext gc) {
        Lifeline lifeline = new Lifeline();
        lifeline.setClassName(umlClass.getName());

        Lifeline another = new Lifeline();
        another.setClassName("ClassName");
        Lifeline another2 = new Lifeline();
        another2.setClassName("ClassName2");
        // Lifeline another3 = new Lifeline();
        // another3.setUmlClass(umlClass);

        MessageOccurrenceSpecification otherMessage = new MessageOccurrenceSpecification();
        otherMessage.setOperationName("reprint");
        otherMessage.setLifeline(another);

        MessageOccurrenceSpecification anotherMessage = new MessageOccurrenceSpecification();
        anotherMessage.setOperationName("reprint2");
        anotherMessage.setLifeline(another2);
        // otherMessage.addMessage(anotherMessage);

        MessageOccurrenceSpecification another3Message = new MessageOccurrenceSpecification();
        another3Message.setOperationName("reprint3");
        another3Message.setLifeline(another);

        message = new MessageOccurrenceSpecification();
        message.setOperationName(operationGraphic.getOperation().toString());
        message.setLifeline(lifeline);
        message.addMessage(otherMessage);
        message.addMessage(anotherMessage);
        message.addMessage(another3Message);
        message.calculatePoint();
        message.draw(gc);
    }
}
