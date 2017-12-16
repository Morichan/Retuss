package translator;

import parser.java.JavaParser;
import parser.java.JavaParserBaseListener;

public class JavaEvalListener extends JavaParserBaseListener {
    JavaParser parser;

    String className = "";

    public JavaEvalListener( JavaParser parser ) {
        this.parser = parser;
    }

    @Override
    public void enterClassDeclaration( JavaParser.ClassDeclarationContext ctx ) {
        className = ctx.IDENTIFIER().toString();
    }
}
