package io.github.morichan.retuss.window.diagram;

import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.sculptor.OperationSculptor;
import io.github.morichan.retuss.language.java.MethodBody;
import io.github.morichan.retuss.window.diagram.sequence.Interaction;
import io.github.morichan.retuss.window.diagram.sequence.Lifeline;

import java.util.List;

public class OperationGraphic extends ClassDiagramGraphic {

    private OperationSculptor sculptor = new OperationSculptor();
    private Operation operation;
    private Interaction interaction;

    private boolean isAbstract = false;

    public OperationGraphic() {
        sculptor.parse("defaultOperation()");
        operation = sculptor.carve();
        visibility = "";
        setType(ContentType.Undefined);
        setIndication(true);
    }

    public OperationGraphic(String name) {
        sculptor.parse(name);
        operation = sculptor.carve();
        setType(ContentType.Undefined);
        setIndication(true);
    }

    public OperationGraphic(Operation operation) {
        this.operation = operation;
        setType(ContentType.Undefined);
        setIndication(true);
    }

    public Operation getOperation() {
        return operation;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setAbstract(boolean abstractFlag) {
        isAbstract = abstractFlag;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public void setText(String name) {
        sculptor.parse(name);
        operation = sculptor.carve();
    }

    @Override
    public String getText() {
        return operation.toString();
    }
}
