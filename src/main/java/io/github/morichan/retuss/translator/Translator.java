package io.github.morichan.retuss.translator;

import io.github.morichan.retuss.language.java.*;
import io.github.morichan.retuss.language.uml.Package;

/**
 * <p> 翻訳者クラス </p>
 */
public class Translator {

    private Package classDiagramPackage = new Package();
    private Java java = new Java();
    private JavaTranslator javaTranslator = new JavaTranslator();

    public void setPackage(Package classPackage) {
        this.classDiagramPackage = classPackage;
    }

    public Package getPackage() {
        return classDiagramPackage;
    }

    public Java getJava() {
        return java;
    }

    /**
     * <p> クラス図を基に各言語へ翻訳します </p>
     */
    public void translateFromClassDiagram() {
        java = javaTranslator.translate(classDiagramPackage.getClasses());
    }
}
