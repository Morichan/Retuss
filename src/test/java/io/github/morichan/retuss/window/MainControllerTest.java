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
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- attribute : int");
                            clickOn(okButtonOnDialogBox);

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
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- attribute : int");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            moveTo(addMenu);
                            moveTo(changeMenu);
                            clickOn("- attribute : int");
                            write("- attribute : double");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("- attribute : double");
                        }

                        @Test
                        void 追加済みの属性の変更メニューを選択した際に何も入力しない場合は属性を変更しない() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- attribute : int");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            moveTo(addMenu);
                            moveTo(changeMenu);
                            clickOn("- attribute : int");
                            push(KeyCode.BACK_SPACE);
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("- attribute : int");
                        }

                        @Test
                        void 追加済みの属性の削除メニューを選択した場合は属性を削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- attribute : int");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            moveTo(addMenu);
                            moveTo(deleteMenu);
                            clickOn("- attribute : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("なし");
                        }

                        @Test
                        void 追加済みの属性の表示選択チェックメニューのチェックを外した場合は属性を非表示にする() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- attribute : int");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("- attribute : int");

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
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- attribute : int");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("- attribute : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(3)).getItems().get(0)).isSelected()).isFalse();
                            moveTo(classAttributeMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("- attribute : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(3)).getItems().get(3)).getItems().get(0)).isSelected()).isTrue();
                        }

                        @Test
                        void 操作の追加メニューを選択した場合は操作を追加する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ operation() : void");
                            clickOn(okButtonOnDialogBox);

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
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ operation() : int");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            moveTo(addMenu);
                            moveTo(changeMenu);
                            clickOn("+ operation() : int");
                            write("+ operation() : double");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("+ operation() : double");
                        }

                        @Test
                        void 追加済みの操作の削除メニューを選択した場合は操作を削除する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ operation() : int");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            moveTo(addMenu);
                            moveTo(deleteMenu);
                            clickOn("+ operation() : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("なし");
                        }

                        @Test
                        void 追加済みの操作の表示選択チェックメニューのチェックを外した場合は操作を非表示にする() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ operation() : int");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("+ operation() : int");

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
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ operation() : int");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            moveTo(addMenu);
                            moveTo(changeMenu);
                            clickOn("+ operation() : int");
                            push(KeyCode.BACK_SPACE);
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(1)).getItems().get(0).getText()).isEqualTo("+ operation() : int");
                        }

                        @Test
                        void 追加済みの非表示の操作の表示選択チェックメニューのチェックを付けた場合は操作を表示する() {
                            clickOn("#classButtonInCD");
                            drawClasses(firstClickedClassDiagramCanvas, "ClassName");
                            clickOn("#normalButtonInCD");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ operation() : int");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("+ operation() : int");

                            rightClickOn(firstClickedClassDiagramCanvas);
                            ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(stage);
                            assertThat(((CheckMenuItem) ((Menu) ((Menu) scrollPane.getContextMenu().getItems().get(4)).getItems().get(3)).getItems().get(0)).isSelected()).isFalse();
                            moveTo(classOperationMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("+ operation() : int");

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
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- x : int");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- y : int");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ z() : c");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ w() : c");
                            clickOn(okButtonOnDialogBox);

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
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- x : int");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- y : int");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ z() : c");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ w() : c");
                            clickOn(okButtonOnDialogBox);

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
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- longAttribute1 : int");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            clickOn(addMenu);
                            write("- longAttribute2 : double");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ longOperation1() : void");
                            clickOn(okButtonOnDialogBox);
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            clickOn(addMenu);
                            write("+ longOperation2() : double");
                            clickOn(okButtonOnDialogBox);

                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("- longAttribute1 : int");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classAttributeMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("- longAttribute2 : double");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("+ longOperation1() : void");
                            rightClickOn(firstClickedClassDiagramCanvas);
                            moveTo(classOperationMenu);
                            moveTo(addMenu);
                            moveTo(checkMenu);
                            clickOn("+ longOperation2() : double");

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

    @Nested
    class コード入力画面において extends ApplicationTest {
        Stage mainStage;
        Stage codeStage;

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

        @Nested
        class コードエリアの場合 {
            Point2D topLeftCorner;
            Point2D okButtonPoint;

            @BeforeEach
            void setup() {
                topLeftCorner = new Point2D(700, 200);
                okButtonPoint = new Point2D(1000.0, 490.0);
                moveCodeWindow();
                clickOn("#classButtonInCD");
                drawClasses(topLeftCorner, "SampleClass", okButtonPoint);
                resetCodeArea(codeStage);
            }

            @Test
            void 何か入力する() {
                String expected = "class Main {\n}\n";

                clickOn(codeStage);
                write("class Main {\n}\n");
                String actual = getCode(codeStage);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void クラス名だけを入力するとクラス名だけのクラスを描画する() {
                String expected = "Main";

                clickOn(codeStage);
                write("class Main {\n}\n");

                clickOn("#normalButtonInCD");
                rightClickOn(topLeftCorner);
                ScrollPane scrollPane = getScrollPaneBelowClassDiagramCanvas(mainStage);
                String actual = scrollPane.getContextMenu().getItems().get(0).getText();
                assertThat(actual).startsWith(expected);
            }
        }

        @Nested
        class クラス図の場合 {
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
            void moveAndDeleteCodeWindow() {
                moveCodeWindow();
            }

            @Test
            void クラス名のみのクラスを記述する() {
                String expected = "class Main {\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                //clickOn(codeStage);
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 描画済みのクラスを除去する() {

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                rightClickOn(firstClickedClassDiagramCanvas);
                clickOn("Main" + deleteClassMenu);

                assertThatThrownBy(() -> getCode(codeStage)).isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void クラス名と属性を1つ持つクラスを記述する() {
                String expected = "class Main {\n    private int number;\n}\n";

                clickOn("#classButtonInCD");
                drawClasses(firstClickedClassDiagramCanvas, "Main", okButtonPoint);
                clickOn("#normalButtonInCD");
                addAttribute(firstClickedClassDiagramCanvas, "- number : int");
                clickOn(okButtonPoint);
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

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
                String actual = getCode(codeStage);

                assertThat(actual).isEqualTo(expected);
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

        private void moveCodeWindow() {
            drag(new Point2D(800.0, 250.0)).dropTo(new Point2D(1700.0, 250.0));
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
     * 具体的には、 {@link #getCodeArea(Stage)} で取得したコードエリア内の文字列を取得します。
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
    private String getCode(Stage stage) {
        return getCodeArea(stage).getText();
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
    private CodeArea getCodeArea(Stage stage) {
        BorderPane borderPaneOnStage = (BorderPane) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
        TabPane tabPaneOnBorderPane = (TabPane) borderPaneOnStage.getCenter();
        Tab tabOnLanguageTabPane = tabPaneOnBorderPane.getTabs().get(0);
        AnchorPane anchorPaneOnLanguageTab = (AnchorPane) tabOnLanguageTabPane.getContent();
        TabPane tabPaneOnAnchorPane = (TabPane) anchorPaneOnLanguageTab.getChildren().get(0);
        Tab tabOnCodeTabPane = tabPaneOnAnchorPane.getTabs().get(0);
        AnchorPane anchorPaneOnCodeTab = (AnchorPane) tabOnCodeTabPane.getContent();
        return (CodeArea) anchorPaneOnCodeTab.getChildren().get(0);
    }

    private void drawClasses(Point2D canvasPoint, String className) {
        clickOn(canvasPoint);
        write(className);
        clickOn(okButtonOnDialogBox);
    }

    private void drawClasses(Point2D canvasPoint, String className, Point2D okButtonPoint) {
        clickOn(canvasPoint);
        write(className);
        clickOn(okButtonPoint);
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
}