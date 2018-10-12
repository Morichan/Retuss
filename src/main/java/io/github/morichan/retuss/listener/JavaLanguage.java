package io.github.morichan.retuss.listener;

import io.github.morichan.retuss.language.java.Java;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import io.github.morichan.retuss.parser.java.JavaLexer;
import io.github.morichan.retuss.parser.java.JavaParser;

public class JavaLanguage {
    private JavaEvalListener javaEvalListener = new JavaEvalListener();

    private Java java;

    public void parseForClassDiagram(String code) {
        walk(code);
        java = javaEvalListener.getJava();
    }

    public Java getJava() {
        return java;
    }

    /**
     * <p> コードを走査する。 </p>
     *
     * <p>
     * 引数として受け取った文字列をレキサ、パーサにかける。
     * もし正しいコードでなかった場合は標準出力にその旨を表示するが、エラーとして出力するわけではないことに注意する必要がある。
     * </p>
     *
     * @param code 構文解析したいコードの文字列
     */
    private void walk(String code) {
        JavaLexer lexer = new JavaLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        javaEvalListener = new JavaEvalListener();
        walker.walk(javaEvalListener, tree);
    }
}
