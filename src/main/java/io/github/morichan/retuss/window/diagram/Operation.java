package io.github.morichan.retuss.window.diagram;

public class Operation extends ClassData {

    public Operation() {
        name = "";
        visibility = "";
        type = ContentType.Undefined;
        isIndicate = true;
    }

    public Operation( String name ) {
        this.name = name;
        isIndicate = true;
    }
}
