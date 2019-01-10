package io.github.morichan.retuss.listener;

import io.github.morichan.retuss.language.java.*;
import io.github.morichan.retuss.language.java.Class;
import io.github.morichan.retuss.parser.java.JavaParser;
import io.github.morichan.retuss.parser.java.JavaParserBaseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Javaソースコードのパーサを利用したコンテキストの抽出クラス
 * <p>
 * ANTLRに依存する。
 */
public class JavaEvalListener extends JavaParserBaseListener {
    private JavaParser.PackageDeclarationContext packageDeclaration = null;
    private List<JavaParser.ImportDeclarationContext> importDeclarations = new ArrayList<>();
    private List<JavaParser.TypeDeclarationContext> typeDeclarations = new ArrayList<>();
    private Java java = new Java();
    private AccessModifier accessModifier = null;
    private MethodBody methodBody;
    private boolean isAbstractMethod = false;
    private boolean hasAbstractMethods = false;

    /**
     * <p> 構文木のルートノードに入った際の操作を行います </p>
     *
     * @param ctx 構文木のルートノードのコンテキスト
     */
    @Override
    public void enterCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof JavaParser.PackageDeclarationContext) {
                packageDeclaration = (JavaParser.PackageDeclarationContext) ctx.getChild(i);

            } else if (ctx.getChild(i) instanceof JavaParser.ImportDeclarationContext) {
                importDeclarations.add((JavaParser.ImportDeclarationContext) ctx.getChild(i));

            } else if (ctx.getChild(i) instanceof JavaParser.TypeDeclarationContext) {
                typeDeclarations.add((JavaParser.TypeDeclarationContext) ctx.getChild(i));
            }
        }
    }

    @Override
    public void enterTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
        hasAbstractMethods = false;
        Class javaClass = new Class();

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof JavaParser.ClassDeclarationContext) {
                javaClass.setName(ctx.getChild(i).getChild(1).getText());
                javaClass.setExtendsClass(searchExtendsClass((JavaParser.ClassDeclarationContext) ctx.getChild(i)));
            }
        }

        java.addClass(javaClass);
    }

    @Override
    public void exitTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
        java.getClasses().get(java.getClasses().size() - 1).setAbstract(hasAbstractMethods);
    }

    @Override
    public void enterClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {
        accessModifier = null;
        boolean isAlreadySearchedAccessModifier = false;
        isAbstractMethod = false;

        if (ctx.getChildCount() >= 2 && ctx.getChild(ctx.getChildCount() - 1) instanceof JavaParser.MemberDeclarationContext) {
            for (int i = 0; i < ctx.getChildCount() - 1; i++) {
                if (ctx.getChild(i).getChild(0) instanceof JavaParser.ClassOrInterfaceModifierContext) {
                    try {
                        accessModifier = AccessModifier.choose(ctx.getChild(i).getChild(0).getChild(0).getText());
                        isAlreadySearchedAccessModifier = true;
                    } catch (IllegalArgumentException e) {
                        // static, abstract, final, strictfp or Annotation
                        if (ctx.getChild(i).getChild(0).getChild(0).getText().equals("abstract")) {
                            isAbstractMethod = true;
                            hasAbstractMethods = true;
                        }
                    }
                }
            }
            if (!isAlreadySearchedAccessModifier) {
                accessModifier = AccessModifier.Package;
            }
        } else if (ctx.getChild(0) instanceof JavaParser.MemberDeclarationContext) {
            accessModifier = AccessModifier.Package;
        }
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        Field field = new Field();

        if (accessModifier != null) {
            field.setAccessModifier(accessModifier);
            accessModifier = null;
        }

        if (ctx.getChild(0).getChild(0) instanceof JavaParser.AnnotationContext) {
            field.setType(new Type(ctx.getChild(0).getChild(1).getText()));
        } else {
            field.setType(new Type(ctx.getChild(0).getChild(0).getText()));
        }

        field.setName(ctx.getChild(1).getChild(0).getChild(0).getChild(0).getText());

        // 既定値での配列宣言文
        if (ctx.getChild(1).getChild(0).getChildCount() > 1 &&
                ctx.getChild(1).getChild(0).getChild(2).getChild(0) instanceof JavaParser.ExpressionContext &&
                ctx.getChild(1).getChild(0).getChild(2).getChild(0).getChildCount() == 2 &&
                ctx.getChild(1).getChild(0).getChild(2).getChild(0).getChild(1) instanceof JavaParser.CreatorContext &&
                ctx.getChild(1).getChild(0).getChild(2).getChild(0).getChild(1).getChildCount() == 2 &&
                ctx.getChild(1).getChild(0).getChild(2).getChild(0).getChild(1).getChild(1) instanceof JavaParser.ArrayCreatorRestContext &&
                !ctx.getChild(1).getChild(0).getChild(2).getChild(0).getChild(1).getChild(1).getChild(1).getText().equals("]") &&
                ctx.getChild(1).getChild(0).getChild(2).getChild(0).getChild(1).getChild(1).getChild(1) instanceof JavaParser.ExpressionContext) {
            field.setArrayLength(new ArrayLength(Integer.parseInt(ctx.getChild(1).getChild(0).getChild(2).getChild(0).getChild(1).getChild(1).getChild(1).getText())));
        }

        java.getClasses().get(java.getClasses().size() - 1).addField(field);
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        Method method = new Method();
        methodBody = new MethodBody();

        if (accessModifier != null) {
            method.setAccessModifier(accessModifier);
            accessModifier = null;
        }

        if (isAbstractMethod) method.setAbstract(true);

        if (ctx.getChild(0).getChild(0) instanceof JavaParser.TypeTypeContext) {
            if (ctx.getChild(0).getChild(0).getChild(0) instanceof JavaParser.AnnotationContext) {
                method.setType(new Type(ctx.getChild(0).getChild(0).getChild(1).getText()));
            } else {
                method.setType(new Type(ctx.getChild(0).getChild(0).getChild(0).getText()));
            }
        } else {
            method.setType(new Type(ctx.getChild(0).getChild(0).getText()));
        }

        method.setName(ctx.getChild(1).getText());

        java.getClasses().get(java.getClasses().size() - 1).addMethod(method);
    }

    @Override
    public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        int lastClass = java.getClasses().size() - 1;
        int lastMethod = java.getClasses().get(lastClass).getMethods().size() - 1;

        java.getClasses().get(lastClass).getMethods().get(lastMethod).setMethodBody(methodBody);
    }

    @Override
    public void enterFormalParameter(JavaParser.FormalParameterContext ctx) {
        if (!(ctx.getParent().getParent().getParent() instanceof JavaParser.MethodDeclarationContext)) return;

        Argument argument = new Argument();

        if (ctx.getChild(0).getChild(0) instanceof JavaParser.AnnotationContext) {
            argument.setType(new Type(ctx.getChild(0).getChild(1).getText()));
        } else {
            argument.setType(new Type(ctx.getChild(0).getChild(0).getText()));
        }

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof JavaParser.VariableDeclaratorIdContext) {
                argument.setName(ctx.getChild(i).getChild(0).getText());
            }
        }

        int lastClass = java.getClasses().size() - 1;
        int lastMethod = java.getClasses().get(lastClass).getMethods().size() - 1;

        java.getClasses().get(lastClass).getMethods().get(lastMethod).addArgument(argument);
    }

    @Override
    public void enterBlockStatement(JavaParser.BlockStatementContext ctx) {
        if (!(ctx.getParent().getParent() instanceof JavaParser.MethodBodyContext)) return;

        if (ctx.getChild(0) instanceof JavaParser.LocalVariableDeclarationContext) {
            for (int i = 0; i < ctx.getChild(0).getChildCount(); i++) {
                if (ctx.getChild(0).getChild(i) instanceof JavaParser.VariableModifierContext) continue;

                addLocalVariableDeclaration(new Type(ctx.getChild(0).getChild(i).getText()),
                        (JavaParser.VariableDeclaratorsContext) ctx.getChild(0).getChild(i + 1));
                return;
            }

        } else if (ctx.getChild(0) instanceof JavaParser.StatementContext) {
            if (ctx.getChild(0).getChild(0) instanceof JavaParser.ExpressionContext) {
                if (ctx.getChild(0).getChild(0).getChildCount() == 3) {
                    if (ctx.getChild(0).getChild(0).getChild(0) instanceof JavaParser.ExpressionContext &&
                            ctx.getChild(0).getChild(0).getChild(2) instanceof JavaParser.ExpressionContext &&
                            ctx.getChild(0).getChild(0).getChild(1).getText().equals("=")) {
                        addAssignment(ctx.getChild(0).getChild(0).getChild(0).getText(), ctx.getChild(0).getChild(0).getChild(2).getText());
                    } else if (ctx.getChild(0).getChild(0).getChild(1).getText().equals("(") &&
                            ctx.getChild(0).getChild(0).getChild(2).getText().equals(")")) {
                        addMethod((JavaParser.ExpressionContext) ctx.getChild(0).getChild(0).getChild(0));
                    }
                }
            }
        }
    }

    private void addLocalVariableDeclaration(Type type, JavaParser.VariableDeclaratorsContext ctx) {

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i).getChildCount() == 1) {
                LocalVariableDeclaration local = new LocalVariableDeclaration(type, ctx.getChild(i).getText());
                methodBody.addStatement(local);
            } else {
                LocalVariableDeclaration local = new LocalVariableDeclaration(type, ctx.getChild(i).getChild(0).getText(), ctx.getChild(i).getChild(2).getText());
                methodBody.addStatement(local);
            }
        }
    }

    private void addAssignment(String name, String value) {
        Assignment assignment = new Assignment(name, value);
        methodBody.addStatement(assignment);
    }

    private void addMethod(JavaParser.ExpressionContext ctx) {
        if (ctx.getChildCount() == 1) {
            Method method = new Method(new Type("TmpType"), ctx.getText());
            methodBody.addStatement(method);
        } else if (ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals(".")) {
            Method method = new Method(new Type(ctx.getChild(0).getText()), ctx.getChild(2).getText());
            methodBody.addStatement(method);
        }
    }

    private Class searchExtendsClass(JavaParser.ClassDeclarationContext ctx) {

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof JavaParser.TypeTypeContext) {
                return new Class(ctx.getChild(i).getText());
            }
        }

        return null;
    }

    public JavaParser.PackageDeclarationContext getPackageDeclaration() throws NullPointerException {
        return packageDeclaration;
    }

    public List<JavaParser.ImportDeclarationContext> getImportDeclarations() {
        return importDeclarations;
    }

    public List<JavaParser.TypeDeclarationContext> getTypeDeclarations() {
        return typeDeclarations;
    }

    public Java getJava() {
        return java;
    }
}
