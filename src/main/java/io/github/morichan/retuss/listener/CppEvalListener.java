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
    private String className; //



    //最初に探索をはじめた時の初期の構文で必要なものを準備

    @Override public void enterTranslationunit(CPP14Parser.TranslationunitContext ctx) { }

    @Override public void enterClasshead(CPP14Parser.ClassheadContext ctx) { }

    @Override public void enterClassname(CPP14Parser.ClassnameContext ctx) { }





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


    public String getText() {
        return className;
    }
}
