package io.github.morichan.retuss.listener;

//import io.github.morichan.retuss.language.cpp.MemberVariable;
import io.github.morichan.retuss.language.cpp.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

import io.github.morichan.retuss.language.cpp.Cpp;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import io.github.morichan.retuss.parser.cpp.CPP14Lexer;
import io.github.morichan.retuss.parser.cpp.CPP14Parser;
import java.util.Arrays;
import java.util.List;
class CppEvalListenerTest {

    private CppEvalListener cppEvalListener = new CppEvalListener();
    CppEvalListener obj;

    CPP14Lexer lexer;
    CommonTokenStream tokens;
    CPP14Parser parser;
    ParseTree tree;
    ParseTreeWalker walker;

//    private Cpp cpp;
//    private String className;
//    private String extendedClassName;

//    @Test
//    void enterClassspecifier() {
//    }







    @Test
    void クラス名を返す() {
        init("class cppClassName {};");
        String expected = "cppClassName";

        String actual = obj.getCpp().getClasses().get(0).getName();

        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void メンバ変数を１つ返す() {
        init("class cppClassName {private: int x;};");
        String expected = "x";

        String actual = obj.getCpp().getClasses().get(0).getMemberVariables().get(0).getName();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void フィールドを2つ返す() {
        init("class CppClass {private: int number; protected: double point;};");
        List<MemberVariable> expected = Arrays.asList(
                new MemberVariable(new Type("int"), "number"),
                new MemberVariable(new Type("double"), "point"));
        expected.get(0).setAccessSpecifier(AccessSpecifier.Private);
        expected.get(1).setAccessSpecifier(AccessSpecifier.Protected);

        List<MemberVariable> actual = obj.getCpp().getClasses().get(0).getMemberVariables();

        for (int i = 0; i < expected.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByFieldRecursively(expected.get(i));
        }
    }



    @Test
    void メソッドを1つ返す() {
        init("class cppClassXYZ {public: void print() {}};");
        MemberFunction expected = new MemberFunction(new Type("void"), "print");
        expected.setFlagImplementation(true);
        expected.setFunctionbody("{}");

        MemberFunction actual = obj.getCpp().getClasses().get(0).getMemberFunctions().get(0);

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void メソッドを3つ返す() {
        init("class CppClass {public: void print() {} protected: int getItem() {} private: void setItem() {}};");
        List<MemberFunction> expected = Arrays.asList(
                new MemberFunction(new Type("void"), "print"),
                new MemberFunction(new Type("int"), "getItem"),
                new MemberFunction(new Type("void"), "setItem")
        );
        expected.get(0).setAccessSpecifier(AccessSpecifier.Public);
        expected.get(1).setAccessSpecifier(AccessSpecifier.Protected);
        expected.get(2).setAccessSpecifier(AccessSpecifier.Private);
        expected.get(0).setFlagImplementation(true);
        expected.get(1).setFlagImplementation(true);
        expected.get(2).setFlagImplementation(true);
        expected.get(0).setFunctionbody("{}");
        expected.get(1).setFunctionbody("{}");
        expected.get(2).setFunctionbody("{}");
     //   expected.get(2).addArgument(new Argument(new Type("int"), "item"));

        List<MemberFunction> actual = obj.getCpp().getClasses().get(0).getMemberFunctions();

        for (int i = 0; i < expected.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByFieldRecursively(expected.get(i));
        }
    }



    @Test
    void 引数を持つメソッドを3つ返す() {
        init("class JavaClass {public: double calculate(double x, double y, double z) {}protected: Point createPoint(int x, int y) {}private: void print(char text) {}};");
        List<MemberFunction> expected = Arrays.asList(
                new MemberFunction(new Type("double"), "calculate"),
                new MemberFunction(new Type("Point"), "createPoint"),
                new MemberFunction(new Type("void"), "print")
        );
        expected.get(0).setAccessSpecifier(AccessSpecifier.Public);
        expected.get(1).setAccessSpecifier(AccessSpecifier.Protected);
        expected.get(2).setAccessSpecifier(AccessSpecifier.Private);
        expected.get(0).setFlagImplementation(true);
        expected.get(1).setFlagImplementation(true);
        expected.get(2).setFlagImplementation(true);
        expected.get(0).setFunctionbody("{}");
        expected.get(1).setFunctionbody("{}");
        expected.get(2).setFunctionbody("{}");
        expected.get(0).addArgument(new Argument(new Type("double"), "x"));
        expected.get(0).addArgument(new Argument(new Type("double"), "y"));
        expected.get(0).addArgument(new Argument(new Type("double"), "z"));
        expected.get(1).addArgument(new Argument(new Type("int"), "x"));
        expected.get(1).addArgument(new Argument(new Type("int"), "y"));
        expected.get(2).addArgument(new Argument(new Type("char"), "text"));

        List<MemberFunction> actual = obj.getCpp().getClasses().get(0).getMemberFunctions();

        for (int i = 0; i < expected.size(); i++) {
            assertThat(actual.get(i)).isEqualToComparingFieldByFieldRecursively(expected.get(i));
        }
    }

    @Test
    void 継承クラス名を返す() {
        init("class JavaClassName : SuperJavaClassName {};");
        String expected = "SuperJavaClassName";

        String actual = obj.getCpp().getClasses().get(0).getExtendsClassName();

        assertThat(actual).isEqualTo(expected);
    }



    private void init(String cppCode) {
        lexer = new CPP14Lexer(CharStreams.fromString(cppCode));
        tokens = new CommonTokenStream(lexer);
        parser = new CPP14Parser(tokens);
        tree = parser.translationunit();
        walker = new ParseTreeWalker();
        obj = new CppEvalListener();
        walker.walk(obj, tree);
    }
}