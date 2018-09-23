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

    //classNameを抽出してくれるメソッド
    @Override
    public void exitUnqualifiedid(CPP14Parser.UnqualifiedidContext ctx) {
        for(int i=0;i<ctx.getChildCount();i++) {
            if(ctx.getChild(i) instanceof TerminalNodeImpl) {
                className=ctx.getChild(0).getText();
            }
        }
    }

    public Cpp getCpp() {
        return cpp;
    }


    public String getText() {
        return className;
    }
}
