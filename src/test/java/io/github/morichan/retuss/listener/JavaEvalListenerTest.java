package io.github.morichan.retuss.listener;

import io.github.morichan.retuss.language.java.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import io.github.morichan.retuss.parser.java.JavaLexer;
import io.github.morichan.retuss.parser.java.JavaParser;

import java.io.IOException;
import java.util.Arrays;
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

    String dir = "./src/main/resources/";

    @Disabled("ファイルPATHがおかしい")
    @Nested
    class 構文解析機自体に関して {

        @Nested
        class Java7までのソースコードの場合 {

            @BeforeEach
            void setup() throws IOException {
                String file = dir + "AllInOne7.txt";
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
                String file = dir + "AllInOne8.txt";
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

    @Disabled("ファイルPATHがおかしい")
    @Nested
    class Java7までのソースコードの場合 {

        JavaParser.PackageDeclarationContext packageDeclaration;
        List<JavaParser.ImportDeclarationContext> importDeclarations;
        List<JavaParser.TypeDeclarationContext> typeDeclarations;

        @BeforeEach
        void setup() throws IOException {
            String file = dir + "AllInOne7.txt";
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

            @Test
            void フィールドを1つ返す() {
                init("class JavaClass {private int number;}");
                Field expected = new Field(new Type("int"), "number");

                Field actual = obj.getJava().getClasses().get(0).getFields().get(0);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void フィールドを3つ返す() {
                init("class JavaClass {private int number; double point; protected float bias;}");
                List<Field> expected = Arrays.asList(
                        new Field(new Type("int"), "number"),
                        new Field(new Type("double"), "point"),
                        new Field(new Type("float"), "bias"));
                expected.get(0).setAccessModifier(AccessModifier.Private);
                expected.get(1).setAccessModifier(AccessModifier.Package);
                expected.get(2).setAccessModifier(AccessModifier.Protected);

                List<Field> actual = obj.getJava().getClasses().get(0).getFields();

                for (int i = 0; i < expected.size(); i++) {
                    assertThat(actual.get(i)).isEqualToComparingFieldByFieldRecursively(expected.get(i));
                }
            }

            @Test
            void メソッドを1つ返す() {
                init("class JavaClass {public void print() {}}");
                Method expected = new Method(new Type("void"), "print");
                expected.setMethodBody(new MethodBody());

                Method actual = obj.getJava().getClasses().get(0).getMethods().get(0);

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void メソッドを3つ返す() {
                init("class JavaClass {public void print() {}protected int getItem() {}void setItem(int item) {}}");
                List<Method> expected = Arrays.asList(
                        new Method(new Type("void"), "print"),
                        new Method(new Type("int"), "getItem"),
                        new Method(new Type("void"), "setItem")
                );
                expected.get(0).setAccessModifier(AccessModifier.Public);
                expected.get(1).setAccessModifier(AccessModifier.Protected);
                expected.get(2).setAccessModifier(AccessModifier.Package);
                expected.get(2).addArgument(new Argument(new Type("int"), "item"));
                expected.get(0).setMethodBody(new MethodBody());
                expected.get(1).setMethodBody(new MethodBody());
                expected.get(2).setMethodBody(new MethodBody());

                List<Method> actual = obj.getJava().getClasses().get(0).getMethods();

                for (int i = 0; i < expected.size(); i++) {
                    assertThat(actual.get(i)).isEqualToComparingFieldByFieldRecursively(expected.get(i));
                }
            }

            @Test
            void 引数を持つメソッドを3つ返す() {
                init("class JavaClass {public double calculate(double x, double y, double z) {}protected Point createPoint(int x, int y) {}private void print(char text) {}}");
                List<Method> expected = Arrays.asList(
                        new Method(new Type("double"), "calculate"),
                        new Method(new Type("Point"), "createPoint"),
                        new Method(new Type("void"), "print")
                );
                expected.get(0).setAccessModifier(AccessModifier.Public);
                expected.get(1).setAccessModifier(AccessModifier.Protected);
                expected.get(2).setAccessModifier(AccessModifier.Private);
                expected.get(0).addArgument(new Argument(new Type("double"), "x"));
                expected.get(0).addArgument(new Argument(new Type("double"), "y"));
                expected.get(0).addArgument(new Argument(new Type("double"), "z"));
                expected.get(1).addArgument(new Argument(new Type("int"), "x"));
                expected.get(1).addArgument(new Argument(new Type("int"), "y"));
                expected.get(2).addArgument(new Argument(new Type("char"), "text"));
                expected.get(0).setMethodBody(new MethodBody());
                expected.get(1).setMethodBody(new MethodBody());
                expected.get(2).setMethodBody(new MethodBody());

                List<Method> actual = obj.getJava().getClasses().get(0).getMethods();

                for (int i = 0; i < expected.size(); i++) {
                    assertThat(actual.get(i)).isEqualToComparingFieldByFieldRecursively(expected.get(i));
                }
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