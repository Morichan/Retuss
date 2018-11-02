package io.github.morichan.retuss.listener;

import io.github.morichan.retuss.language.cpp.Cpp;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import io.github.morichan.retuss.parser.cpp.CPP14Lexer;
import io.github.morichan.retuss.parser.cpp.CPP14Parser;

public class CppLanguage {
    private CppEvalListener cppEvalListener = new CppEvalListener();

    private Cpp cpp;

//    private String className;
//    private String extendedClassName;

    public void parseForClassDiagram(String code) {
        walk(code);
        cpp = cppEvalListener.getCpp();
    }


    public Cpp getCpp() {
        return cpp;
    }



    private void walk(String code) {
        CPP14Lexer lexer = new CPP14Lexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CPP14Parser parser = new CPP14Parser(tokens);
        ParseTree tree = parser.translationunit();
        ParseTreeWalker walker = new ParseTreeWalker();
        cppEvalListener = new CppEvalListener();
        walker.walk(cppEvalListener, tree);
    }

}
