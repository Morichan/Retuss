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

    private String className;
    private String extendedClassName;

    public void parseForClassDiagram(String code) {
        walk(code);
        className = searchClassName();
        extendedClassName = searchExtendedClassName();
        cpp = cppEvalListener.getCpp();
    }

    private String searchClassName() {
        String name = "";
        return name;
    }

    /**
     * <p> 継承先クラス名を探索する。 </p>
     *
     * <p>
     * 事前に{@link #walk(String)}を実行する必要がある。
     * 走査後、予約語 "extends" 以降 "implements" または "{" 以前の文字列を抽出する。
     * その際に、実際に文法的に使えるかどうかはともかく、間のスペースやアノテーション、カギ括弧 "[]" は無視する。
     * </p>
     *
     * @return 予約語 "extends" 以降 "implements" または "{" 以前の文字列（間のスペース、アノテーションおよびカギ括弧 "[]" は無視）
     */
    private String searchExtendedClassName() {
        String name = "";
        return name;
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
