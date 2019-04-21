package io.github.morichan.retuss.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mockit.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class RetussWindowTest {
    @Mocked
    private Application app;

    @Tested
    RetussWindow retussWindow = new RetussWindow();

    @Test
    @Disabled
    void main文を実行するとlaunchメソッドを1回実行する() {
        // 初期化
        new Expectations() {{
            String[] args = {};
            app.launch( args );
            times = 1;
        }};

        // 実行
        String[] args = {};

        // 検証
        retussWindow.main( args );
    }
}