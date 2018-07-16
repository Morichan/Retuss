package io.github.morichan.retuss.listener;

import io.github.morichan.retuss.parser.java.JavaParser;
import io.github.morichan.retuss.parser.java.JavaParserBaseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Javaソースコードのパーサを利用したコンテキストの抽出クラス
 *
 * ANTLRに依存する。
 */
public class JavaEvalListener extends JavaParserBaseListener {
    private JavaParser.PackageDeclarationContext packageDeclaration = null;
    private List<JavaParser.ImportDeclarationContext> importDeclarations = new ArrayList<>();
    private List<JavaParser.TypeDeclarationContext> typeDeclarations = new ArrayList<>();

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

    public JavaParser.PackageDeclarationContext getPackageDeclaration() throws NullPointerException {
        return packageDeclaration;
    }

    public List<JavaParser.ImportDeclarationContext> getImportDeclarations() {
        return importDeclarations;
    }

    public List<JavaParser.TypeDeclarationContext> getTypeDeclarations() {
        return typeDeclarations;
    }
}
