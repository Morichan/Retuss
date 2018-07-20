package io.github.morichan.retuss.listener;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import io.github.morichan.retuss.parser.java.JavaLexer;
import io.github.morichan.retuss.parser.java.JavaParser;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class JavaEvalListenerTest {
    JavaEvalListener obj;

    JavaLexer lexer;
    CommonTokenStream tokens;
    JavaParser parser;
    ParseTree tree;
    ParseTreeWalker walker;

    String code = "package window; class TestCode { int test = 0; }";
    String dir = "src\\main\\resources\\";

    @Nested
    class 構文解析機自体に関して {

        @Nested
        class Java7までのソースコードの場合 {

            @BeforeEach
            void setup() throws IOException {
                String file = dir + "AllInOne7.java";
                lexer = new JavaLexer(CharStreams.fromFileName(file));
                tokens = new CommonTokenStream(lexer);
                parser = new JavaParser(tokens);
                tree = parser.compilationUnit();
                walker = new ParseTreeWalker();
                obj = new JavaEvalListener();
            }

            @Test
            void 構文解析時にエラーが出ないか確認する() {
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
            void setup() throws IOException {
                String file = dir + "AllInOne8.java";
                lexer = new JavaLexer(CharStreams.fromFileName(file));
                tokens = new CommonTokenStream(lexer);
                parser = new JavaParser(tokens);
                tree = parser.compilationUnit();
                walker = new ParseTreeWalker();
                obj = new JavaEvalListener();
            }

            @Test
            void 構文解析時にエラーが出ないか確認する() {
                try {
                    walker.walk(obj, tree);
                } catch (NullPointerException e) {
                    fail("ParseTreeObjectNullError");
                }
            }
        }
    }

    @Nested
    class Java7までのソースコードの場合 {

        JavaParser.PackageDeclarationContext packageDeclaration;
        List<JavaParser.ImportDeclarationContext> importDeclarations;
        List<JavaParser.TypeDeclarationContext> typeDeclarations;

        @BeforeEach
        void setup() throws IOException {
            String file = dir + "AllInOne7.java";
            lexer = new JavaLexer(CharStreams.fromFileName(file));
            tokens = new CommonTokenStream(lexer);
            parser = new JavaParser(tokens);
            tree = parser.compilationUnit();
            walker = new ParseTreeWalker();
            obj = new JavaEvalListener();
            walker.walk(obj, tree);

            packageDeclaration = obj.getPackageDeclaration();
            importDeclarations = obj.getImportDeclarations();
            typeDeclarations = obj.getTypeDeclarations();

        }

        @Test
        void パッケージ名を取得する() throws NullPointerException {
            JavaParser.QualifiedNameContext ctx = null;
            String actual = "";
            String expected = "myapplication.mylibrary";

            for (int i = 0; i < packageDeclaration.getChildCount(); i++) {
                if (packageDeclaration.getChild(i) instanceof JavaParser.QualifiedNameContext) {
                    ctx = (JavaParser.QualifiedNameContext) packageDeclaration.getChild(i);
                    break;
                }
            }
            for (int i = 0; i < ctx.getChildCount(); i++) {
                actual += ctx.getChild(i).toString();
            }

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void インポート文は5つある() {
            assertThat(importDeclarations.size()).isEqualTo(5);
        }

        @Test
        void クラス宣言は35個ある() {

            // TypeDeclarationContext内には、何も存在しない";"も1つのインスタンスとして生成するため、それを削除する
            for (int i = 0; i < typeDeclarations.size(); i++) {
                if (!(typeDeclarations.get(i).getChild(0) instanceof JavaParser.ClassOrInterfaceModifierContext) &&
                        !(typeDeclarations.get(i).getChild(0) instanceof JavaParser.ClassDeclarationContext) &&
                        !(typeDeclarations.get(i).getChild(0) instanceof JavaParser.EnumDeclarationContext) &&
                        !(typeDeclarations.get(i).getChild(0) instanceof JavaParser.InterfaceDeclarationContext) &&
                        !(typeDeclarations.get(i).getChild(0) instanceof JavaParser.AnnotationTypeDeclarationContext)) {
                    typeDeclarations.remove(i);
                    i--;
                }
            }

            assertThat(typeDeclarations.size()).isEqualTo(35);
        }
    }

    @Nested
    class Javaインスタンスを生成する場合 {

        @Nested
        class クラスが1つの際に {

            @Test
            void クラス名を返す() {
                init("class JavaClassName {}");
                String expected = "JavaClassName";

                String actual = obj.getJava().getClasses().get(0).getName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 継承クラス名を返す() {
                init("class JavaClassName extends SuperJavaClassName {}");
                String expected = "SuperJavaClassName";

                String actual = obj.getJava().getClasses().get(0).getExtendsClassName();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    private void init(String javaCode) {
        lexer = new JavaLexer(CharStreams.fromString(javaCode));
        tokens = new CommonTokenStream(lexer);
        parser = new JavaParser(tokens);
        tree = parser.compilationUnit();
        walker = new ParseTreeWalker();
        obj = new JavaEvalListener();
        walker.walk(obj, tree);
    }
}