package io.github.morichan.retuss.window;

import io.github.morichan.retuss.window.diagram.ClassNodeDiagram;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import mockit.*;
import org.fxmisc.richtext.CodeArea;
import org.junit.jupiter.api.*;
import org.testfx.api.FxRobotException;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Tag("GUITests")
class MainControllerTest extends ApplicationTest {
    Point2D xButtonOnDialogBox;
    String okButtonOnDialogBox;

    @AfterAll
    static void reset() {
        ClassNodeDiagram.resetNodeCount();
    }

    @Nested
    class メイン画面において extends ApplicationTest {
        Stage stage;

        @Start
        public void start(Stage stage) throws IOException {
            String fxmlFileName = "/retussMain.fxml";
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFileName)));
            stage.setScene(scene);
            stage.show();
            this.stage = stage;
        }

        @Nested
        class クラス図の場合 {
            Point2D topLeftCorner;
            Point2D bottomRightCorner;
            Point2D firstClickedClassDiagramCanvas;
            Point2D secondClickedClassDiagramCanvas;
            Point2D thirdClickedClassDiagramCanvas;
            Point2D betweenFirstAndSecondClickedClassDiagramCanvas;
            Point2D betweenFirstAndThirdClickedClassDiagramCanvas;
            Point2D betweenSecondAndThirdClickedClassDiagramCanvas;
            String changeClassMenu;
            String deleteClassMenu;
            String classAttributeMenu;
            String classOperationMenu;
            String addMenu;
            String changeMenu;
            String deleteMenu;
            String checkMenu;

            @BeforeEach
            void setup() {
                topLeftCorner = new Point2D(650, 163);
                bottomRightCorner = new Point2D(1583, 984);
                xButtonOnDialogBox = new Point2D(1050.0, 350.0);
                okButtonOnDialogBox = "OK";
                firstClickedClassDiagramCanvas = new Point2D(900.0, 600.0);
                secondClickedClassDiagramCanvas = new Point2D(1050.0, 300.0);
                thirdClickedClassDiagramCanvas = new Point2D(800.0, 450.0);
                betweenFirstAndSecondClickedClassDiagramCanvas = new Point2D(
                        firstClickedClassDiagramCanvas.getX() + (secondClickedClassDiagramCanvas.getX() - firstClickedClassDiagramCanvas.getX()) / 2,
                        firstClickedClassDiagramCanvas.getY() - (firstClickedClassDiagramCanvas.getY() - secondClickedClassDiagramCanvas.getY()) / 2
                );
                betweenFirstAndThirdClickedClassDiagramCanvas = new Point2D(
                        firstClickedClassDiagramCanvas.getX() + (thirdClickedClassDiagramCanvas.getX() - firstClickedClassDiagramCanvas.getX()) / 2,
                        firstClickedClassDiagramCanvas.getY() - (firstClickedClassDiagramCanvas.getY() - thirdClickedClassDiagramCanvas.getY()) / 2
                );
                betweenSecondAndThirdClickedClassDiagramCanvas = new Point2D(
                        thirdClickedClassDiagramCanvas.getX() + (secondClickedClassDiagramCanvas.getX() - thirdClickedClassDiagramCanvas.getX()) / 2,
                        thirdClickedClassDiagramCanvas.getY() - (thirdClickedClassDiagramCanvas.getY() - secondClickedClassDiagramCanvas.getY()) / 2
                );

                changeClassMenu = "クラスの名前の変更";
                deleteClassMenu = "クラスをモデルから削除";
                classAttributeMenu = "属性";
                classOperationMenu = "操作";
                addMenu = "追加";
                changeMenu = "変更";
                deleteMenu = "削除";
                checkMenu = "表示選択";
            }

            @Nested
            class ボタンに関して {

                @BeforeEach
                void reset() {
                    ClassNodeDiagram.resetNodeCount();
                }

                @Test
                void どのボタンもクリックしていない場合はどれも選択していない() {
                    Button normalButton = (Button) lookup("#normalButtonInCD").query();
                    Button classButton = (Button) lookup("#classButtonInCD").query();
                    Button noteButton = (Button) lookup("#noteButtonInCD").query();

                    assertThat(normalButton.isDefaultButton()).isFalse();
                    assertThat(classButton.isDefaultButton()).isFalse();
                    assertThat(noteButton.isDefaultButton()).isFalse();
                }

                @Test
                void ノーマルボタンをクリックするとノーマルボタンをオンにして他をオフにする() {
                    clickOn("#normalButtonInCD");

                    Button normalButton = (Button) lookup("#normalButtonInCD").query();
                    Button classButton = (Button) lookup("#classButtonInCD").query();
                    Button noteButton = (Button) lookup("#noteButtonInCD").query();

                    assertThat(normalButton.isDefaultButton()).isTrue();
                    assertThat(classButton.isDefaultButton()).isFalse();
                    assertThat(noteButton.isDefaultButton()).isFalse();
                }

                @Test
                void クラスボタンをクリックするとクラスボタンをオンにして他をオフにする() {
                    clickOn("#classButtonInCD");

                    Button normalButton = (Button) lookup("#normalButtonInCD").query();
                    Button classButton = (Button) lookup("#classButtonInCD").query();
                    Button noteButton = (Button) lookup("#noteButtonInCD").query();

                    assertThat(normalButton.isDefaultButton()).isFalse();
                    assertThat(classButton.isDefaultButton()).isTrue();
                    assertThat(noteButton.isDefaultButton()).isFalse();
                }

                @Test
                void ノートボタンをクリックするとノートボタンをオンにして他をオフにする() {
                    clickOn("#noteButtonInCD");

                    Button noteButton = (Button) lookup("#noteButtonInCD").query();
                    Button normalButton = (Button) lookup("#normalButtonInCD").query();
                    Button classButton = (Button) lookup("#classButtonInCD").query();

                    assertThat(normalButton.isDefaultButton()).isFalse();
                    assertThat(classButton.isDefaultButton()).isFalse();
                    assertThat(noteButton.isDefaultButton()).isTrue();
                }

                @Test
                void クラスボタンを選択している際にクラスボタンをクリックするとオンにして他をオフにする() {
                    clickOn("#classButtonInCD");
                    clickOn("#classButtonInCD");

                    Button normalButton = (Button) lookup("#normalButtonInCD").query();
                    Button classButton = (Button) lookup("#classButtonInCD").query();
                    Button noteButton = (Button) lookup("#noteButtonInCD").query();

                    assertThat(normalButton.isDefaultButton()).isFalse();
                    assertThat(classButton.isDefaultButton()).isTrue();
                    assertThat(noteButton.isDefaultButton()).isFalse();
                }
            }

            @Nested
            class キャンバスに関して {

                @BeforeEach
                void reset() {
                    ClassNodeDiagram.resetNodeCount();
                }

                @Nested
                class ノーマルアイコンを選択している際に {

                    @BeforeEach
                    void reset() {
                        ClassNodeDiagram.resetNodeCount();
                    }

                    @Nested
                    class クラスを1つ記述した状態で {

                        @BeforeEach
                        void reset() {
                            ClassNodeDiagram.resetNodeCount();
                        }

                        @Test
                        void 右クリックした場合は変更メニューと削除メニューを表示する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);

                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).isEqualTo("ClassNameクラスの名前の変更");
                            assertThat(scrollPane.getContextMenu().getItems().get(1).getText()).isEqualTo("ClassNameクラスをモデルから削除");
                        }

                        @Test
                        void 削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            clickOn("ClassName" + deleteClassMenu);

                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                        }

                        @Test
                        void 右クリックした後で何も描かれていない箇所を右クリックしても何も表示しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            rightClickOn(secondClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);

                            assertThat(scrollPane.getContextMenu()).isNull();
                        }

                        @Test
                        void 変更メニューを選択した場合はクラス名を変更する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            clickOn("ClassName" + changeClassMenu);
                            write("ChangedClassName");
                            clickOn(okButtonOnDialogBox);
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas);

                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("ChangedClassName");
                        }

                        @Test
                        void 変更メニューを選択し何も入力しなかった場合はクラス名を変更しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            clickOn("ClassName" + changeClassMenu);
                            push(KeyCode.BACK_SPACE);
                            clickOn(okButtonOnDialogBox);
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas);

                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("ClassName");
                        }

                        @Test
                        void 属性の追加メニューを選択した場合は属性を追加する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            GraphicsContext gc = getGraphicsContext(stage);
                            Paint fillColor = gc.getFill();
                            Paint strokeColor = gc.getStroke();
                            TextAlignment textAlignment = gc.getTextAlign();

                            assertThat(fillColor).isEqualTo(Color.BLACK);
                            assertThat(strokeColor).isEqualTo(Color.BLACK);
                            assertThat(textAlignment).isEqualTo(TextAlignment.LEFT);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("- attribute : int");
                        }

                        @Test
                        void 追加済みの属性の変更メニューを選択した場合は変更した属性を描画する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            changeAttribute(firstClickedClassDiagramCanvas, "- attribute : int", "- attribute : double");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("- attribute : double");
                        }

                        @Test
                        void 追加済みの属性の変更メニューを選択した際に何も入力しない場合は属性を変更しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            changeAttribute(firstClickedClassDiagramCanvas, "- attribute : int", "");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("- attribute : int");
                        }

                        @Test
                        void 追加済みの属性の削除メニューを選択した場合は属性を削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            deleteAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("なし");
                        }

                        @Test
                        void 追加済みの属性の表示選択チェックメニューのチェックを外した場合は属性を非表示にする() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            checkAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(3)).getItems().get(0)).isSelected()).isFalse();

                            GraphicsContext gc = getGraphicsContext(stage);
                            TextAlignment textAlignment = gc.getTextAlign();
                            assertThat(textAlignment).isEqualTo(TextAlignment.CENTER);
                        }

                        @Test
                        void 追加済みの非表示の属性の表示選択チェックメニューのチェックを付けた場合は属性を表示する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- attribute : int");
                            checkAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(3)).getItems().get(0)).isSelected()).isFalse();

                            checkAttribute(firstClickedClassDiagramCanvas, "- attribute : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(3)).getItems().get(0)).isSelected()).isTrue();
                        }

                        @Test
                        void 操作の追加メニューを選択した場合は操作を追加する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addOperation(firstClickedClassDiagramCanvas, "+ operation() : void");

                            GraphicsContext gc = getGraphicsContext(stage);
                            Paint fillColor = gc.getFill();
                            Paint strokeColor = gc.getStroke();
                            TextAlignment textAlignment = gc.getTextAlign();

                            assertThat(fillColor).isEqualTo(Color.BLACK);
                            assertThat(strokeColor).isEqualTo(Color.BLACK);
                            assertThat(textAlignment).isEqualTo(TextAlignment.LEFT);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("+ operation() : void");
                        }

                        @Test
                        void 追加済みの操作の変更メニューを選択した場合は変更した操作を描画する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addOperation(firstClickedClassDiagramCanvas, "+ operation() : void");

                            changeOperation(firstClickedClassDiagramCanvas, "+ operation() : void", "+ operation() : double");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("+ operation() : double");
                        }

                        @Test
                        void 追加済みの操作の削除メニューを選択した場合は操作を削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addOperation(firstClickedClassDiagramCanvas, "+ operation() : void");

                            deleteOperation(firstClickedClassDiagramCanvas, "+ operation() : void");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("なし");
                        }

                        @Test
                        void 追加済みの操作の表示選択チェックメニューのチェックを外した場合は操作を非表示にする() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addOperation(firstClickedClassDiagramCanvas, "+ operation() : int");

                            checkOperation(firstClickedClassDiagramCanvas, "+ operation() : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(3)).getItems().get(0)).isSelected()).isFalse();

                            GraphicsContext gc = getGraphicsContext(stage);
                            TextAlignment textAlignment = gc.getTextAlign();
                            assertThat(textAlignment).isEqualTo(TextAlignment.CENTER);
                        }

                        @Test
                        void 追加済みの操作の変更メニューを選択した際に何も入力しない場合は操作を変更しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addOperation(firstClickedClassDiagramCanvas, "+ operation() : int");

                            changeOperation(firstClickedClassDiagramCanvas, "+ operation() : int", "");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("+ operation() : int");
                        }

                        @Test
                        void 追加済みの非表示の操作の表示選択チェックメニューのチェックを付けた場合は操作を表示する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            addOperation(firstClickedClassDiagramCanvas, "+ operation() : int");
                            checkOperation(firstClickedClassDiagramCanvas, "+ operation() : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(3)).getItems().get(0)).isSelected()).isFalse();
                            checkOperation(firstClickedClassDiagramCanvas, "+ operation() : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(3)).getItems().get(0)).isSelected()).isTrue();
                        }

                        @Test
                        void 描画済みのクラスの4隅を右クリックすると各箇所でメニューを表示する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "Test");
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 49, firstClickedClassDiagramCanvas.getY() + 39);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 49, firstClickedClassDiagramCanvas.getY() + 39);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 49, firstClickedClassDiagramCanvas.getY() - 39);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 49, firstClickedClassDiagramCanvas.getY() - 39);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                        }

                        @Test
                        void 描画済みのクラスの4隅より広い8箇所を右クリックしても何も表示しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "Test");
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY() + 41);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX(), firstClickedClassDiagramCanvas.getY() + 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY() + 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY());
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY());
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY() - 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX(), firstClickedClassDiagramCanvas.getY() - 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY() - 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                        }

                        @Test
                        void 描画済みの属性と操作を2つづつ持つクラスの4隅を右クリックすると各箇所でメニューを表示する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "Test");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- x : int");
                            addAttribute(firstClickedClassDiagramCanvas, "- y : int");
                            addOperation(firstClickedClassDiagramCanvas, "+ z() : c");
                            addOperation(firstClickedClassDiagramCanvas, "+ w() : c");

                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 49, firstClickedClassDiagramCanvas.getY() + 59);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 49, firstClickedClassDiagramCanvas.getY() + 59);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 49, firstClickedClassDiagramCanvas.getY() - 59);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 49, firstClickedClassDiagramCanvas.getY() - 59);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                        }

                        @Test
                        void 描画済みの属性と操作を2つづつ持つクラスの4隅より広い8箇所を右クリックしても何も表示しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "Test");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- x : int");
                            addAttribute(firstClickedClassDiagramCanvas, "- y : int");
                            addOperation(firstClickedClassDiagramCanvas, "+ z() : c");
                            addOperation(firstClickedClassDiagramCanvas, "+ w() : c");

                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY() + 61);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX(), firstClickedClassDiagramCanvas.getY() + 61);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY() + 61);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY());
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY());
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY() - 61);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX(), firstClickedClassDiagramCanvas.getY() - 61);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY() - 61);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                        }

                        @Test
                        void 描画済みの非表示の属性と操作を2つづつ持つクラスの4隅を右クリックするとメニューを表示するがそれより広い8箇所を右クリックしても何も表示しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "Test");
                            clickOn("#normalButtonInCD");
                            addAttribute(firstClickedClassDiagramCanvas, "- longLongAttribute1 : int");
                            addAttribute(firstClickedClassDiagramCanvas, "- longLongAttribute2 : double");
                            addOperation(firstClickedClassDiagramCanvas, "+ longLongOperation1() : character");
                            addOperation(firstClickedClassDiagramCanvas, "+ longLongOperation2() : float");

                            checkAttribute(firstClickedClassDiagramCanvas, "- longLongAttribute1 : int");
                            checkAttribute(firstClickedClassDiagramCanvas, "- longLongAttribute2 : double");
                            checkOperation(firstClickedClassDiagramCanvas, "+ longLongOperation1() : character");
                            checkOperation(firstClickedClassDiagramCanvas, "+ longLongOperation2() : float");

                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 49, firstClickedClassDiagramCanvas.getY() + 39);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 49, firstClickedClassDiagramCanvas.getY() + 39);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 49, firstClickedClassDiagramCanvas.getY() - 39);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 49, firstClickedClassDiagramCanvas.getY() - 39);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("Test");

                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY() - 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY() + 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX(), firstClickedClassDiagramCanvas.getY() + 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY() + 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() - 51, firstClickedClassDiagramCanvas.getY());
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY());
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX() + 51, firstClickedClassDiagramCanvas.getY() - 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(firstClickedClassDiagramCanvas.getX(), firstClickedClassDiagramCanvas.getY() - 41);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                        }
                    }

                    @Nested
                    class クラスを複数記述した状態で {

                        @BeforeEach
                        void reset() {
                            ClassNodeDiagram.resetNodeCount();
                        }

                        @Test
                        void 描画済みクラス2つの内1つ目のクラスを削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            clickOn("FirstClassName" + deleteClassMenu);

                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(secondClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("SecondClassName");
                        }

                        @Test
                        void 描画済みクラス2つの内1つ目のクラスを削除した後3つ目のクラスを描画する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            clickOn("FirstClassName" + deleteClassMenu);

                            clickOn("#classButtonInCD");
                            drawClasses(thirdClickedClassDiagramCanvas, "ThirdClassName");

                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(secondClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("SecondClassName");
                            rightClickOn(thirdClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("ThirdClassName");
                        }

                        @Test
                        void 描画済みクラス2つの内1つ目のクラスを削除した後3つ目のクラスを削除した場所に描画する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#normalButtonInCD");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            clickOn("FirstClassName" + deleteClassMenu);

                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ThirdClassName");

                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("ThirdClassName");
                            rightClickOn(secondClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("SecondClassName");
                        }

                        @Test
                        void 描画済みクラス2つ間の関係属性の範囲外を右クリックしても右クリックメニューは表示しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#compositionButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            write("- composition");
                            clickOn(okButtonOnDialogBox);

                            Point2D noDrawnPoint1 = new Point2D(secondClickedClassDiagramCanvas.getX() + 10.0, firstClickedClassDiagramCanvas.getY() + 10.0);
                            Point2D noDrawnPoint2 = new Point2D(secondClickedClassDiagramCanvas.getX() - 50.0, firstClickedClassDiagramCanvas.getY() - 100.0);
                            Point2D noDrawnPoint3 = new Point2D(firstClickedClassDiagramCanvas.getX() + 50.0, secondClickedClassDiagramCanvas.getY() + 100.0);
                            Point2D noDrawnPoint4 = new Point2D(firstClickedClassDiagramCanvas.getX() - 10.0, secondClickedClassDiagramCanvas.getY() - 10.0);

                            clickOn("#normalButtonInCD");
                            rightClickOn(noDrawnPoint1);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(noDrawnPoint2);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(noDrawnPoint3);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(noDrawnPoint4);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                        }

                        @Test
                        void 描画済みクラス2つ間の関係属性を右クリックし変更を選択すると変更したコンポジション関係を描画する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#compositionButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            write("- composition");
                            clickOn(okButtonOnDialogBox);

                            clickOn("#normalButtonInCD");
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                            clickOn("- composition の変更");

                            write("+ changedComposition");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);

                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("+ changedComposition");
                        }

                        @Test
                        void 描画済みクラス2つ間の関係属性を右クリックし変更を選択し何も記述しなかった場合はコンポジション関係を変更しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#compositionButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            write("- notChangingComposition");
                            clickOn(okButtonOnDialogBox);

                            clickOn("#normalButtonInCD");
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                            clickOn("- notChangingComposition の変更");

                            push(KeyCode.BACK_SPACE);
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);

                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- notChangingComposition");
                        }

                        @Test
                        void 描画済みクラス2つ間の関係属性を右クリックし削除を選択するとコンポジション関係を削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#compositionButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            write("- composition");
                            clickOn(okButtonOnDialogBox);

                            clickOn("#normalButtonInCD");
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                            clickOn("- composition の削除");

                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);

                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                        }

                        @Test
                        void 描画済みクラス2つ間に関係属性を2つ描画すると右クリックメニュー上では最後に描画したメニュー内容を返す() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#compositionButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            write("- composition1");
                            clickOn(okButtonOnDialogBox);
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            write("- composition2");
                            clickOn(okButtonOnDialogBox);

                            clickOn("#normalButtonInCD");
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);

                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- composition2");
                        }

                        @Test
                        void 描画済みクラス3つ間に関係属性を2つ描画する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            drawClasses(thirdClickedClassDiagramCanvas, "ThirdClassName");

                            clickOn("#compositionButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            write("- composition1");
                            clickOn(okButtonOnDialogBox);
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(thirdClickedClassDiagramCanvas);
                            write("- composition2");
                            clickOn(okButtonOnDialogBox);

                            clickOn("#normalButtonInCD");
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- composition1");

                            rightClickOn(betweenFirstAndThirdClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- composition2");
                        }

                        @Test
                        void 描画済みクラス3つ間に関係属性を3つ描画する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            drawClasses(thirdClickedClassDiagramCanvas, "ThirdClassName");
                            clickOn("#compositionButtonInCD");

                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            write("- compositionFromFirstToSecond");
                            clickOn(okButtonOnDialogBox);
                            clickOn(secondClickedClassDiagramCanvas);
                            clickOn(thirdClickedClassDiagramCanvas);
                            write("- compositionFromSecondToThird");
                            clickOn(okButtonOnDialogBox);
                            clickOn(thirdClickedClassDiagramCanvas);
                            clickOn(firstClickedClassDiagramCanvas);
                            write("- compositionFromThirdToFirst");
                            clickOn(okButtonOnDialogBox);

                            clickOn("#normalButtonInCD");
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- compositionFromFirstToSecond");

                            rightClickOn(betweenSecondAndThirdClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- compositionFromSecondToThird");

                            rightClickOn(betweenFirstAndThirdClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- compositionFromThirdToFirst");
                        }

                        @Test
                        void 描画済みクラス2つ間の汎化関係を右クリックし削除を選択すると汎化関係を削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            clickOn("#generalizationButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);

                            clickOn("#normalButtonInCD");
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                            clickOn("汎化の削除");

                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);

                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                        }

                        @Test
                        void 描画済みクラス3つ間のうち多重継承の関係属性を2つ描画しようとすると最初に記述した汎化関係を削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                            drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                            drawClasses(thirdClickedClassDiagramCanvas, "ThirdClassName");

                            clickOn("#generalizationButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(secondClickedClassDiagramCanvas);
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(thirdClickedClassDiagramCanvas);

                            clickOn("#normalButtonInCD");
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();

                            rightClickOn(betweenFirstAndThirdClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).isEqualTo("汎化の削除");
                        }

                        @Test
                        void 描画したクラスをドラッグするとドラッグしたクラスからの関係も同時に移動する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "Test1");
                            drawClasses(thirdClickedClassDiagramCanvas, "Test2");
                            clickOn("#compositionButtonInCD");
                            clickOn(firstClickedClassDiagramCanvas);
                            clickOn(thirdClickedClassDiagramCanvas);
                            write("- composition");
                            clickOn(okButtonOnDialogBox);
                            clickOn("#normalButtonInCD");
                            drag(firstClickedClassDiagramCanvas).dropTo(secondClickedClassDiagramCanvas);

                            rightClickOn(betweenFirstAndThirdClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(betweenSecondAndThirdClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- composition");
                        }

                        @Test
                        void 描画したクラスをドラッグするとドラッグしたクラスへの関係も同時に移動する() {
                            clickOn("#classButtonInCD");
                            drawClasses(secondClickedClassDiagramCanvas, "Test1");
                            drawClasses(thirdClickedClassDiagramCanvas, "Test2");
                            clickOn("#compositionButtonInCD");
                            clickOn(secondClickedClassDiagramCanvas);
                            clickOn(thirdClickedClassDiagramCanvas);
                            write("- composition");
                            clickOn(okButtonOnDialogBox);
                            clickOn("#normalButtonInCD");
                            drag(thirdClickedClassDiagramCanvas).dropTo(firstClickedClassDiagramCanvas);

                            rightClickOn(betweenSecondAndThirdClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu()).isNull();
                            rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- composition");
                        }
                    }
                }

                @Test
                void クラスアイコンを選択していない際にキャンバスをクリックしても何も表示しない() {
                    clickOn("#classDiagramCanvas");

                    assertThrows(FxRobotException.class, () -> clickOn(okButtonOnDialogBox));
                }

                @Nested
                class クラスアイコンを選択している際に {

                    @BeforeEach
                    void reset() {
                        ClassNodeDiagram.resetNodeCount();
                    }

                    @Test
                    void キャンバスをクリックするとクラス名の入力ウィンドウを表示する(@Mocked TextInputDialog mock) {
                        clickOn("#classButtonInCD");
                        clickOn("#classDiagramCanvas");

                        new Verifications() {{
                            mock.showAndWait();
                            times = 1;
                        }};
                    }

                    @Test
                    void キャンバスをクリックしクラス名を入力すると入力したクラス名のクラスを表示する() {
                        clickOn("#classButtonInCD");
                        clickOn("#classDiagramCanvas");

                        write("ClassName");
                        clickOn(okButtonOnDialogBox);
                        GraphicsContext gc = getGraphicsContext(stage);
                        Paint fillColor = gc.getFill();
                        Paint strokeColor = gc.getStroke();
                        TextAlignment textAlignment = gc.getTextAlign();

                        assertThat(fillColor).isEqualTo(Color.BLACK);
                        assertThat(strokeColor).isEqualTo(Color.BLACK);
                        assertThat(textAlignment).isEqualTo(TextAlignment.CENTER);

                        clickOn("#normalButtonInCD");
                        rightClickOn("#classDiagramCanvas");

                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("ClassName");
                    }

                    @Test
                    void クラスを2つ表示する() {
                        clickOn("#classButtonInCD");
                        clickOn("#classDiagramCanvas");

                        write("FirstClassName");
                        clickOn(okButtonOnDialogBox);
                        clickOn(secondClickedClassDiagramCanvas);
                        write("SecondClassName");
                        clickOn(okButtonOnDialogBox);

                        clickOn("#normalButtonInCD");
                        rightClickOn("#classDiagramCanvas");
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("FirstClassName");
                        rightClickOn(secondClickedClassDiagramCanvas);
                        scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("SecondClassName");
                    }

                    @Test
                    void キャンバスをクリックしクラス名を入力しない場合は何も表示しない() {
                        clickOn("#classButtonInCD");
                        clickOn("#classDiagramCanvas");

                        clickOn(okButtonOnDialogBox);

                        clickOn("#normalButtonInCD");
                        rightClickOn("#classDiagramCanvas");
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu()).isNull();
                    }

                    @Test
                    void 何も描画されていないキャンバスを右クリックした場合は何も表示しない() {
                        clickOn("#classButtonInCD");
                        rightClickOn("#classDiagramCanvas");

                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);

                        assertThat(scrollPane.getContextMenu()).isNull();
                    }

                    @Test
                    void キャンバスに描かれているClassNameクラスを右クリックした場合は何も表示しない() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "ClassName");

                        rightClickOn(firstClickedClassDiagramCanvas);
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);

                        assertThat(scrollPane.getContextMenu()).isNull();
                    }

                    @Test
                    void キャンバスの左上隅をクリックすると描画クラスがキャンバス枠を超えない位置に描画する() {
                        clickOn("#classButtonInCD");
                        drawClasses(topLeftCorner, "Test");
                        clickOn("#normalButtonInCD");

                        rightClickOn(topLeftCorner.getX(), topLeftCorner.getY());
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu()).isNull();
                    }

                    @Test
                    void キャンバスの右下隅をクリックすると描画クラスがキャンバス枠を超えない位置に描画する() {
                        clickOn("#classButtonInCD");
                        drawClasses(bottomRightCorner, "Test");
                        clickOn("#normalButtonInCD");

                        rightClickOn(bottomRightCorner.getX(), bottomRightCorner.getY());
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu()).isNull();
                    }

                    @Test
                    void 描画したクラスをドラッグすると描画クラスを移動先にのみ描画する() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "Test");
                        clickOn("#normalButtonInCD");
                        drag(firstClickedClassDiagramCanvas).dropTo(secondClickedClassDiagramCanvas);

                        rightClickOn(firstClickedClassDiagramCanvas.getX(), firstClickedClassDiagramCanvas.getY());
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu()).isNull();
                        rightClickOn(secondClickedClassDiagramCanvas.getX(), secondClickedClassDiagramCanvas.getY());
                        scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu()).isNotNull();
                    }

                    @Test
                    void 描画したクラスを枠外までドラッグすると描画クラスを枠内の移動先にのみ描画する() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "Test");
                        clickOn("#normalButtonInCD");
                        drag(firstClickedClassDiagramCanvas).dropTo(firstClickedClassDiagramCanvas.getX() - 400.0, firstClickedClassDiagramCanvas.getY());

                        rightClickOn(firstClickedClassDiagramCanvas.getX(), firstClickedClassDiagramCanvas.getY());
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu()).isNull();
                        rightClickOn(firstClickedClassDiagramCanvas.getX() - 200.0, firstClickedClassDiagramCanvas.getY());
                        scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu()).isNotNull();
                    }
                }

                @Nested
                class コンポジションアイコンを選択している際に {

                    @BeforeEach
                    void reset() {
                        ClassNodeDiagram.resetNodeCount();
                    }

                    @Test
                    void キャンバスに描かれているClassNameクラスをクリックするとClassNameクラスの縁の色を変更する() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                        clickOn("#compositionButtonInCD");

                        clickOn(firstClickedClassDiagramCanvas);
                        GraphicsContext gc = getGraphicsContext(stage);
                        Paint fillColor = gc.getFill();
                        Paint strokeColor = gc.getStroke();
                        TextAlignment textAlignment = gc.getTextAlign();

                        assertThat(fillColor).isEqualTo(Color.BLACK);
                        assertThat(strokeColor).isEqualTo(Color.RED);
                        assertThat(textAlignment).isEqualTo(TextAlignment.CENTER);
                    }

                    @Test
                    void キャンバスに描かれているClassNameクラスをクリックした後にクラスを描画していない箇所をクリックするとClassNameクラスの縁の色を元に戻す() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                        clickOn("#compositionButtonInCD");

                        clickOn(firstClickedClassDiagramCanvas);
                        clickOn(secondClickedClassDiagramCanvas);
                        GraphicsContext gc = getGraphicsContext(stage);
                        Paint fillColor = gc.getFill();
                        Paint strokeColor = gc.getStroke();
                        TextAlignment textAlignment = gc.getTextAlign();

                        assertThat(fillColor).isEqualTo(Color.BLACK);
                        assertThat(strokeColor).isEqualTo(Color.BLACK);
                        assertThat(textAlignment).isEqualTo(TextAlignment.CENTER);
                    }

                    @Test
                    void キャンバスに描かれている2つのクラスをクリックしDialogに関係属性を記述するとコンポジション関係を描画する() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                        drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                        clickOn("#compositionButtonInCD");

                        clickOn(firstClickedClassDiagramCanvas);
                        clickOn(secondClickedClassDiagramCanvas);
                        write("- composition");
                        clickOn(okButtonOnDialogBox);

                        clickOn("#normalButtonInCD");
                        rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("- composition");
                    }

                    @Test
                    void キャンバスに描かれている2つのクラスをクリックしDialogに関係属性を記述しなかった場合はコンポジション関係を描画しない() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                        drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                        clickOn("#compositionButtonInCD");

                        clickOn(firstClickedClassDiagramCanvas);
                        clickOn(secondClickedClassDiagramCanvas);

                        clickOn(okButtonOnDialogBox);

                        clickOn("#normalButtonInCD");
                        rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu()).isNull();
                    }

                    @Test
                    void キャンバスに描かれているFirstClassNameクラスをクリックし描画されていない箇所をクリックした後でFirstClassNameクラスをクリックすると縁の色を変更するがDialogは表示しない() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");

                        clickOn("#compositionButtonInCD");
                        clickOn(firstClickedClassDiagramCanvas);
                        clickOn(secondClickedClassDiagramCanvas);
                        clickOn(firstClickedClassDiagramCanvas);
                        GraphicsContext gc = getGraphicsContext(stage);
                        Paint strokeColor = gc.getStroke();

                        assertThat(strokeColor).isEqualTo(Color.RED);
                        assertThrows(FxRobotException.class, () -> clickOn(okButtonOnDialogBox));
                    }
                }

                @Nested
                class 汎化アイコンを選択している際に {

                    @BeforeEach
                    void reset() {
                        ClassNodeDiagram.resetNodeCount();
                    }

                    @Test
                    void キャンバスに描かれているClassNameクラスをクリックするとClassNameクラスの縁の色を変更する() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                        clickOn("#generalizationButtonInCD");

                        clickOn(firstClickedClassDiagramCanvas);
                        GraphicsContext gc = getGraphicsContext(stage);
                        Paint fillColor = gc.getFill();
                        Paint strokeColor = gc.getStroke();
                        TextAlignment textAlignment = gc.getTextAlign();

                        assertThat(fillColor).isEqualTo(Color.BLACK);
                        assertThat(strokeColor).isEqualTo(Color.RED);
                        assertThat(textAlignment).isEqualTo(TextAlignment.CENTER);
                    }

                    @Test
                    void キャンバスに描かれているClassNameクラスをクリックした後にクラスを描画していない箇所をクリックするとClassNameクラスの縁の色を元に戻す() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                        clickOn("#generalizationButtonInCD");

                        clickOn(firstClickedClassDiagramCanvas);
                        clickOn(secondClickedClassDiagramCanvas);
                        GraphicsContext gc = getGraphicsContext(stage);
                        Paint fillColor = gc.getFill();
                        Paint strokeColor = gc.getStroke();
                        TextAlignment textAlignment = gc.getTextAlign();

                        assertThat(fillColor).isEqualTo(Color.BLACK);
                        assertThat(strokeColor).isEqualTo(Color.BLACK);
                        assertThat(textAlignment).isEqualTo(TextAlignment.CENTER);
                    }

                    @Test
                    void キャンバスに描かれている2つのクラスをクリックしDialogに関係属性を記述すると汎化関係を描画する() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "FirstClassName");
                        drawClasses(secondClickedClassDiagramCanvas, "SecondClassName");
                        clickOn("#generalizationButtonInCD");

                        clickOn(firstClickedClassDiagramCanvas);
                        clickOn(secondClickedClassDiagramCanvas);

                        clickOn("#normalButtonInCD");
                        rightClickOn(betweenFirstAndSecondClickedClassDiagramCanvas);
                        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                        assertThat(scrollPane.getContextMenu().getItems().get(0).getText()).startsWith("汎化");
                    }

                    @Test
                    void キャンバスに描かれているFirstClassNameクラスをクリックし描画されていない箇所をクリックした後でFirstClassNameクラスをクリックすると縁の色を変更する() {
                        clickOn("#classButtonInCD");
                        drawClasses(firstClickedClassDiagramCanvas, "ClassName");

                        clickOn("#generalizationButtonInCD");
                        clickOn(firstClickedClassDiagramCanvas);
                        clickOn(secondClickedClassDiagramCanvas);
                        clickOn(firstClickedClassDiagramCanvas);
                        GraphicsContext gc = getGraphicsContext(stage);
                        Paint strokeColor = gc.getStroke();

                        assertThat(strokeColor).isEqualTo(Color.RED);
                    }
                }
            }
        }
    }

    private static int windowStartCount = 0;

    @Nested
    class コード入力画面において extends ApplicationTest {
        Stage mainStage;
        Stage codeStage;

        @Nested
        class コードエリアの場合 extends ApplicationTest {
            Point2D first;
            Point2D second;
            Point2D third;
            Point2D betweenFirstAndSecond;
            Point2D betweenFirstAndThird;
            Point2D okButtonPoint;

            @Start
            public void start(Stage stage) throws IOException {
                mainStage = stage;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/retussMain.fxml"));
                Parent root = loader.load();
                mainStage.setScene(new Scene(root));

                MainController mainController = loader.getController();
                mainController.showCodeStage(mainController, mainStage, "/retussCode.fxml", "");

                mainStage.show();
                codeStage = mainController.getCodeStage();
            }

            @BeforeEach
            void setup() {
                first = new Point2D(700, 200);
                second = new Point2D(900, 400);
                third = new Point2D(800, 600);
                betweenFirstAndSecond = new Point2D(first.getX() + (second.getX() - first.getX())/2, first.getY() + (second.getY() - first.getY())/2);
                betweenFirstAndThird = new Point2D(first.getX() + (third.getX() - first.getX())/2, first.getY() + (third.getY() - first.getY())/2);
                okButtonPoint = new Point2D(1000.0, 490.0);
                moveCodeWindow();
                deleteCodeWindow();
                deleteCodeWindow();
                clickOn("#classButtonInCD");
                drawClasses(first, "AAA", okButtonPoint);
                resetCodeArea(codeStage);
            }

            @AfterEach
            void reset() {
                ClassNodeDiagram.resetNodeCount();
            }

            @Test
            void 何か入力する() {
                String expected = "class Main {\n}\n";

                clickOn(codeStage);
                write("class Main {\n}\n");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名だけを入力するとクラス名だけのクラスを描画する() {
                String expected = "Main";

                clickOn(codeStage);
                write("class Main {\n}\n");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                String actual = scrollPane.getContextMenu().getItems().get(0).getText();
                assertThat(actual).startsWith(expected);
            }

            @Test
            void 属性を1つ持つクラスを描画する() {
                String expected = "- number : int";

                clickOn(codeStage);
                write("class Main {private int number;}");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                String actual = ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText();
                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 属性を3つ持つクラスを描画する() {
                List<String> expected = Arrays.asList("- number : double", "# text : String", "~ point : float");

                clickOn(codeStage);
                write("class Main {private double number; protected String text; float point;}");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                List<String> actual = new ArrayList<>();
                for (MenuItem item : ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems()) {
                    actual.add(item.getText());
                }

                for (int i = 0; i < expected.size(); i++) {
                    assertThat(actual.get(i)).isEqualTo(expected.get(i));
                }
            }

            @Test
            void 属性を1つ持つクラスを描画した後に属性を変更する() {
                String expected = "- changedNumber : int";

                clickOn(codeStage);
                write("class Main {private int number;}");
                multiPush(2, KeyCode.LEFT);
                multiPush(6, KeyCode.BACK_SPACE);
                write("changedNumber");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                String actual = ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText();
                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 属性を1つ持つクラスを描画した後に属性を削除する() {

                clickOn(codeStage);
                write("class Main {private int number;}");
                push(KeyCode.LEFT);
                multiPush(19, KeyCode.BACK_SPACE);

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                String actual = ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText();
                assertThat(actual).isEqualTo("なし");
            }

            @Test
            void 属性を3つ持つクラスを描画した後に2番目の属性を変更する() {
                List<String> expected = Arrays.asList("- number : double", "+ changedNumber : Integer", "~ point : float");

                clickOn(codeStage);
                write("class Main {private double number; protected String text; float point;}");
                multiPush(14, KeyCode.LEFT);
                multiPush(22, KeyCode.BACK_SPACE);
                write("public Integer changedNumber;");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                List<String> actual = new ArrayList<>();
                for (MenuItem item : ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems()) {
                    actual.add(item.getText());
                }

                for (int i = 0; i < expected.size(); i++) {
                    assertThat(actual.get(i)).isEqualTo(expected.get(i));
                }
            }

            @Test
            void 操作を1つ持つクラスを描画する() {
                String expected = "+ getNumber() : int";

                clickOn(codeStage);
                write("class Main {public int getNumber() {}}");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                String actual = ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText();
                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 操作を3つ持つクラスを描画する() {
                List<String> expected = Arrays.asList("+ getNumber() : int", "# setNumber(number : int) : void", "~ print(text : String, point : float) : void");

                clickOn(codeStage);
                write("class Main {public int getNumber() {} protected void setNumber(int number) {} void print(String text, float point) {}}");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                List<String> actual = new ArrayList<>();
                for (MenuItem item : ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems()) {
                    actual.add(item.getText());
                }

                for (int i = 0; i < expected.size(); i++) {
                    assertThat(actual.get(i)).isEqualTo(expected.get(i));
                }
            }

            @Test
            void 操作を1つ持つクラスを描画した後に操作を変更する() {
                String expected = "+ changedNumber() : int";

                clickOn(codeStage);
                write("class Main {public int getNumber() {}}");
                multiPush(6, KeyCode.LEFT);
                multiPush(9, KeyCode.BACK_SPACE);
                write("changedNumber");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                String actual = ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText();
                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 操作を1つ持つクラスを描画した後に操作を削除する() {

                clickOn(codeStage);
                write("class Main {public int getNumber() {}}");
                multiPush(1, KeyCode.LEFT);
                multiPush(25, KeyCode.BACK_SPACE);

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                String actual = ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText();
                assertThat(actual).isEqualTo("なし");
            }

            @Test
            void 操作を3つ持つクラスを描画した後に2番目の操作を変更する() {
                List<String> expected = Arrays.asList("+ getNumber() : int", "- insertNumber(number : int, index : int) : int", "~ print(text : String, point : float) : void");

                clickOn(codeStage);
                write("class Main {public int getNumber() {} protected void setNumber(int number) {} void print(String text, float point) {}}");
                multiPush(44, KeyCode.LEFT);
                multiPush(36, KeyCode.BACK_SPACE);
                write("private int insertNumber(int number, int index)");

                clickOn("#normalButtonInCD");
                rightClickOn(first);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                List<String> actual = new ArrayList<>();
                for (MenuItem item : ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems()) {
                    actual.add(item.getText());
                }

                for (int i = 0; i < expected.size(); i++) {
                    assertThat(actual.get(i)).isEqualTo(expected.get(i));
                }
            }

            @Test
            void クラスの2番目の名前を変更する() {
                drawSecondClass();

                String expectedMain = "Main";
                String expectedSub = "Sub";

                clickOn(codeStage);
                write("class Sub {}");

                clickOn("#normalButtonInCD");
                String actualSub = getMenuText(second);
                String actualMain = getMenuText(first);
                assertThat(actualMain).startsWith(expectedMain);
                assertThat(actualSub).startsWith(expectedSub);
            }

            @Test
            void クラスの2番目に属性を追加する() {
                drawSecondClass();

                String expectedMain = "なし";
                String expectedSub = "- attribute : String";

                clickOn(codeStage);
                write("class Sub { private String attribute; }");

                clickOn("#normalButtonInCD");
                String actualSub = getAttributeMenuText(second, 0);
                String actualMain = getAttributeMenuText(first, 0);
                assertThat(actualMain).startsWith(expectedMain);
                assertThat(actualSub).startsWith(expectedSub);
            }

            @Test
            void クラスの2番目に操作を追加する() {
                drawSecondClass();

                String expectedMain = "なし";
                String expectedSub = "+ getText() : String";

                clickOn(codeStage);
                write("class Sub { public String getText() {} }");

                clickOn("#normalButtonInCD");
                String actualSub = getOperationMenuText(second, 0);
                String actualMain = getOperationMenuText(first, 0);
                assertThat(actualMain).startsWith(expectedMain);
                assertThat(actualSub).startsWith(expectedSub);
            }

            @Test
            void クラスの2番目から1番目との継承関係を記述する() {
                drawSecondClass();

                String expected = "汎化";

                clickOn(codeStage);
                write("class Sub extends Main {}");

                clickOn("#normalButtonInCD");
                String actual = getMenuText(betweenFirstAndSecond);
                assertThat(actual).startsWith(expected);
            }

            @Test
            void クラスの1番目から2番目との継承関係を記述する() {
                drawSecondClass();
                clickOn(codeStage);
                write("class Sub {}");

                String expected = "汎化";

                clickOnTab(codeStage, 0);
                resetCodeArea(codeStage);
                clickOn(codeStage);
                write("class Main extends Sub {}");

                clickOn("#normalButtonInCD");
                String actual = getMenuText(betweenFirstAndSecond);
                assertThat(actual).startsWith(expected);
            }

            @Test
            void クラスの2番目から1番目とのコンポジション関係を記述する() {
                drawSecondClass();

                String expected = "- main";

                clickOn(codeStage);
                write("class Sub { private Main main; }");

                clickOn("#normalButtonInCD");
                String actual = getMenuText(betweenFirstAndSecond);
                assertThat(actual).startsWith(expected);
            }

            @Test
            void クラスの1番目から2番目とのコンポジション関係を記述する() {
                drawSecondClass();
                clickOn(codeStage);
                write("class Sub {}");

                String expected = "- sub";

                clickOnTab(codeStage, 0);
                resetCodeArea(codeStage);
                clickOn(codeStage);
                write("class Main { private Sub sub; }");

                clickOn("#normalButtonInCD");
                String actual = getMenuText(betweenFirstAndSecond);
                assertThat(actual).startsWith(expected);
            }

            @Test
            void クラスの1番目から2番目とのコンポジション関係と3番目との汎化関係を記述する() {
                drawSecondClass();
                drawThirdClass();
                clickOn(codeStage);
                write("class Super {}");

                String expectedComposition = "- sub";
                String expectedGeneralization = "汎化";

                clickOnTab(codeStage, 0);
                resetCodeArea(codeStage);
                clickOn(codeStage);
                write("class Main extends Super { private Sub sub; }");

                clickOn("#normalButtonInCD");
                String actualComposition = getMenuText(betweenFirstAndSecond);
                String actualGeneralization = getMenuText(betweenFirstAndThird);
                assertThat(actualComposition).startsWith(expectedComposition);
                assertThat(actualGeneralization).startsWith(expectedGeneralization);
            }

            @Test
            void クラスの1番目から2番目と3番目とのコンポジション関係を記述する() {
                drawSecondClass();
                drawThirdClass();
                clickOn(codeStage);
                write("class Super {}");

                String expected1 = "- subClass";
                String expected2 = "# superClass";

                clickOnTab(codeStage, 0);
                resetCodeArea(codeStage);
                clickOn(codeStage);
                write("class Main { private Sub subClass; protected Super superClass; }");

                clickOn("#normalButtonInCD");
                String actual1 = getMenuText(betweenFirstAndSecond);
                String actual2 = getMenuText(betweenFirstAndThird);
                assertThat(actual1).startsWith(expected1);
                assertThat(actual2).startsWith(expected2);
            }

            private void drawSecondClass() {
                write("class Main {}");
                drawClasses(second, "BBB", okButtonPoint);
                clickOnTab(codeStage, 1);
                resetCodeArea(codeStage);
            }

            private void drawThirdClass() {
                write("class Sub {}");
                drawClasses(third, "CCC", okButtonPoint);
                clickOnTab(codeStage, 2);
                resetCodeArea(codeStage);
            }

            private void multiPush(int count, KeyCode key) {
                for (int i = 0; i < count; i++) {
                    push(key);
                }
            }
        }

        @Nested
        class クラス図の場合 extends ApplicationTest {
            Point2D xButtonOnDialogBox;
            Point2D okButtonPoint;
            Point2D topLeftCornerEdge;
            Point2D bottomRightCornerEdge;
            Point2D firstClickedClassDiagramCanvas;
            Point2D secondClickedClassDiagramCanvas;
            Point2D thirdClickedClassDiagramCanvas;
            Point2D betweenFirstAndSecondClickedClassDiagramCanvas;
            Point2D betweenFirstAndThirdClickedClassDiagramCanvas;
            Point2D betweenSecondAndThirdClickedClassDiagramCanvas;
            String changeClassMenu;
            String deleteClassMenu;
            String classAttributeMenu;
            String classOperationMenu;
            String addMenu;
            String changeMenu;
            String deleteMenu;
            String checkMenu;

            @Start
            public void start(Stage stage) throws IOException {
                mainStage = stage;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/retussMain.fxml"));
                Parent root = loader.load();
                mainStage.setScene(new Scene(root));

                MainController mainController = loader.getController();
                mainController.showCodeStage(mainController, mainStage, "/retussCode.fxml", "");

                mainStage.show();
                codeStage = mainController.getCodeStage();
                codeStage.close();
            }

            @BeforeEach
            void setup() {
                topLeftCornerEdge = new Point2D(650, 163);
                bottomRightCornerEdge = new Point2D(1583, 984);
                xButtonOnDialogBox = new Point2D(1050.0, 350.0);
                okButtonPoint = new Point2D(1000.0, 490.0);
                firstClickedClassDiagramCanvas = new Point2D(900.0, 600.0);
                secondClickedClassDiagramCanvas = new Point2D(1050.0, 300.0);
                thirdClickedClassDiagramCanvas = new Point2D(800.0, 450.0);
                betweenFirstAndSecondClickedClassDiagramCanvas = new Point2D(
                        firstClickedClassDiagramCanvas.getX() + (secondClickedClassDiagramCanvas.getX() - firstClickedClassDiagramCanvas.getX()) / 2,
                        firstClickedClassDiagramCanvas.getY() - (firstClickedClassDiagramCanvas.getY() - secondClickedClassDiagramCanvas.getY()) / 2
                );
                betweenFirstAndThirdClickedClassDiagramCanvas = new Point2D(
                        firstClickedClassDiagramCanvas.getX() + (thirdClickedClassDiagramCanvas.getX() - firstClickedClassDiagramCanvas.getX()) / 2,
                        firstClickedClassDiagramCanvas.getY() - (firstClickedClassDiagramCanvas.getY() - thirdClickedClassDiagramCanvas.getY()) / 2
                );
                betweenSecondAndThirdClickedClassDiagramCanvas = new Point2D(
                        thirdClickedClassDiagramCanvas.getX() + (secondClickedClassDiagramCanvas.getX() - thirdClickedClassDiagramCanvas.getX()) / 2,
                        thirdClickedClassDiagramCanvas.getY() - (thirdClickedClassDiagramCanvas.getY() - secondClickedClassDiagramCanvas.getY()) / 2
                );

                changeClassMenu = "クラスの名前の変更";
                deleteClassMenu = "クラスをモデルから削除";
                classAttributeMenu = "属性";
                classOperationMenu = "操作";
                addMenu = "追加";
                changeMenu = "変更";
                deleteMenu = "削除";
                checkMenu = "表示選択";
            }

            @BeforeEach
            void reset() {
                ClassNodeDiagram.resetNodeCount();
            }

            @Test
            void クラス名のみのクラスを記述する() {
                String expected = "class Main {\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名を変更したクラスを記述する() {
                String expected = "class ChangedClassName {\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                rightClickOn(firstClickedClassDiagramCanvas);
                clickOn("Main" + changeClassMenu);
                write("ChangedClassName");
                clickOn(okButtonPoint);
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 描画済みのクラスを除去する() {

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                rightClickOn(firstClickedClassDiagramCanvas);
                clickOn("Main" + deleteClassMenu);

                assertThatThrownBy(() -> getCode(codeStage, 0)).isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void クラス名と属性を1つ持つクラスを記述する() {
                String expected = "class Main {\n    private int number;\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- number : int");
                clickOn(okButtonPoint);
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と属性を3つ持つクラスを記述する() {
                String expected = "class Main {\n    private int number;\n    double point;\n    protected float testNumber;\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- number : int");
                addAttribute(firstClickedClassDiagramCanvas, "~ point : double");
                addAttribute(firstClickedClassDiagramCanvas, "# testNumber : float");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と属性を3つ持つクラスの2番目の属性を変更する() {
                String expected = "class Main {\n    private int number;\n    public char changedAttribute;\n    protected float testNumber;\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- number : int");
                addAttribute(firstClickedClassDiagramCanvas, "~ point : double");
                addAttribute(firstClickedClassDiagramCanvas, "# testNumber : float");
                changeAttribute(firstClickedClassDiagramCanvas, "~ point : double", "+ changedAttribute : char");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と属性を3つ持つクラスの2番目の属性を除去する() {
                String expected = "class Main {\n    private int number;\n    protected float testNumber;\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- number : int");
                addAttribute(firstClickedClassDiagramCanvas, "~ point : double");
                addAttribute(firstClickedClassDiagramCanvas, "# testNumber : float");
                deleteAttribute(firstClickedClassDiagramCanvas, "~ point : double");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と属性を3つ持つクラスの2番目の属性を非表示化する() {
                String expected = "class Main {\n    private int number;\n    double point;\n    protected float testNumber;\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- number : int");
                addAttribute(firstClickedClassDiagramCanvas, "~ point : double");
                addAttribute(firstClickedClassDiagramCanvas, "# testNumber : float");
                disableAttribute(firstClickedClassDiagramCanvas, "~ point : double");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と操作を1つ持つクラスを記述する() {
                String expected = "class Main {\n    public int getNumber() {}\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addOperation(firstClickedClassDiagramCanvas, "+ getNumber() : int");
                clickOn(okButtonPoint);
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と操作を3つ持つクラスを記述する() {
                String expected = "class Main {\n    public int getNumber() {}\n    void setNumber(int num) {}\n    protected void print() {}\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addOperation(firstClickedClassDiagramCanvas, "+ getNumber() : int");
                addOperation(firstClickedClassDiagramCanvas, "~ setNumber(num : int) : void");
                addOperation(firstClickedClassDiagramCanvas, "# print() : void");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と操作を3つ持つクラスの2番目の操作を変更する() {
                String expected = "class Main {\n    public int getNumber() {}\n    void changeNumber(int num, int param) {}\n    protected void print() {}\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addOperation(firstClickedClassDiagramCanvas, "+ getNumber() : int");
                addOperation(firstClickedClassDiagramCanvas, "~ setNumber(num : int) : void");
                addOperation(firstClickedClassDiagramCanvas, "# print() : void");
                changeOperation(firstClickedClassDiagramCanvas, "~ setNumber(num : int) : void", "~ changeNumber(num : int, param : int) : void");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と操作を3つ持つクラスの2番目の操作を除去する() {
                String expected = "class Main {\n    public int getNumber() {}\n    protected void print() {}\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addOperation(firstClickedClassDiagramCanvas, "+ getNumber() : int");
                addOperation(firstClickedClassDiagramCanvas, "~ setNumber(num : int) : void");
                addOperation(firstClickedClassDiagramCanvas, "# print() : void");
                deleteOperation(firstClickedClassDiagramCanvas, "~ setNumber(num : int) : void");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 属性を1つ持つクラスを記述する() {
                String expected = "class Main {\n    private double addAttribute;\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- addAttribute : double");
                clickOn(codeStage);
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 属性を3つ持つクラスを記述する() {
                String expected = "class Main {\n    private double addAttribute;\n    protected int number = 0;\n    float x;\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- addAttribute : double");
                addAttribute(firstClickedClassDiagramCanvas, "# number : int = 0");
                addAttribute(firstClickedClassDiagramCanvas, "~ x : float");
                clickOn(codeStage);
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名と操作を3つ持つクラスの2番目の操作を非表示化する() {
                String expected = "class Main {\n    public int getNumber() {}\n    void setNumber(int num) {}\n    protected void print() {}\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addOperation(firstClickedClassDiagramCanvas, "+ getNumber() : int");
                addOperation(firstClickedClassDiagramCanvas, "~ setNumber(num : int) : void");
                addOperation(firstClickedClassDiagramCanvas, "# print() : void");
                disableOperation(firstClickedClassDiagramCanvas, "~ setNumber(num : int) : void");
                String actual = getCode(codeStage, 0);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名のみのクラスを3つ記述する() {
                List<String> expectedList = Arrays.asList(
                        "class Main {\n}\n",
                        "class Sub {\n}\n",
                        "class Super {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                drawClasses(thirdClickedClassDiagramCanvas, "Super", okButtonPoint);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void クラスを3つ記述して2番目のクラスに属性と操作を2つずつ追加する() {
                List<String> expectedList = Arrays.asList(
                        "class Main {\n}\n",
                        "class Sub {\n    private int number;\n    private char text;\n\n    public void setNumber(int number) {}\n    public int getNumber() {}\n}\n",
                        "class Super {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                drawClasses(thirdClickedClassDiagramCanvas, "Super", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(secondClickedClassDiagramCanvas, "- number : int");
                addAttribute(secondClickedClassDiagramCanvas, "- text : char");
                addOperation(secondClickedClassDiagramCanvas, "+ setNumber(number : int) : void");
                addOperation(secondClickedClassDiagramCanvas, "+ getNumber() : int");

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void クラス名のみのクラスを3つ記述して2番目のクラスを除去する() {
                List<String> expectedList = Arrays.asList(
                        "class Main {\n}\n",
                        "class Super {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                drawClasses(thirdClickedClassDiagramCanvas, "Super", okButtonPoint);
                clickOn("#normalButtonInCD");
                rightClickOn(secondClickedClassDiagramCanvas);
                clickOn("Sub" + deleteClassMenu);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void クラス名のみのクラスを3つ記述して2番目のクラスのクラス名を変更する() {
                List<String> expectedList = Arrays.asList(
                        "class Main {\n}\n",
                        "class Subversion {\n}\n",
                        "class Super {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                drawClasses(thirdClickedClassDiagramCanvas, "Super", okButtonPoint);
                clickOn("#normalButtonInCD");
                rightClickOn(secondClickedClassDiagramCanvas);
                clickOn("Sub" + changeClassMenu);
                write("Subversion");
                clickOn(okButtonPoint);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void クラス名のみのクラス3つの内2つが継承関係のクラス関係を持つクラス記述する() {
                List<String> expectedList = Arrays.asList(
                        "class Main {\n}\n",
                        "class Sub extends Super {\n}\n",
                        "class Super {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                drawClasses(thirdClickedClassDiagramCanvas, "Super", okButtonPoint);
                clickOn("#generalizationButtonInCD");
                clickOn(secondClickedClassDiagramCanvas);
                clickOn(thirdClickedClassDiagramCanvas);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void クラス名のみのクラス3つの内2つがコンポジット関係のクラス関係を持つクラス記述する() {
                List<String> expectedList = Arrays.asList(
                        "class Main {\n    private Sub sub = new Sub();\n}\n",
                        "class Sub {\n}\n",
                        "class Super {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                drawClasses(thirdClickedClassDiagramCanvas, "Super", okButtonPoint);
                clickOn("#compositionButtonInCD");
                clickOn(firstClickedClassDiagramCanvas);
                clickOn(secondClickedClassDiagramCanvas);
                write("- sub");
                clickOn(okButtonPoint);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void クラス名のみのクラス3つの内3つがコンポジット関係のクラス関係を持つクラス記述する() {
                List<String> expectedList = Arrays.asList(
                        "class Main {\n    private Sub subClass = new Sub();\n    private Super superClass = new Super();\n}\n",
                        "class Sub {\n}\n",
                        "class Super {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                drawClasses(thirdClickedClassDiagramCanvas, "Super", okButtonPoint);
                clickOn("#compositionButtonInCD");
                clickOn(firstClickedClassDiagramCanvas);
                clickOn(secondClickedClassDiagramCanvas);
                write("- subClass");
                clickOn(okButtonPoint);
                clickOn(firstClickedClassDiagramCanvas);
                clickOn(thirdClickedClassDiagramCanvas);
                write("- superClass");
                clickOn(okButtonPoint);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void クラス名のみのクラス3つの内3つが互いにコンポジット関係のクラス関係を持つクラス記述する() {
                List<String> expectedList = Arrays.asList(
                        "class Main {\n    private Sub sub = new Sub();\n}\n",
                        "class Sub {\n    public Super super = new Super();\n}\n",
                        "class Super {\n    Main main = new Main();\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                drawClasses(thirdClickedClassDiagramCanvas, "Super", okButtonPoint);
                clickOn("#compositionButtonInCD");
                clickOn(firstClickedClassDiagramCanvas);
                clickOn(secondClickedClassDiagramCanvas);
                write("- sub");
                clickOn(okButtonPoint);
                clickOn(secondClickedClassDiagramCanvas);
                clickOn(thirdClickedClassDiagramCanvas);
                write("+ super");
                clickOn(okButtonPoint);
                clickOn(thirdClickedClassDiagramCanvas);
                clickOn(firstClickedClassDiagramCanvas);
                write("~ main");
                clickOn(okButtonPoint);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void 属性を1つ持つクラスを記述後にその型名を持つクラスを記述するとコンポジション関係を描画する() {
                String expected = "- subClass";
                List<String> expectedList = Arrays.asList(
                        "class Main {\n    private Sub subClass;\n}\n",
                        "class Sub {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- subClass : Sub");
                clickOn("#classButtonInCD");
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                clickOn("#normalButtonInCD");

                String actual = getMenuText(betweenFirstAndSecondClickedClassDiagramCanvas);
                assertThat(actual).startsWith(expected);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void 属性を1つ持つクラスを記述後にその型名を持つクラスを記述してコンポジション関係を描画した後にコンポジション先のクラス名を変更すると関係を消す() {
                String expected = "- subClass";
                List<String> expectedList = Arrays.asList(
                        "class Main {\n    private Sub subClass;\n}\n",
                        "class Subversion {\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- subClass : Sub");
                clickOn("#classButtonInCD");
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                clickOn("#normalButtonInCD");
                change(secondClickedClassDiagramCanvas, "Sub", "Subversion");
                clickOn(okButtonPoint);

                assertThatThrownBy(() -> getMenuText(betweenFirstAndSecondClickedClassDiagramCanvas)).isInstanceOf(NullPointerException.class);

                String actual = getAttributeMenuText(firstClickedClassDiagramCanvas, 0);
                assertThat(actual).startsWith(expected);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            @Test
            void 属性を1つ持つクラスを記述後にその型名を持つクラスを記述してコンポジション関係を描画した後にコンポジション先のクラスを削除すると関係を消す() {
                String expected = "- subClass";
                List<String> expectedList = Arrays.asList(
                        "class Main {\n    private Sub subClass;\n}\n");

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- subClass : Sub");
                clickOn("#classButtonInCD");
                drawClasses(secondClickedClassDiagramCanvas, "Sub", okButtonPoint);
                clickOn("#normalButtonInCD");
                delete(secondClickedClassDiagramCanvas, "Sub");
                clickOn("#normalButtonInCD");

                assertThatThrownBy(() -> getMenuText(betweenFirstAndSecondClickedClassDiagramCanvas)).isInstanceOf(NullPointerException.class);

                String actual = getAttributeMenuText(firstClickedClassDiagramCanvas, 0);
                assertThat(actual).startsWith(expected);

                for (int i = 0; i < expectedList.size(); i++) {
                    assertThat(getCode(codeStage, i)).isEqualTo(expectedList.get(i));
                }
            }

            private void addAttribute(Point2D point, String attributeText) {
                add(classAttributeMenu, addMenu, point, attributeText);
                clickOn(okButtonPoint);
            }

            private void changeAttribute(Point2D point, String beforeAttributeText, String afterAttributeText) {
                change(classAttributeMenu, addMenu, changeMenu, point, beforeAttributeText, afterAttributeText);
                clickOn(okButtonPoint);
            }

            private void deleteAttribute(Point2D point, String attributeText) {
                delete(classAttributeMenu, addMenu, deleteMenu, point, attributeText);
            }

            private void disableAttribute(Point2D point, String attributeText) {
                disable(classAttributeMenu, addMenu, checkMenu, point, attributeText);
            }

            private void addOperation(Point2D point, String attributeText) {
                add(classOperationMenu, addMenu, point, attributeText);
                clickOn(okButtonPoint);
            }

            private void changeOperation(Point2D point, String beforeAttributeText, String afterAttributeText) {
                change(classOperationMenu, addMenu, changeMenu, point, beforeAttributeText, afterAttributeText);
                clickOn(okButtonPoint);
            }

            private void deleteOperation(Point2D point, String attributeText) {
                delete(classOperationMenu, addMenu, deleteMenu, point, attributeText);
            }

            private void disableOperation(Point2D point, String attributeText) {
                disable(classOperationMenu, addMenu, checkMenu, point, attributeText);
            }
        }

        private String getMenuText(Point2D point) {
            rightClickOn(point);
            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
            return scrollPane.getContextMenu().getItems().get(0).getText();
        }

        private String getAttributeMenuText(Point2D point, int index) {
            rightClickOn(point);
            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
            return ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(index).getText();
        }

        private String getOperationMenuText(Point2D point, int index) {
            rightClickOn(point);
            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
            return ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(index).getText();
        }

        private void moveCodeWindow() {
            drag(new Point2D(800.0, 250.0)).dropTo(new Point2D(1700.0, 250.0));
        }

        private void deleteCodeWindow() {
            clickOn(new Point2D(1250.0, 250.0));
        }
    }



    /**
     * <p> クラス図キャンバス直下に存在するスクロールパネルを取得する。 </p>
     *
     * <p>
     * 具体的には、ステージ上のボーダーパネル上のアンカーパネル上のスプリットパネル上の2つ目のアンカーパネル上の
     * タブパネル上のアンカーパネル上のボーダーパネル上のセンターのアンカーパネル上のスクロールパネルを取得する。
     * </p>
     *
     * <p>
     * 右クリックメニューを表示する大元のパネルがこのスクロールパネルであるため、主に右クリックメニューのテストに利用する。
     * </p>
     *
     * @param stage 大元のステージ <br> 基本的にはretussMain.fxmlのステージ以外を呼び出すことはない
     * @return クラス図キャンバス直下のスクロールパネル <br> FXMLファイルを書き換えるか実行中にどこかのパネルを消さない限り{@code null}になる可能性はない
     */
    private ScrollPane getScrollPaneBelowClassDiagramCanvas(Stage stage) {
        BorderPane borderPaneOnStage = (BorderPane) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
        AnchorPane anchorPaneOnBorderPane = (AnchorPane) borderPaneOnStage.getCenter();
        SplitPane splitPaneOnAnchorPaneOnBorderPane = (SplitPane) anchorPaneOnBorderPane.getChildren().get(0);
        AnchorPane anchorPaneOnSplitPane = (AnchorPane) splitPaneOnAnchorPaneOnBorderPane.getItems().get(1);
        TabPane tabPaneOnAnchorPaneOnSplitPane = (TabPane) anchorPaneOnSplitPane.getChildren().get(0);
        AnchorPane anchorPaneOnTabPane = (AnchorPane) tabPaneOnAnchorPaneOnSplitPane.getTabs().get(0).getContent();
        BorderPane borderPaneOnAnchorPaneOnTabPane = (BorderPane) anchorPaneOnTabPane.getChildren().get(0);
        AnchorPane anchorPaneOnVBox = (AnchorPane) borderPaneOnAnchorPaneOnTabPane.getCenter();
        return (ScrollPane) anchorPaneOnVBox.getChildren().get(0);
    }

    /**
     * <p> クラス図キャンバスのグラフィックスコンテキストを取得する。 </p>
     *
     * <p>
     * getScrollPaneBelowClassDiagramCanvasに依存する。
     * クラス図のグラフィックをセットする際の色などを取得するために用いる。
     * </p>
     *
     * @param stage 大元のステージ <br> {@link #getScrollPaneBelowClassDiagramCanvas(Stage)} で呼び出せるステージのみ
     * @return クラス図キャンバスのグラフィックスコンテキスト <br> FXMLファイルを書き換えるか実行中にどこかのパネルを消さない限り{@code null}になる可能性はない
     */
    private GraphicsContext getGraphicsContext(Stage stage) {
        ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
        AnchorPane anchorPane = (AnchorPane) scrollPane.getContent();
        Canvas canvas = (Canvas) anchorPane.getChildren().get(0);
        return canvas.getGraphicsContext2D();
    }

    /**
     * <p> コード入力ウィンドウに存在するJavaタブ内の文字列を取得します </p>
     *
     * <p>
     * 具体的には、 {@link #getCodeArea(Stage, int)} で取得したコードエリア内の文字列を取得します。
     * 上記構造のタブ内以降についてはController実行中に生成しているため、変更される恐れがあります。
     * </p>
     *
     * <p>
     * コード入力とキャンバス上との整合性テストに利用すします。
     * </p>
     *
     * @param stage 大元のステージ <br> 基本的にはretussCode.fxmlのステージ以外を呼び出すことはありません
     * @return コード入力ウィンドウに存在するJavaタブ内の文字列 <br> FXMLファイルを書き換えるか実行中にどこかのタブを消さない限り{@code null}になる可能性はありません
     */
    private String getCode(Stage stage, int tabNumber) {
        return getCodeArea(stage, tabNumber).getText();
    }

    /**
     * <p> コード入力ウィンドウに存在するJavaタブ内の文字列を消去します </p>
     *
     * <p>
     * 具体的には、ステージ上のボーダーパネル上のタブパネル上の1つ目のタブ内のアンカーパネル上の
     * タブパネル上の1つ目のタブ内のアンカーパネル上のコードエリア内の文字列を取得します。
     * 上記構造のタブ内以降についてはController実行中に生成しているため、変更される恐れがします。
     * </p>
     *
     * <p>
     * コード入力とキャンバス上との整合性テストの前に利用します。
     * </p>
     *
     * @param stage 大元のステージ <br> 基本的にはretussCode.fxmlのステージ以外を呼び出すことはありません
     */
    private void resetCodeArea(Stage stage) {
        // getCodeArea(stage).replaceText("");
        clickOn(stage);
        drag(stage).dropTo(new Point2D(1300.0, 250.0));
        push(KeyCode.BACK_SPACE);
        push(KeyCode.RIGHT);
        push(KeyCode.BACK_SPACE);
    }

    /**
     * <p> コード入力ウィンドウに存在するJavaタブ内のコードエリアを取得します </p>
     *
     * <p>
     * 具体的には、ステージ上のボーダーパネル上のタブパネル上の1つ目のタブ内のアンカーパネル上の
     * タブパネル上の1つ目のタブ内のアンカーパネル上のコードエリアを取得します。
     * 上記構造のタブ内以降についてはController実行中に生成しているため、変更される恐れがあります。
     * </p>
     *
     * @param stage 大元のステージ <br> 基本的にはretussCode.fxmlのステージ以外を呼び出すことはありません
     * @return コード入力ウィンドウに存在するJavaタブ内のコードエリア <br> FXMLファイルを書き換えるか実行前にどこかのタブを消さない限り{@code null}になる可能性はありません
     */
    private CodeArea getCodeArea(Stage stage, int tabNumber) {
        BorderPane borderPaneOnStage = (BorderPane) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
        TabPane tabPaneOnBorderPane = (TabPane) borderPaneOnStage.getCenter();
        Tab tabOnLanguageTabPane = tabPaneOnBorderPane.getTabs().get(0);
        AnchorPane anchorPaneOnLanguageTab = (AnchorPane) tabOnLanguageTabPane.getContent();
        TabPane tabPaneOnAnchorPane = (TabPane) anchorPaneOnLanguageTab.getChildren().get(0);
        Tab tabOnCodeTabPane = tabPaneOnAnchorPane.getTabs().get(tabNumber);
        AnchorPane anchorPaneOnCodeTab = (AnchorPane) tabOnCodeTabPane.getContent();
        return (CodeArea) anchorPaneOnCodeTab.getChildren().get(0);
    }

    private void clickOnTab(Stage stage, int tabNumber) {
        BorderPane borderPaneOnStage = (BorderPane) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
        TabPane tabPaneOnBorderPane = (TabPane) borderPaneOnStage.getCenter();
        Tab tabOnLanguageTabPane = tabPaneOnBorderPane.getTabs().get(0);
        AnchorPane anchorPaneOnLanguageTab = (AnchorPane) tabOnLanguageTabPane.getContent();
        TabPane tabPaneOnAnchorPane = (TabPane) anchorPaneOnLanguageTab.getChildren().get(0);
        clickOn(tabPaneOnAnchorPane.getTabs().get(tabNumber).getText());
    }

    private void drawClasses(Point2D canvasPoint, String className) {
        clickOn(canvasPoint);
        write(className);
        clickOn(new Point2D(1000.0, 490.0));
    }

    private void drawClasses(Point2D canvasPoint, String className, Point2D okButtonPoint) {
        clickOn(canvasPoint);
        write(className);
        clickOn(okButtonPoint);
    }

    private void change(Point2D point, String beforeAttributeText, String afterAttributeText) {
        rightClickOn(point);
        clickOn(beforeAttributeText + "クラスの名前の変更");
        write(afterAttributeText);
    }

    private void delete(Point2D point, String text) {
        rightClickOn(point);
        clickOn(text + "クラスをモデルから削除");
    }

    private void add(String featureMenu, String addMenu, Point2D point, String attributeText) {
        rightClickOn(point);
        moveTo(featureMenu);
        clickOn(addMenu);
        write(attributeText);
    }

    private void change(String featureMenu, String addMenu, String changeMenu, Point2D point, String beforeAttributeText, String afterAttributeText) {
        rightClickOn(point);
        moveTo(featureMenu);
        moveTo(addMenu);
        moveTo(changeMenu);
        clickOn(beforeAttributeText);
        write(afterAttributeText);
    }

    private void delete(String featureMenu, String addMenu, String deleteMenu, Point2D point, String attributeText) {
        rightClickOn(point);
        moveTo(featureMenu);
        moveTo(addMenu);
        moveTo(deleteMenu);
        clickOn(attributeText);
    }

    private void disable(String featureMenu, String addMenu, String checkMenu, Point2D point, String attributeText) {
        rightClickOn(point);
        moveTo(featureMenu);
        moveTo(addMenu);
        moveTo(checkMenu);
        clickOn(attributeText);
    }

    private void addAttribute(Point2D classPoint, String addText) {
        addFeature(classPoint, "属性", addText);
    }

    private void addOperation(Point2D classPoint, String addText) {
        addFeature(classPoint, "操作", addText);
    }

    private void addFeature(Point2D classPoint, String menu, String addText) {
        rightClickOn(classPoint);
        moveTo(menu);
        clickOn("追加");
        write(addText);
        clickOn(okButtonOnDialogBox);
    }

    private void changeAttribute(Point2D classPoint, String beforeText, String afterText) {
        changeFeature(classPoint, "属性", beforeText, afterText);
    }

    private void changeOperation(Point2D classPoint, String beforeText, String afterText) {
        changeFeature(classPoint, "操作", beforeText, afterText);
    }

    private void changeFeature(Point2D classPoint, String menu, String beforeText, String afterText) {
        rightClickOn(classPoint);
        moveTo(menu);
        moveTo("追加");
        moveTo("変更");
        clickOn(beforeText);

        if (afterText.length() > 0) write(afterText);
        else push(KeyCode.BACK_SPACE);

        clickOn(okButtonOnDialogBox);
    }

    private void deleteAttribute(Point2D classPoint, String deleteText) {
        deleteFeature(classPoint, "属性", deleteText);
    }

    private void deleteOperation(Point2D classPoint, String deleteText) {
        deleteFeature(classPoint, "操作", deleteText);
    }

    private void deleteFeature(Point2D classPoint, String menu, String deleteMenu) {
        rightClickOn(classPoint);
        moveTo(menu);
        moveTo("追加");
        moveTo("削除");
        clickOn(deleteMenu);
    }

    private void checkAttribute(Point2D classPoint, String checkText) {
        checkFeature(classPoint, "属性", checkText);
    }

    private void checkOperation(Point2D classPoint, String checkText) {
        checkFeature(classPoint, "操作", checkText);
    }

    private void checkFeature(Point2D classPoint, String menu, String checkMenu) {
        rightClickOn(classPoint);
        moveTo(menu);
        moveTo("追加");
        moveTo("表示選択");
        clickOn(checkMenu);
    }
}