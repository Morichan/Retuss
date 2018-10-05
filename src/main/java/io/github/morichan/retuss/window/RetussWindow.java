package io.github.morichan.retuss.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <p> RETUSSウィンドウクラス </p>
 *
 * <p>
 *     RETUSSのGUIとして、JavaFXを用いたウィンドウを起動するクラスです。
 * </p>
 */
public class RetussWindow extends Application {

    /**
     * <p> RETUSSを開始します </p>
     *
     * @param mainStage 初期ステージ：{@link Application} クラス参照
     * @throws IOException FXMLファイルの入力エラーの場合
     */
    @Override
    public void start(Stage mainStage) throws IOException {
        String mainFxmlFileName = "retussMain.fxml";
        String mainTitle = "RETUSS : Real-time Ensure Traceability between UML and Source-code System";
        String codeFxmlFileName = "retussCode.fxml";
        String codeTitle = "new project";

        String resourcesPath = "/";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcesPath + mainFxmlFileName));
        Parent root = loader.load();
        mainStage.setTitle(mainTitle);
        mainStage.setScene(new Scene(root));

        mainStage.show();

        // {@link MainController#showCodeStage(Stage, String, String)} と同様の処理
        MainController mainController = loader.getController();
        mainController.showCodeStage(mainStage, resourcesPath + codeFxmlFileName, codeTitle);
    }

    /**
     * <p> メインメソッド </p>
     *
     * <p>
     *     JavaFXを用いる場合はデフォルトで存在するメソッドです。
     *     ここから全てが始まります。
     * </p>
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        launch(args);
    }
}
