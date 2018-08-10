package io.github.morichan.retuss.window.diagram;

import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.sculptor.OperationSculptor;

public class OperationGraphic extends ClassDiagramGraphic {

    private OperationSculptor sculptor = new OperationSculptor();
    private Operation operation;

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
