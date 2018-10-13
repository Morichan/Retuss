package io.github.morichan.retuss.listener;

import io.github.morichan.retuss.language.java.Class;
import io.github.morichan.retuss.language.java.Java;
import io.github.morichan.retuss.parser.java.JavaParser;
import io.github.morichan.retuss.parser.java.JavaParserBaseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Javaソースコードのパーサを利用したコンテキストの抽出クラス
 *
 * ANTLRに依存する。
 */
public class JavaEvalListener extends JavaParserBaseListener {
    private JavaParser.PackageDeclarationContext packageDeclaration = null;
    private List<JavaParser.ImportDeclarationContext> importDeclarations = new ArrayList<>();
    private List<JavaParser.TypeDeclarationContext> typeDeclarations = new ArrayList<>();
    private Java java = new Java();


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
        Class javaClass = new Class();

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof JavaParser.ClassDeclarationContext) {
                javaClass.setName(ctx.getChild(i).getChild(1).getText());
                javaClass.setExtendsClass(searchExtendsClass((JavaParser.ClassDeclarationContext) ctx.getChild(i)));
            }
        }




        java.addClass(javaClass);
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
