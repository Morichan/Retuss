package io.github.morichan.retuss.window.diagram;

import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.sculptor.OperationSculptor;

public class OperationGraphic extends ClassDiagramGraphic {

    private OperationSculptor sculptor = new OperationSculptor();
    private Operation operation;

    private boolean isAbstract = false;

    public OperationGraphic() {
        sculptor.parse("defaultOperation()");
        operation = sculptor.carve();
        visibility = "";
        type = ContentType.Undefined;
        isIndicate = true;
    }

    public OperationGraphic(String name) {
        sculptor.parse(name);
        operation = sculptor.carve();
        isIndicate = true;
    }

    public OperationGraphic(Operation operation) {
        this.operation = operation;
        isIndicate = true;
    }

    public Operation getOperation() {
        return operation;
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
