package translator;

import parser.java9.Java9BaseListener;
import parser.java9.Java9Parser;

public class Java9EvalListener extends Java9BaseListener {
    Java9Parser parser;

    String className = "";

    public Java9EvalListener(Java9Parser parser) {
        this.parser = parser;
    }

    @Override
    public void enterClassDeclaration( Java9Parser.ClassDeclarationContext ctx ) {
        className = ctx.normalClassDeclaration().Identifier().toString();
    }
}
