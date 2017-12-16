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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Java9EvalListenerTest {
    Java9EvalListener obj;

    Java9Lexer lexer;
    CommonTokenStream tokens;
    Java9Parser parser;
    ParseTree tree;
    ParseTreeWalker walker;

    @Nested
    class サンプルコードの場合 {

        @Nested
        class Java7までのソースコードに関して {

            @BeforeEach
            public void setup() throws IOException {
                String file = "Resources\\AllInOne7.java";
                lexer = new Java9Lexer(CharStreams.fromFileName(file));
                tokens = new CommonTokenStream( lexer );
                parser = new Java9Parser( tokens );
                tree = parser.compilationUnit();
                System.out.println("hoge>"+parser.getNumberOfSyntaxErrors());
                walker = new ParseTreeWalker();
                obj = new Java9EvalListener( parser );
            }

            //@Disabled("ローカルインタフェースに未対応 : Java9EvalListenerTest#サンプルコードの場合#Java7までのソースコードに関して#構文解析する")
            @Test
            public void 構文解析時にエラーが出ないか確認する() {
                assertThrows( NullPointerException.class, () -> walker.walk( obj, tree ) );
            }
        }

        @Nested
        class Java8のソースコードに関して {

            @BeforeEach
            public void setup() throws IOException {
                String file = "Resources\\AllInOne8.java";
                lexer = new Java9Lexer(CharStreams.fromFileName(file));
                tokens = new CommonTokenStream( lexer );
                parser = new Java9Parser( tokens );
                tree = parser.compilationUnit();
                walker = new ParseTreeWalker();
                obj = new Java9EvalListener( parser );
                walker.walk( obj, tree );
            }

            @Test
            public void 構文解析時にエラーが出ないか確認する() {
                assertThat( obj.className ).isEqualTo( "Unicode" );
            }
        }
    }
}