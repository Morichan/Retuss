package io.github.morichan.retuss.listener;

import io.github.morichan.retuss.language.cpp.*;
import io.github.morichan.retuss.language.cpp.Class;
import io.github.morichan.retuss.parser.cpp.CPP14Parser;
import io.github.morichan.retuss.parser.cpp.CPP14BaseListener;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.List;


public class CppEvalListener extends CPP14BaseListener {
    int class_count;
    private Cpp cpp = new Cpp();
    //  private String classname; //
//    private CPP14Parser.ClassnameContext classnameContext = null;
    //  private List<CPP14Parser.ClassnameContext> classnames = new ArrayList<>();

    //最初に探索をはじめた時の初期の構文で必要なものを準備
    //  private MemberVariable memberVariable=new MemberVariable();
    private AccessSpecifier accessSpecifier = null;
    boolean isAlreadySearchedAccessSpecifier = false;
    boolean functiondefinitionFlag = false;
    boolean memberdeclarationFlag = false;
    boolean classspecifierFlag = false;
    boolean hasVirtualMemberFunctions0 = false;

    @Override
    public void enterTranslationunit(CPP14Parser.TranslationunitContext ctx) {
    }

    @Override
    public void enterTypespecifier(CPP14Parser.TypespecifierContext ctx) {
    }

    /*
     *メンバ変数 orメンバ関数の指定子を取得
     */

    @Override
    public void enterMemberspecification(CPP14Parser.MemberspecificationContext ctx) {
        //   accessSpecifier = null;

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof CPP14Parser.AccessspecifierContext) {
                accessSpecifier = accessSpecifier.choose(ctx.getChild(i).getChild(0).getText());
            }
        }

//        cpp.addClass(cppClass);
    }

    /*
    メンバ変数か関数の判別して、適切な処理へ（";"で宣言するメンバのとき）
     */

    @Override
    public void enterMemberdeclaration(CPP14Parser.MemberdeclarationContext ctx) {
        // MemberVariable memberVariable = new MemberVariable();
        memberdeclarationFlag = true;
        boolean isVirtual = false;
        boolean isEqualZero=false;
        if (!((ctx.getChild(0) instanceof CPP14Parser.FunctiondefinitionContext))) {        //関数の記述方法の{}の方法をブロック
            if (ctx.getChild(1).getChild(0).getChild(0).getChild(0).getChild(0).getChildCount() == 1 || ctx.getChild(1).getChild(0).getChild(0).getChild(0).getChild(0).getChild(2) instanceof  CPP14Parser.ConstantexpressionContext) {
            //if (ctx.getChild(1).getChild(0).getChild(0).getChild(0).getChild(0).getChildCount() == 1) {     //メンバ変数の時  バグあり
                MemberVariable memberVariable = new MemberVariable();
                if (accessSpecifier != null) {
                    memberVariable.setAccessSpecifier(accessSpecifier);
                    // accessSpecifier = null;
                }

//<<<<<<< HEAD
                    for (int i = 0; i < ctx.getChildCount(); i++) {
                        if (ctx.getChild(i) instanceof CPP14Parser.DeclspecifierseqContext) {
                            if(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChildCount() > 1){     //stringの分岐に対応。
                                memberVariable.setType(new Type(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(1).getChild(0).getChild(0).getText()));
                            }else {
                                memberVariable.setType(new Type(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText()));
                            }
                        }
                        if (ctx.getChild(i) instanceof CPP14Parser.MemberdeclaratorlistContext) {
                            if (ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(1) instanceof CPP14Parser.PtrdeclaratorContext && ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getText().equals("*")
                                    && ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChildCount() >= 2) {
                                String state = ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getText();
                                memberVariable.setName(state + ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(1).getText());
                              //  memberVariable.setName(String.format("%s%s",state,ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(1).getText()));
                             //   memberVariable.setName(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(1).getText());   //*bのとき,なぜか*いれて成形しなくてもいけるw

                            }
                            else if(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChildCount() >= 4 && ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(2) instanceof  CPP14Parser.ConstantexpressionContext){
                              memberVariable.setName(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText());
                              memberVariable.setConstantExpression(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(2).getText());
                            }
                            else {
                                memberVariable.setName(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText());
                            }
                        }
                    }
//=======
//                for (int i = 0; i < ctx.getChildCount(); i++) {
//                    if (ctx.getChild(i) instanceof CPP14Parser.DeclspecifierseqContext) {
//                        if (ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChildCount() > 1) {     //stringの分岐に対応。
//                            memberVariable.setType(new Type(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(1).getChild(0).getChild(0).getText()));
//                        } else {
//                            memberVariable.setType(new Type(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText()));
//>>>>>>> upstream/dev_kuriharashun

//                    if (ctx.getChild(i) instanceof CPP14Parser.MemberdeclaratorlistContext) {
//                        memberVariable.setName(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText());
//                    }
//                }
                cpp.getClasses().get(cpp.getClasses().size() - 1).addMemberVariable(memberVariable);
            }

            if (ctx.getChild(1).getChild(0).getChild(0).getChild(0).getChild(0).getChild(1) instanceof CPP14Parser.ParametersandqualifiersContext) {      //メンバ関数のとき
                MemberFunction memberFunction = new MemberFunction();
                if (accessSpecifier != null) {
                    memberFunction.setAccessSpecifier(accessSpecifier);
                    // accessSpecifier = null;
                }
                for (int i = 0; i < ctx.getChildCount(); i++) {
                    if (ctx.getChild(i) instanceof CPP14Parser.DeclspecifierseqContext) {

                        if (ctx.getChild(i).getChild(0).getChild(0) instanceof CPP14Parser.FunctionspecifierContext) {     //"virtual"分岐
                            isVirtual = true;
                            memberFunction.setType(new Type(ctx.getChild(i).getChild(1).getText()));
                        } else {
                            memberFunction.setType(new Type(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText()));
                        }
                    }
                    if (ctx.getChild(i) instanceof CPP14Parser.MemberdeclaratorlistContext) {


                        if (ctx.getChild(i).getChild(0).getChildCount() >= 2 && ctx.getChild(i).getChild(0).getChild(1).getChild(1).getText().equals("0")) {
                            isEqualZero=true;
                            if(isVirtual) {
                                memberFunction.setVirtualMemberFunction0(true);
                                hasVirtualMemberFunctions0 = true;
                            }
                        }
if(!(isEqualZero == true && isVirtual == false)) {
    memberFunction.setName(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText());
}
                    }
                }
                cpp.getClasses().get(cpp.getClasses().size() - 1).addMemberFunction(memberFunction);
            }

        }

    }

    @Override
    public void exitMemberdeclaration(CPP14Parser.MemberdeclarationContext ctx) {
        memberdeclarationFlag = false;
    }


    /*
    メソッドの"{}"で実装まで宣言する処理のとき
     */

    @Override
    public void enterFunctiondefinition(CPP14Parser.FunctiondefinitionContext ctx) {
        //  functiondefinitionFlag=true;
        if (classspecifierFlag) {
            if (ctx.getChildCount() > 2) {         //コンストラクトと普通のメソッドの区別
                MemberFunction memberFunction = new MemberFunction();
                memberFunction.setFlagImplementation(true);
                if (accessSpecifier != null) {
                    memberFunction.setAccessSpecifier(accessSpecifier);
                    // accessSpecifier = null;
                }

                if (ctx.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0) instanceof CPP14Parser.SimpletypespecifierContext) {
                    memberFunction.setType(new Type(ctx.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText()));
                }

                if (ctx.getChild(1).getChild(0).getChild(0) instanceof CPP14Parser.NoptrdeclaratorContext) {
                    memberFunction.setName(ctx.getChild(1).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText());
                }


                cpp.getClasses().get(cpp.getClasses().size() - 1).addMemberFunction(memberFunction);

            }
        }
    }

    // @Override public void exitFunctiondefinition(CPP14Parser.FunctiondefinitionContext ctx) { functiondefinitionFlag=true; }     //ここfalseじゃね？

    @Override
    public void enterParameterdeclaration(CPP14Parser.ParameterdeclarationContext ctx) {
//        if (! (ctx.getParent().getParent().getParent().getParent().getParent().getParent().getParent() instanceof CPP14Parser.FunctiondefinitionContext)) return;
        if (memberdeclarationFlag) {
            Argument argument = new Argument();

            if (ctx.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0) instanceof CPP14Parser.SimpletypespecifierContext) {
                argument.setType(new Type(ctx.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText()));
            }

            for (int i = 0; i < ctx.getChildCount(); i++) {
                if (ctx.getChild(i) instanceof CPP14Parser.DeclaratorContext) {
                    argument.setName(ctx.getChild(i).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getText());
                }
            }

            cpp.getClasses().get(cpp.getClasses().size() - 1).getMemberFunctions()
                    .get(cpp.getClasses().get(cpp.getClasses().size() - 1).getMemberFunctions().size() - 1).addArgument(argument);

        }

    }


    private Class searchExtendsClass(CPP14Parser.ClassheadContext ctx) {

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof CPP14Parser.BaseclauseContext) {
//                return new Class(ctx.getChild(i).getChild(1).getChild(0).getChild(1).getChild(0).getChild(0).getText());
                for (int j = 0; j < ctx.getChild(i).getChildCount(); j++) {
                    if (ctx.getChild(i).getChild(j) instanceof CPP14Parser.BasespecifierlistContext) {
                        for (int k = 0; k < ctx.getChild(i).getChild(j).getChildCount(); k++) {
                            if (ctx.getChild(i).getChild(j).getChild(k) instanceof CPP14Parser.BasespecifierContext) {
                                for (int l = 0; l < ctx.getChild(i).getChild(j).getChild(k).getChildCount(); l++) {
                                    if (ctx.getChild(i).getChild(j).getChild(k).getChild(l) instanceof CPP14Parser.BasetypespecifierContext) {
                                        return new Class(ctx.getChild(i).getChild(j).getChild(k).getChild(l).getChild(0).getChild(0).getChild(0).getText());
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }

        }


        return null;
    }


    @Override
    public void enterClassspecifier(CPP14Parser.ClassspecifierContext ctx) {
        class_count++;
        Class cppClass = new Class();
        classspecifierFlag = true;
        hasVirtualMemberFunctions0 = false;
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof CPP14Parser.ClassheadContext) {
                if (ctx.getChild(i).getChild(0) instanceof CPP14Parser.ClasskeyContext) {
                    //   cppClass.setClassType(ctx.getChild(i).getChild(0).getChild(0).getText());
                }
                cppClass.setName(ctx.getChild(i).getChild(1).getChild(0).getChild(0).getText());
                cppClass.setExtendsClass(searchExtendsClass((CPP14Parser.ClassheadContext) ctx.getChild(i)));
            }
        }

        cpp.addClass(cppClass);
    }


    @Override
    public void exitClassspecifier(CPP14Parser.ClassspecifierContext ctx) {
        if (hasVirtualMemberFunctions0) {
            cpp.getClasses().get(cpp.getClasses().size() - 1).setAbstract(hasVirtualMemberFunctions0);
        }

        classspecifierFlag = false;
    }

    /**
     * メソッドの実装を文字列として保持
     *
     * @param ctx メソッド実装部のコンテキスト
     */
    @Override
    public void enterFunctionbody(CPP14Parser.FunctionbodyContext ctx) {
        //  MemberFunction memberFunction = new MemberFunction();
        String functionbody;
        functionbody = "defo\n";
        functionbody = ctx.getText();
        //if(cpp.getClasses().size() <= 0){return;}
        cpp.getClasses().get(cpp.getClasses().size() - 1).getMemberFunctions()
                .get(cpp.getClasses().get(cpp.getClasses().size() - 1).getMemberFunctions().size() - 1).setFunctionbody(functionbody);
    }


    /**
     * 継承クラスの構文解析中に実行されるメソッド
     */
    @Override
    public void enterClassordecltype(CPP14Parser.ClassordecltypeContext ctx) {
//        Class cppClass = new Class();
//        for (int i = 0; i < ctx.getChildCount(); i++) {
//            if (ctx.getChild(i) instanceof CPP14Parser.ClassnameContext) {
//                    cppClass.setExtendsClass(searchExtendsClass((CPP14Parser.ClassnameContext) ctx.getChild(i)));
//            }
//        }
//        cpp.addClass(cppClass);
    }


    public Cpp getCpp() {
        return cpp;
    }


//    public String getText() {
//        return className;
//    }
}
