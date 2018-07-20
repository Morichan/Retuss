package io.github.morichan.retuss.translator;

import io.github.morichan.retuss.language.java.Java;
import io.github.morichan.retuss.language.uml.Package;

/**
 * <p> 翻訳者クラス </p>
 */
public class Translator {

    private Package classDiagramPackage = new Package();

    private Java java = new Java();

    public Package getPackage() {
        return classDiagramPackage;
    }

    public Java getJava() {
        return java;
    }

    /**
     * <p> クラス図を基に各言語へ翻訳します </p>
     *
     * @param classDiagramPackage クラス図のパッケージ
     */
    public void translate(Package classDiagramPackage) {
        JavaTranslator javaTranslator = new JavaTranslator();

        java = javaTranslator.translate(classDiagramPackage);
    }

    /**
     * <p> Javaを基にクラス図へ翻訳します </p>
     *
     * @param java Javaソースコード
     */
    public void translate(Java java) {
        UMLTranslator umlTranslator = new UMLTranslator();

        classDiagramPackage = umlTranslator.translate(java);
    }
}
