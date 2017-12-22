package translator;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parser.java.JavaLexer;
import parser.java.JavaParser;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

class JavaEvalListenerTest {
    JavaEvalListener obj;

    JavaLexer lexer;
    CommonTokenStream tokens;
    JavaParser parser;
    ParseTree tree;
    ParseTreeWalker walker;

    String code = "package retuss; class TestCode { public int test = 0; }";

    @Nested
    class 構文解析機自体に関して {

        @Nested
        class Java7までのソースコードの場合 {

            @BeforeEach
            public void setup() throws IOException {
                String file = "Resources\\AllInOne7.java";
                lexer = new JavaLexer(CharStreams.fromFileName(file));
                tokens = new CommonTokenStream( lexer );
                parser = new JavaParser( tokens );
                tree = parser.compilationUnit();
                walker = new ParseTreeWalker();
                obj = new JavaEvalListener( parser );
                //walker.walk( obj, tree );
            }

            @Test
            public void 構文解析時にエラーが出ないか確認する() {
                try {
                    walker.walk( obj, tree );
                } catch ( NullPointerException e ) {
                    fail("ParseTreeObjectNullError");
                }
            }
        }

        @Nested
        class Java8のソースコードの場合 {

            @BeforeEach
            public void setup() throws IOException {
                String file = "Resources\\AllInOne8.java";
                lexer = new JavaLexer(CharStreams.fromFileName(file));
                tokens = new CommonTokenStream( lexer );
                parser = new JavaParser( tokens );
                tree = parser.compilationUnit();
                walker = new ParseTreeWalker();
                obj = new JavaEvalListener( parser );
                //walker.walk( obj, tree );
            }

            @Test
            public void 構文解析時にエラーが出ないか確認する() {
                try {
                    walker.walk( obj, tree );
                } catch ( NullPointerException e ) {
                    fail("ParseTreeObjectNullError");
                }
            }
        }
    }
}