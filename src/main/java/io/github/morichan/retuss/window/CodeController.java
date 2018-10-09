package io.github.morichan.retuss.window;

import io.github.morichan.retuss.language.java.Class;
import io.github.morichan.retuss.language.java.Java;
import io.github.morichan.retuss.language.uml.Package;
import io.github.morichan.retuss.listener.JavaEvalListener;
import io.github.morichan.retuss.listener.JavaLanguage;
import io.github.morichan.retuss.translator.Translator;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

/**
 * <p> RETUSSコードウィンドウの動作管理クラス </p>
 *
 * <p>
 * {@link MainController}クラスで生成したretussCode.fxmlファイルにおけるシグナルハンドラを扱います。
 * </p>
 */
public class CodeController {
    @FXML
    private TabPane codeTabPane;

    private MainController mainController;

    private Java java = new Java();
    private Package umlPackage = new Package();
    private Translator translator = new Translator();
    private JavaLanguage javaLanguage = new JavaLanguage();

    @FXML
    private void initialize() {
        codeTabPane.getTabs().add(createLanguageTab("Java"));
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void createCodeTabs(Package classPackage) {
        translator.translate(classPackage);
        java = translator.getJava();
        setCodeTabs(java);
    }

    private Tab createLanguageTab(String tabName) {

        TabPane codeTabPane = new TabPane(createCodeTab(null));
        AnchorPane languageAnchor = new AnchorPane(codeTabPane);
        AnchorPane.setBottomAnchor(codeTabPane, 0.0);
        AnchorPane.setTopAnchor(codeTabPane, 0.0);
        AnchorPane.setLeftAnchor(codeTabPane, 0.0);
        AnchorPane.setRightAnchor(codeTabPane, 0.0);

        Tab languageTab = new Tab();
        languageTab.setContent(languageAnchor);
        languageTab.setText(tabName);

        return languageTab;
    }

    private Tab createCodeTab(Class javaClass) {
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        // codeArea.appendText(java.getClasses().get(0).toString());
        codeArea.setOnKeyTyped(event -> {
            convertCodeToUml();
        });
        codeArea.setOnMouseClicked(event -> {
            //translator.translate(classDiagramDrawer.extractPackage());
        });

        // if (translator.getJava().getClasses().size() > 0) setCodeTabs(translator.getJava());
        // if (java.getClasses().size() > 0 && !java.getClasses().get(0).toString().equals(codeArea.getText())) codeArea.replaceText(java.getClasses().get(0).toString());
        if (javaClass != null) codeArea.replaceText(javaClass.toString());

        AnchorPane codeAnchor = new AnchorPane(codeArea);
        AnchorPane.setBottomAnchor(codeArea, 0.0);
        AnchorPane.setTopAnchor(codeArea, 0.0);
        AnchorPane.setLeftAnchor(codeArea, 0.0);
        AnchorPane.setRightAnchor(codeArea, 0.0);

        Tab codeTab = new Tab();
        codeTab.setContent(codeAnchor);
        if (javaClass == null) codeTab.setText("<Unknown Title>");
        else codeTab.setText(javaClass.getName());
        codeTab.setClosable(false);

        return codeTab;
    }

    private void setCodeTabs(Java java) {
        ((TabPane) ((AnchorPane) codeTabPane.getTabs().get(0).getContent()).getChildren().get(0)).getTabs().clear();
        for (Class javaClass : java.getClasses()) {
            Tab tab = createCodeTab(javaClass);
            ((TabPane) ((AnchorPane) codeTabPane.getTabs().get(0).getContent()).getChildren().get(0)).getTabs().add(tab);
        }
    }

    private void convertCodeToUml() {
        if (java.getClasses() == null) return;

        try {
            javaLanguage.parseForClassDiagram(getCode(0));
            translator.translate(javaLanguage.getJava());

            umlPackage = translator.getPackage();

            mainController.writeUmlForCode(umlPackage);

        } catch (NullPointerException e) {
            System.out.println("This is Parse Error because JavaEvalListener object is null, but no problem.");
        }
    }

    private String getCode(int tabNumber) {
        return ((CodeArea) ((AnchorPane) ((TabPane) ((AnchorPane) codeTabPane.getTabs().get(0).getContent()).getChildren().get(0)).getTabs().get(tabNumber).getContent()).getChildren().get(0)).getText();
    }
}
