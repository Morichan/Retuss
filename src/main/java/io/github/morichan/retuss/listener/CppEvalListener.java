package io.github.morichan.retuss.listener;

import io.github.morichan.retuss.language.cpp.Class;
import io.github.morichan.retuss.language.cpp.Cpp;
import io.github.morichan.retuss.parser.cpp.CPP14Parser;
import io.github.morichan.retuss.parser.cpp.CPP14BaseListener;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.List;


public class CppEvalListener extends CPP14BaseListener{
    private Cpp cpp = new Cpp();
  //  private String classname; //
//    private CPP14Parser.ClassnameContext classnameContext = null;
    private List<CPP14Parser.ClassnameContext> classnames = new ArrayList<>();

    //最初に探索をはじめた時の初期の構文で必要なものを準備

    @Override public void enterTranslationunit(CPP14Parser.TranslationunitContext ctx) { }

    @Override public void enterClasshead(CPP14Parser.ClassheadContext ctx) { }

    @Override
    public void enterClassheadname(CPP14Parser.ClassheadnameContext ctx) {
//        for (int i = 0; i < ctx.getChildCount(); i++) {
//            if (ctx.getChild(i) instanceof CPP14Parser.ClassnameContext) {
//                classnames.add((CPP14Parser.ClassnameContext) ctx.getChild(i));
//
//            }
//        }
        Class cppClass = new Class();

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof CPP14Parser.ClassnameContext) {
                cppClass.setName(ctx.getChild(i).getChild(0).getText());
           //     cppClass.setExtendsClass(searchExtendsClass((JavaParser.ClassDeclarationContext) ctx.getChild(i)));
            }
        }
        cpp.addClass(cppClass);
    }

    /**
     *     継承クラスの構文解析中に実行されるメソッド
     *
     */
    @Override
    public void enterClassordecltype(CPP14Parser.ClassordecltypeContext ctx) {
        Class cppClass = new Class();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof CPP14Parser.ClassnameContext) {
                    cppClass.setExtendsClass(searchExtendsClass((CPP14Parser.ClassDeclarationContext) ctx.getChild(i)));
            }
        }
        cpp.addClass(cppClass);
    }



    //enterClassname で処理を実装すると、継承のクラスの構文解析中にも発生するので推奨しない。
//    @Override public void enterClassname(CPP14Parser.ClassnameContext ctx) {
//        Class cppClass = new Class();
//
//        for (int i = 0; i < ctx.getChildCount(); i++) {
//            if (ctx.getChild(i) instanceof TerminalNodeImpl) {
//                cppClass.setName(ctx.getChild(i).getText());
//           //     cppClass.setExtendsClass(searchExtendsClass((JavaParser.ClassDeclarationContext) ctx.getChild(i)));
//            }
//        }
//        cpp.addClass(cppClass);
//    }





    //抽出してくれるメソッドの処理の例
//    @Override
//    public void exitUnqualifiedid(CPP14Parser.UnqualifiedidContext ctx) {
//        for(int i=0;i<ctx.getChildCount();i++) {
//            if(ctx.getChild(i) instanceof TerminalNodeImpl) {
//                className=ctx.getChild(0).getText();
//            }
//        }
//    }





    public Cpp getCpp() {
        return cpp;
    }


//    public String getText() {
//        return className;
//    }
}
