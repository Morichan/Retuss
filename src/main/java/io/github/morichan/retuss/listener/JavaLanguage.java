package io.github.morichan.retuss.listener;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import io.github.morichan.retuss.parser.java.JavaLexer;
import io.github.morichan.retuss.parser.java.JavaParser;

public class JavaLanguage {
    private JavaEvalListener java = new JavaEvalListener();

    private JavaLexer lexer;
    private CommonTokenStream tokens;
    private JavaParser parser;
    private ParseTree tree;
    private ParseTreeWalker walker;

    private String className;
    private String extendedClassName;

    public void parseForClassDiagram(String code) {
        walk(code);
        className = searchClassName();
        extendedClassName = searchExtendedClassName();
    }

    public String getClassName() {
        return className;
    }

    public String getExtendedClassName() {
        return extendedClassName;
    }

    /**
     * <p> クラス名を探索する。 </p>
     *
     * <p>
     * 事前に{@link #walk(String)}を実行する必要がある。
     * 走査後、最初に見つけたクラスのクラス名を抽出する。
     * </p>
     *
     * @return 最初に見つけたクラスのクラス名
     */
    private String searchClassName() {
        String name = "";

        for (int i = 0; i < java.getTypeDeclarations().get(0).getChildCount(); i++) {
            if (java.getTypeDeclarations().get(0).getChild(i) instanceof JavaParser.ClassDeclarationContext) {
                name = java.getTypeDeclarations().get(0).getChild(i).getChild(1).getText();
                break;
            }
        }

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

        for (int i = 0; i < java.getTypeDeclarations().get(0).getChildCount(); i++) {
            if (java.getTypeDeclarations().get(0).getChild(i) instanceof JavaParser.ClassDeclarationContext) {
                // クラス宣言コンテキスト内
                for (int j = 0; j < java.getTypeDeclarations().get(0).getChild(i).getChildCount(); j++) {
                    if (java.getTypeDeclarations().get(0).getChild(i).getChild(j) instanceof JavaParser.TypeTypeContext) {
                        // 継承先クラス名コンテキスト内を含むTypeTypeコンテキスト内
                        for (int k = 0; k < java.getTypeDeclarations().get(0).getChild(i).getChild(j).getChildCount(); k++) {
                            if (java.getTypeDeclarations().get(0).getChild(i).getChild(j).getChild(k) instanceof JavaParser.ClassOrInterfaceTypeContext) {
                                // 継承先クラス名コンテキスト内
                                name = java.getTypeDeclarations().get(0).getChild(i).getChild(j).getChild(k).getText();
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

        return name;
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
        lexer = new JavaLexer(CharStreams.fromString(code));
        tokens = new CommonTokenStream(lexer);
        parser = new JavaParser(tokens);
        tree = parser.compilationUnit();
        walker = new ParseTreeWalker();
        java = new JavaEvalListener();
        walker.walk(java, tree);
    }
}
