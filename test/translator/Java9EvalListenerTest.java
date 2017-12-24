package translator;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import parser.java9.Java9Lexer;
import parser.java9.Java9Parser;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class Java9EvalListenerTest {
    Java9EvalListener obj;

    Java9Lexer lexer;
    CommonTokenStream tokens;
    Java9Parser parser;
    ParseTree tree;
    ParseTreeWalker walker;

    @Nested
    class 構文解析機自体に関して {

        @Nested
        class Java7までのソースコードの場合 {

            @BeforeEach
            public void setup() throws IOException {
                String file = "Resources\\AllInOne7.java";
                lexer = new Java9Lexer(CharStreams.fromFileName(file));
                tokens = new CommonTokenStream( lexer );
                parser = new Java9Parser( tokens );
                tree = parser.compilationUnit();
                walker = new ParseTreeWalker();
                obj = new Java9EvalListener();
            }

            @Disabled("ローカルインタフェースに未対応 : Java9EvalListenerTest#サンプルコードの場合#Java7までのソースコードに関して#構文解析する")
            @Test
            public void 構文解析時にエラーが出ないか確認する() {
                try {
                    walker.walk(obj, tree);
                } catch (NullPointerException e) {
                    fail("ParseTreeObjectNullError");
                }
            }
        }

        @Nested
        class Java8のソースコードの場合 {

            @BeforeEach
            public void setup() throws IOException {
                String file = "Resources\\AllInOne8.java";
                lexer = new Java9Lexer(CharStreams.fromFileName(file));
                tokens = new CommonTokenStream( lexer );
                parser = new Java9Parser( tokens );
                tree = parser.compilationUnit();
                walker = new ParseTreeWalker();
                obj = new Java9EvalListener();
            }

            @Test
            public void 構文解析時にエラーが出ないか確認する() {
                try {
                    walker.walk( obj, tree );
                } catch (NullPointerException e) {
                    fail("ParseTreeObjectNullError");
                }
            }
        }
    }
}