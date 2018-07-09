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
     * @throws IOException {@link #decorateStage(Stage, String, String)} メソッド参照
     */
    @Override
    public void start(Stage mainStage) throws IOException {
        String mainFxmlFileName = "retussMain.fxml";
        String mainTitle = "RETUSS : Real-time Ensure Traceability between UML and Source-code System";
        String codeFxmlFileName = "retussCode.fxml";
        String codeTitle = "new project";

        mainStage = decorateStage(mainStage, mainFxmlFileName, mainTitle);
        Stage codeStage = makeAdditionalStage(mainStage, codeFxmlFileName, codeTitle);

        mainStage.show();
        codeStage.show();
    }

    /**
     * <p> 追加ウィンドウのステージを作ります </p>
     *
     * @param ownerStage 土台となるステージ：土台ステージを消すと追加ステージも消える。
     * @param fxmlFileName 入力FXMLファイル名：{@code null} 不可
     * @param title ウィンドウタイトル
     * @return 追加するステージ
     * @throws IOException {@link #decorateStage(Stage, String, String)} メソッド参照
     */
    public Stage makeAdditionalStage(Stage ownerStage, String fxmlFileName, String title) throws IOException {
        Stage addStage = new Stage();
        addStage.initOwner(ownerStage);
        addStage.setScene(new Scene(new BorderPane()));

        addStage = decorateStage(addStage, fxmlFileName, title);

        return addStage;
    }

    /**
     * <p> 表示するステージを装飾します </p>
     *
     * @param stage 表示するステージ：特にメインウィンドウを表示する際は{@code null} 不可
     * @param fxmlFileName 入力FXMLファイル名：{@code null} 不可
     * @param title ウィンドウタイトル
     * @return 装飾されたステージ
     * @throws IOException FXMLファイルの入力エラーの場合
     */
    private Stage decorateStage(Stage stage, String fxmlFileName, String title) throws IOException {
        String resourcesPath = "./";
        Parent root = FXMLLoader.load(getClass().getResource(resourcesPath + fxmlFileName));
        stage.setTitle(title);
        stage.setScene(new Scene(root));

        return stage;
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
