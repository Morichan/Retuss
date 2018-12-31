package io.github.morichan.retuss.window;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.Operation;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.feature.type.Type;
import io.github.morichan.fescue.feature.visibility.Visibility;
import io.github.morichan.retuss.language.uml.Class;
import io.github.morichan.retuss.language.uml.Package;
import io.github.morichan.retuss.window.diagram.*;
import io.github.morichan.retuss.window.utility.UtilityJavaFXComponent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import mockit.*;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ClassDiagramDrawerTest {

    @AfterAll
    static void resetClassCount() {
        ClassNodeDiagram.resetNodeCount();
    }

    @Nested
    class クラスアイコンを選択している場合 extends ApplicationTest {
        ClassDiagramDrawer cdd;

        List<Button> buttons = new ArrayList<>();
        Button normalButton;
        Button classButton;
        Button noteButton;

        UtilityJavaFXComponent util;

        @BeforeEach
        void setUp() {
            cdd = new ClassDiagramDrawer();
            normalButton = new Button("Normal");
            classButton = new Button("Class");
            noteButton = new Button("Note");
            buttons = Arrays.asList(normalButton, classButton, noteButton);

            util = new UtilityJavaFXComponent();

            buttons = util.bindAllButtonsFalseWithout(buttons, classButton);
        }

        @Nested
        class クラスに関して {

            @BeforeEach
            void resetCount() {
                ClassNodeDiagram.resetNodeCount();
            }

            @Test
            void キャンバスをクリックすると描画する() {
                cdd.setNodeText("ClassName");

                cdd.addDrawnNode(buttons);

                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
            }

            @Test
            void キャンバスを2回クリックすると2回描画する() {
                cdd.setNodeText("ClassName");

                cdd.addDrawnNode(buttons);
                cdd.addDrawnNode(buttons);

                assertThat(cdd.getNodes().size()).isEqualTo(2);
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(1).getNodeId()).isOne();
            }

            @Test
            void 削除する() {
                cdd.setNodeText("ClassName");
                cdd.addDrawnNode(buttons);

                cdd.deleteDrawnNode(0);

                assertThat(cdd.getNodes().size()).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
            }

            @Test
            void 描画済み2つの内1つ目を削除する() {
                // Arrange
                createClasses(cdd, buttons, 0, "FirstClassName", new Point2D(100.0, 200.0));
                createClasses(cdd, buttons, 1, "SecondClassName", new Point2D(500.0, 600.0));

                // Act
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.deleteDrawnNode(cdd.getCurrentNodeNumber());

                // Assert
                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("SecondClassName");
            }

            @Test
            void 描画済み2つの内2つ目を削除する() {
                // Arrange
                createClasses(cdd, buttons, 0, "FirstClassName", new Point2D(100.0, 200.0));
                createClasses(cdd, buttons, 1, "SecondClassName", new Point2D(500.0, 600.0));

                // Act
                int id = cdd.getNodeDiagramId(500.0, 600.0);
                cdd.deleteDrawnNode(cdd.getCurrentNodeNumber());

                // Assert
                assertThat(id).isOne();
                assertThat(cdd.getCurrentNodeNumber()).isOne();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("FirstClassName");
            }

            @Test
            void 描画済み2つの内2つ目を削除した後3つ目を描画する() {
                // Arrange
                createClasses(cdd, buttons, 0, "FirstClassName", new Point2D(100.0, 200.0));
                createClasses(cdd, buttons, 1, "SecondClassName", new Point2D(500.0, 600.0));

                // Act
                int id = cdd.getNodeDiagramId(500.0, 600.0);
                cdd.deleteDrawnNode(cdd.getCurrentNodeNumber());

                createClasses(cdd, buttons, 1, "ThirdClassName", new Point2D(300.0, 400.0));

                // Assert
                assertThat(id).isOne();
                assertThat(cdd.getCurrentNodeNumber()).isOne();
                assertThat(cdd.getNodes().size()).isEqualTo(2);
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("FirstClassName");
                assertThat(cdd.getNodes().get(1).getNodeId()).isEqualTo(2);
                assertThat(cdd.getNodes().get(1).getNodeText()).isEqualTo("ThirdClassName");
            }

            @Test
            void 描画済み3つの内2つ目を削除する() {
                // Arrange
                createClasses(cdd, buttons, 0, "FirstClassName", new Point2D(100.0, 200.0));
                createClasses(cdd, buttons, 1, "SecondClassName", new Point2D(300.0, 400.0));
                createClasses(cdd, buttons, 2, "ThirdClassName", new Point2D(500.0, 600.0));

                // Act
                int id = cdd.getNodeDiagramId(300.0, 400.0);
                cdd.deleteDrawnNode(cdd.getCurrentNodeNumber());

                // Assert
                assertThat(id).isOne();
                assertThat(cdd.getNodes().size()).isEqualTo(2);
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("FirstClassName");
                assertThat(cdd.getNodes().get(1).getNodeId()).isEqualTo(2);
                assertThat(cdd.getNodes().get(1).getNodeText()).isEqualTo("ThirdClassName");
            }

            @Test
            void 描画済み3つの内2つ目を削除した後4つ目を描画する() {
                createClasses(cdd, buttons, 0, "FirstClassName", new Point2D(100.0, 200.0));
                createClasses(cdd, buttons, 1, "SecondClassName", new Point2D(300.0, 400.0));
                createClasses(cdd, buttons, 2, "ThirdClassName", new Point2D(500.0, 600.0));
                int id = cdd.getNodeDiagramId(300.0, 400.0);
                cdd.deleteDrawnNode(cdd.getCurrentNodeNumber());

                createClasses(cdd, buttons, 2, "FourthClassName", new Point2D(700.0, 800.0));

                assertThat(id).isOne();
                assertThat(cdd.getNodes().size()).isEqualTo(3);
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(2).getNodeId()).isEqualTo(3);
                assertThat(cdd.getNodes().get(2).getNodeText()).isEqualTo("FourthClassName");
            }

            @Test
            void 追加する際に空文字を入力した場合は追加しない(@Mocked ClassNodeDiagram mock) {
                GraphicsContext mocked = mock(GraphicsContext.class);
                Canvas canvas = new Canvas();
                canvas.setWidth(1000.0);
                canvas.setHeight(1000.0);
                when(mocked.getCanvas()).thenReturn(canvas);
                cdd.setGraphicsContext(mocked);
                cdd.setNodeText("");
                cdd.setMouseCoordinates(100.0, 200.0);
                cdd.addDrawnNode(buttons);

                assertThat(cdd.getNodes().size()).isZero();
                cdd.allReDrawNode();

                new Verifications() {{
                    mock.draw();
                    times = 0;
                }};
            }

            @Test
            void クラス名を変更する() {
                cdd.setNodeText("ClassName");
                cdd.addDrawnNode(buttons);

                cdd.changeDrawnNodeText(0, ContentType.Title, 0, "ChangedClassName");

                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ChangedClassName");
            }

            @Test
            void クラス名を変更する際に空文字を入力した場合は変更しない() {
                createClasses(cdd, buttons, 0, "NotChangedClassName", new Point2D(100.0, 200.0));

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.changeDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Title, 0, "");

                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("NotChangedClassName");
            }

            @Test
            void 描画済み2つの内1つ目のクラス名を変更する() {
                // Arrange
                createClasses(cdd, buttons, 0, "ClassName", new Point2D(100.0, 200.0));
                createClasses(cdd, buttons, 1, "NotChangingClassName", new Point2D(500.0, 600.0));

                // Act
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.changeDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Title, 0, "ChangedClassName");

                // Assert
                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isEqualTo(2);
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ChangedClassName");
                assertThat(cdd.getNodes().get(1).getNodeId()).isOne();
                assertThat(cdd.getNodes().get(1).getNodeText()).isEqualTo("NotChangingClassName");
            }

            @Test
            void 幅と高さを返す() {
                double expectedWidth = 100.0;
                double expectedHeight = 80.0;
                createClasses(cdd, buttons, 0, "ClassName", new Point2D(100.0, 200.0));

                double actualWidth = cdd.getNodeWidth(0);
                double actualHeight = cdd.getNodeHeight(0);

                assertThat(actualWidth).isEqualTo(expectedWidth);
                assertThat(actualHeight).isEqualTo(expectedHeight);
            }

            @Test
            void 位置を返す() {
                Point2D expected = new Point2D(100.0, 200.0);
                createClasses(cdd, buttons, 0, "ClassName", new Point2D(100.0, 200.0));

                Point2D actual = cdd.getMouseCoordinates();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void X軸とY軸が共にキャンバスより小さい位置を入力した場合は枠をはみ出さない位置を返す() {
                Point2D expected = new Point2D(10.0 + (100.0 / 2), 10.0 + (80.0 / 2));
                createClasses(cdd, buttons, 0, "ClassName", new Point2D(0.0, 0.0));

                Point2D actual = cdd.getOperationalPoint();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 描画済み3つの内2つ目の位置を変更する() {
                // Arrange
                createClasses(cdd, buttons, 0, "FirstClassName", new Point2D(100.0, 200.0));
                createClasses(cdd, buttons, 1, "SecondClassName", new Point2D(300.0, 400.0));
                createClasses(cdd, buttons, 2, "ThirdClassName", new Point2D(500.0, 600.0));

                // Act
                int beforeId = cdd.getNodeDiagramId(300.0, 400.0);
                cdd.moveTo(1, new Point2D(100.0, 600.0));
                int afterId = cdd.getNodeDiagramId(100.0, 600.0);

                // Assert
                assertThat(beforeId).isOne();
                assertThat(afterId).isOne();
                assertThat(cdd.getNodes().size()).isEqualTo(3);
            }
        }

        @Nested
        class クラスの属性に関して {

            @BeforeEach
            void resetCount() {
                ClassNodeDiagram.resetNodeCount();
            }

            @BeforeEach
            void setUp() {
                createClasses(cdd, buttons, 0, "ClassName", new Point2D(100.0, 200.0));
            }

            @Test
            void 追加する() {
                // Arrange
                int id = -1;
                List<String> attributes = Arrays.asList(
                        "- content1 : int",
                        "- content2 : double",
                        "- content3 : char");

                // Act
                for (String attribute : attributes) {
                    id = cdd.getNodeDiagramId(100.0, 200.0);
                    cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, attribute);
                }

                // Assert
                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getDrawnNodeTextList(0, ContentType.Attribute).size()).isEqualTo(attributes.size());
                for (int i = 0; i < attributes.size(); i++) {
                    assertThat(cdd.getDrawnNodeTextList(0, ContentType.Attribute).get(i)).isEqualTo(attributes.get(i));
                }
            }

            @Test
            void 追加する際に空文字を入力した場合は追加しない() {
                String attribute = "";

                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, attribute);

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getDrawnNodeTextList(0, ContentType.Attribute).size()).isZero();
            }

            @Test
            void リストを取得する() {

                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, "- content : int");

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getNodes().get(0).getNodeContentText(ContentType.Attribute, 0)).isEqualTo("- content : int");
            }

            @Test
            void 変更する() {

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, "- content : int");
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.changeDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, 0, "- content : double");

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getNodes().get(0).getNodeContentText(ContentType.Attribute, 0)).isEqualTo("- content : double");
            }

            @Test
            void 変更する際に空文字を入力した場合は変更しない() {

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, "- content : int");
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.changeDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, 0, "");

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getNodes().get(0).getNodeContentText(ContentType.Attribute, 0)).isEqualTo("- content : int");
            }

            @Test
            void 削除する() {

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, "- content : int");
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.deleteDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, 0);

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getDrawnNodeTextList(0, ContentType.Attribute).size()).isZero();
            }

            @Test
            void 非表示にする() {

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, "- content1 : int");
                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, "- content2 : double");
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.setDrawnNodeContentBoolean(cdd.getCurrentNodeNumber(), ContentType.Attribute, ContentType.Indication, 0, false);

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getDrawnNodeContentsBooleanList(0, ContentType.Attribute, ContentType.Indication).size()).isEqualTo(2);
                assertThat(cdd.getDrawnNodeContentsBooleanList(0, ContentType.Attribute, ContentType.Indication).get(0)).isFalse();
                assertThat(cdd.getDrawnNodeContentsBooleanList(0, ContentType.Attribute, ContentType.Indication).get(1)).isTrue();
            }
        }

        @Nested
        class クラスの操作に関して {

            @BeforeEach
            void resetCount() {
                ClassNodeDiagram.resetNodeCount();
            }

            @BeforeEach
            void setUp() {
                createClasses(cdd, buttons, 0, "ClassName", new Point2D(100.0, 200.0));
            }

            @Test
            void 追加する() {
                // Arrange
                int id = -1;
                List<String> operations = Arrays.asList("+ content1() : int", "+ content2() : double", "+ content3() : char");

                // Act
                for (String operation : operations) {
                    id = cdd.getNodeDiagramId(100.0, 200.0);
                    cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, operation);
                }

                // Assert
                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getDrawnNodeTextList(0, ContentType.Operation).size()).isEqualTo(operations.size());
                for (int i = 0; i < operations.size(); i++) {
                    assertThat(cdd.getDrawnNodeTextList(0, ContentType.Operation).get(i)).isEqualTo(operations.get(i));
                }
            }

            @Test
            void 追加する際に空文字を入力した場合は追加しない() {
                String attribute = "";

                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, attribute);

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getDrawnNodeTextList(0, ContentType.Operation).size()).isZero();
            }

            @Test
            void 変更する() {

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, "+ content() : int");
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.changeDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, 0, "+ content() : double");

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getNodes().get(0).getNodeContentText(ContentType.Operation, 0)).isEqualTo("+ content() : double");
            }

            @Test
            void 変更する際に空文字を入力した場合は変更しない() {

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, "+ content() : int");
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.changeDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, 0, "");

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getNodes().get(0).getNodeContentText(ContentType.Operation, 0)).isEqualTo("+ content() : int");
            }

            @Test
            void 削除する() {

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, "- content() : int");
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.deleteDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, 0);

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getDrawnNodeTextList(0, ContentType.Operation).size()).isZero();
            }

            @Test
            void 非表示にする() {

                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, "- content1() : int");
                cdd.getNodeDiagramId(100.0, 200.0);
                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, "- content2() : double");
                int id = cdd.getNodeDiagramId(100.0, 200.0);
                cdd.setDrawnNodeContentBoolean(cdd.getCurrentNodeNumber(), ContentType.Operation, ContentType.Indication, 0, false);

                assertThat(id).isZero();
                assertThat(cdd.getCurrentNodeNumber()).isZero();
                assertThat(cdd.getNodes().size()).isOne();
                assertThat(cdd.getNodes().get(0).getNodeId()).isZero();
                assertThat(cdd.getNodes().get(0).getNodeText()).isEqualTo("ClassName");
                assertThat(cdd.getDrawnNodeContentsBooleanList(0, ContentType.Operation, ContentType.Indication).size()).isEqualTo(2);
                assertThat(cdd.getDrawnNodeContentsBooleanList(0, ContentType.Operation, ContentType.Indication).get(0)).isFalse();
                assertThat(cdd.getDrawnNodeContentsBooleanList(0, ContentType.Operation, ContentType.Indication).get(1)).isTrue();
            }
        }
    }

    @Nested
    class ノートアイコンを選択している場合 extends ApplicationTest {
        @Tested
        ClassDiagramDrawer cdd;

        List<Button> buttons = new ArrayList<>();
        Button normalButton;
        Button classButton;
        Button noteButton;

        UtilityJavaFXComponent util;

        @BeforeEach
        void setUp() {
            cdd = new ClassDiagramDrawer();
            normalButton = new Button("Normal");
            classButton = new Button("Class");
            noteButton = new Button("Note");
            buttons = Arrays.asList(normalButton, classButton, noteButton);

            util = new UtilityJavaFXComponent();

            buttons = util.bindAllButtonsFalseWithout(buttons, noteButton);
        }

        @Test
        void キャンバスをクリックするとノートを描画する(@Mocked NoteNodeDiagram mock) {
            // Arrange
            cdd.setNodeText("Note");
            cdd.addDrawnNode(buttons);

            // Act
            cdd.allReDrawNode();

            // Assert
            assertThat(cdd.getNodes().size()).isOne();

            new Verifications() {{
                mock.draw();
                times = 1;
            }};
        }
    }

    @Nested
    class ノーマルアイコンを選択している場合 extends ApplicationTest {
        @Tested
        ClassDiagramDrawer cdd;

        List<Button> buttons = new ArrayList<>();
        Button normalButton;
        Button classButton;
        Button noteButton;

        UtilityJavaFXComponent util;

        @BeforeEach
        void setUp() {
            cdd = new ClassDiagramDrawer();
            normalButton = new Button("Normal");
            classButton = new Button("Class");
            noteButton = new Button("Note");
            buttons = Arrays.asList(normalButton, classButton, noteButton);

            util = new UtilityJavaFXComponent();

            buttons = util.bindAllButtonsFalseWithout(buttons, normalButton);
        }

        @Test
        void キャンバスをクリックしても何も描画しない(@Mocked ClassNodeDiagram classNodeDiagram, @Mocked NoteNodeDiagram noteNodeDiagram) {
            // Arrange
            cdd.setNodeText("Item");

            // Act
            cdd.addDrawnNode(buttons);

            // Arrange
            assertThat(cdd.getNodes().size()).isZero();

            new Verifications() {{
                classNodeDiagram.draw();
                times = 0;
                noteNodeDiagram.draw();
                times = 0;
            }};
        }
    }

    @Nested
    class コンポジションアイコンを選択している場合 extends ApplicationTest {
        @Tested
        ClassDiagramDrawer cdd;

        List<Button> buttons = new ArrayList<>();
        Button normalButton;
        Button classButton;
        Button noteButton;
        Button compositionButton;

        UtilityJavaFXComponent util;

        @BeforeEach
        void setUp() {
            cdd = new ClassDiagramDrawer();
            normalButton = new Button("Normal");
            classButton = new Button("Class");
            noteButton = new Button("Note");
            compositionButton = new Button("Composition");
            buttons = Arrays.asList(normalButton, classButton, noteButton, compositionButton);

            util = new UtilityJavaFXComponent();

            buttons = util.bindAllButtonsFalseWithout(buttons, compositionButton);
        }

        @Test
        void キャンバスをクリックしても何も描画しない(@Mocked ClassNodeDiagram classNodeDiagram, @Mocked NoteNodeDiagram noteNodeDiagram) {
            cdd.setNodeText("Item");
            cdd.addDrawnNode(buttons);

            assertThat(cdd.getNodes().size()).isZero();

            new Verifications() {{
                classNodeDiagram.draw();
                times = 0;
                noteNodeDiagram.draw();
                times = 0;
            }};
        }
    }

    @Nested
    class クラスを3つ記述後にコンポジションアイコンを選択している場合 extends ApplicationTest {
        @Tested
        ClassDiagramDrawer cdd;

        List<Button> buttons = new ArrayList<>();
        Button normalButton;
        Button classButton;
        Button noteButton;
        Button compositionButton;
        Point2D firstClass;
        Point2D secondClass;
        Point2D thirdClass;
        Point2D betweenFirstAndSecondClass;
        Point2D betweenFirstAndThirdClass;

        UtilityJavaFXComponent util;

        @BeforeEach
        void setUp() {
            cdd = new ClassDiagramDrawer();
            normalButton = new Button("Normal");
            classButton = new Button("Class");
            noteButton = new Button("Note");
            compositionButton = new Button("Composition");
            buttons = Arrays.asList(normalButton, classButton, noteButton, compositionButton);
            firstClass = new Point2D(100.0, 200.0);
            secondClass = new Point2D(500.0, 600.0);
            thirdClass = new Point2D(100.0, 600.0);
            betweenFirstAndSecondClass = new Point2D(300.0, 400.0);
            betweenFirstAndThirdClass = new Point2D(100.0, 400.0);

            util = new UtilityJavaFXComponent();
            buttons = util.bindAllButtonsFalseWithout(buttons, classButton);

            ClassNodeDiagram.resetNodeCount();

            cdd.setNodeText("FirstClassName");
            createClasses(cdd, buttons, 0, "FirstClassName", firstClass);
            createClasses(cdd, buttons, 1, "SecondClassName", secondClass);
            createClasses(cdd, buttons, 2, "ThirdClassName", thirdClass);
            buttons = util.bindAllButtonsFalseWithout(buttons, compositionButton);
        }

        @Test
        void 描画済みクラスの1つ目をクリックするとコンポジションの描画を待機し正規ノードを待機していなかったとする() {

            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());

            assertThat(actual).isFalse();
            assertThat(cdd.getNowStateType()).isEqualByComparingTo(ContentType.Composition);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void 描画済みクラスの1つ目をクリックした後に2つ目をクリックするとコンポジションの描画待機を解除し正規ノードを待機していたとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());

            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void 描画済みクラスの1つ目をクリックした後にクラスを描画していない箇所をクリックするとコンポジションの描画待機を解除し正規ノードを待機していなかったとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX() + 100.0, secondClass.getY() + 100.0);

            assertThat(actual).isFalse();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void 描画済みクラスの1つ目をクリックした後に1つ目をクリックするとコンポジションの描画待機を解除し正規ノードを待機していたとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());

            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void 描画済みクラスの1つ目をコンポジションとしてクリックした後に2つ目を汎化としてクリックすると汎化の描画を待機し正規ノードを待機していたとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, secondClass.getX(), secondClass.getY());

            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Generalization);
            assertThat(cdd.getCurrentNodeNumber()).isOne();
        }

        @Test
        void 描画済みクラスの1つ目をコンポジションとしてクリックして2つ目を汎化としてクリックした後に3回目と4回目をコンポジションとしてクリックするとコンポジションの描画待機を解除し正規ノードを待機していたとする() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, secondClass.getX(), secondClass.getY());

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());

            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void 描画済みクラスの1つ目をコンポジションとしてクリックした後に2回目と3回目を汎化としてクリックすると汎化の描画を待機し正規ノードを待機していたとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Generalization);
            assertThat(cdd.getCurrentNodeNumber()).isZero();

            actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, secondClass.getX(), secondClass.getY());
            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void 描画済みクラスの2つクリックするとコンポジション関係を描画する() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition", secondClass.getX(), secondClass.getY());

            assertThat(cdd.getCurrentNodeNumber()).isOne();
            assertThat(cdd.getEdgeDiagram().getEdgeContentText(0)).isEqualTo("- composition");
            assertThat(cdd.getEdgeDiagram().getRelationId(ContentType.Composition, 0)).isOne();
            assertThat(cdd.getEdgeDiagram().getRelationSourceId(ContentType.Composition, 0)).isZero();
        }

        @Test
        void 描画済みクラスの2つのクラス間をクリックすると真を返すがそれ以外の箇所では偽を返す() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition", secondClass.getX(), secondClass.getY());

            boolean actualTrue = cdd.isAlreadyDrawnAnyDiagram(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY());
            boolean actualFalse = cdd.isAlreadyDrawnAnyDiagram(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY() + 100.0);

            assertThat(actualTrue).isTrue();
            assertThat(actualFalse).isFalse();
        }

        @Test
        void キャンバスに描画している一番上の関係の内容を返す() {
            RelationshipAttributeGraphic expected = new RelationshipAttributeGraphic("- composition");
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition", secondClass.getX(), secondClass.getY());
            cdd.searchDrawnAnyDiagramType(secondClass.getX(), secondClass.getY());

            RelationshipAttributeGraphic actual = cdd.searchDrawnEdge(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY());

            assertThat(actual.getText()).isEqualTo(expected.getText());
        }

        @Test
        void キャンバスに描画している一番上の関係の内容を変更する() {
            RelationshipAttributeGraphic expected = new RelationshipAttributeGraphic("- changedComposition");
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition", secondClass.getX(), secondClass.getY());
            cdd.searchDrawnAnyDiagramType(secondClass.getX(), secondClass.getY());

            cdd.changeDrawnEdge(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY(), expected.getText());
            RelationshipAttributeGraphic actual = cdd.searchDrawnEdge(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY());

            assertThat(actual.getText()).isEqualTo(expected.getText());
        }

        @Test
        void キャンバスに描画している一番上の関係の内容を削除する() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition", secondClass.getX(), secondClass.getY());
            cdd.searchDrawnAnyDiagramType(secondClass.getX(), secondClass.getY());

            cdd.deleteDrawnEdge(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY());
            RelationshipAttributeGraphic actual = cdd.searchDrawnEdge(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY());

            assertThat(actual).isNull();
        }

        @Test
        void キャンバスに描画しているクラス2つのコンポジション関係を複数描画する() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition1", secondClass.getX(), secondClass.getY());

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition2", secondClass.getX(), secondClass.getY());

            assertThat(cdd.getEdgeDiagram().getEdgeContentText(0)).isEqualTo("- composition1");
            assertThat(cdd.getEdgeDiagram().getRelationId(ContentType.Composition, 0)).isOne();
            assertThat(cdd.getEdgeDiagram().getRelationSourceId(ContentType.Composition, 0)).isZero();
            assertThat(cdd.getEdgeDiagram().getEdgeContentText(1)).isEqualTo("- composition2");
            assertThat(cdd.getEdgeDiagram().getRelationId(ContentType.Composition, 1)).isOne();
            assertThat(cdd.getEdgeDiagram().getRelationSourceId(ContentType.Composition, 1)).isZero();
        }

        @Test
        void キャンバスに描画しているクラス2つのコンポジション関係を描画する途中で一度描画していない箇所を選択してから描画する() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY());
            cdd.setMouseCoordinates(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY());

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition", secondClass.getX(), secondClass.getY());

            assertThat(cdd.getEdgeDiagram().getEdgeContentText(0)).isEqualTo("- composition");
            assertThat(cdd.getEdgeDiagram().getRelationId(ContentType.Composition, 0)).isOne();
            assertThat(cdd.getEdgeDiagram().getRelationSourceId(ContentType.Composition, 0)).isZero();
        }

        @Test
        void キャンバスに描画しているクラス3つのコンポジション関係を2つ描画する() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition1", secondClass.getX(), secondClass.getY());

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, thirdClass.getX(), thirdClass.getY());
            cdd.addDrawnEdge(buttons, "- composition2", thirdClass.getX(), thirdClass.getY());

            assertThat(cdd.getEdgeDiagram().getEdgeContentText(0)).isEqualTo("- composition1");
            assertThat(cdd.getEdgeDiagram().getRelationId(ContentType.Composition, 0)).isOne();
            assertThat(cdd.getEdgeDiagram().getRelationSourceId(ContentType.Composition, 0)).isZero();
            assertThat(cdd.getEdgeDiagram().getEdgeContentText(1)).isEqualTo("- composition2");
            assertThat(cdd.getEdgeDiagram().getRelationId(ContentType.Composition, 1)).isEqualTo(2);
            assertThat(cdd.getEdgeDiagram().getRelationSourceId(ContentType.Composition, 1)).isZero();
            assertThat(cdd.searchDrawnEdge(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY()).getText()).isEqualTo("- composition1");
            assertThat(cdd.searchDrawnEdge(betweenFirstAndThirdClass.getX(), betweenFirstAndThirdClass.getY()).getText()).isEqualTo("- composition2");
        }

        @Test
        void 描画済み2つのクラスの内2つ目の位置を移動すると2つ目と3つ目間の関係も同時に移動する() {
            // Arrange
            Point2D expected = new Point2D(100.0, 600.0);
            createClasses(cdd, buttons, 0, "Test1", firstClass);
            createClasses(cdd, buttons, 1, "Test2", secondClass);

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass);
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "- composition", secondClass.getX(), secondClass.getY());

            // Act
            cdd.moveTo(1, expected);
            cdd.allReDrawCanvas();

            // Assert
            assertThat(cdd.getEdgeDiagram().getRelationPoint(ContentType.Composition, 0)).isEqualTo(expected);
        }
    }

    @Nested
    class クラスを3つ記述後に汎化アイコンを選択している場合 extends ApplicationTest {
        @Tested
        ClassDiagramDrawer cdd;

        List<Button> buttons = new ArrayList<>();
        Button normalButton;
        Button classButton;
        Button noteButton;
        Button generalizationButton;
        Point2D firstClass;
        Point2D secondClass;
        Point2D thirdClass;
        Point2D betweenFirstAndSecondClass;
        Point2D betweenFirstAndThirdClass;

        UtilityJavaFXComponent util;

        @BeforeEach
        void setUp() {
            cdd = new ClassDiagramDrawer();
            normalButton = new Button("Normal");
            classButton = new Button("Class");
            noteButton = new Button("Note");
            generalizationButton = new Button("Generalization");
            buttons = Arrays.asList(normalButton, classButton, noteButton, generalizationButton);
            firstClass = new Point2D(100.0, 200.0);
            secondClass = new Point2D(500.0, 600.0);
            thirdClass = new Point2D(100.0, 600.0);
            betweenFirstAndSecondClass = new Point2D(300.0, 400.0);
            betweenFirstAndThirdClass = new Point2D(100.0, 400.0);

            util = new UtilityJavaFXComponent();
            buttons = util.bindAllButtonsFalseWithout(buttons, classButton);

            ClassNodeDiagram.resetNodeCount();

            createClasses(cdd, buttons, 0, "FirstClassName", firstClass);
            createClasses(cdd, buttons, 1, "SecondClassName", secondClass);
            createClasses(cdd, buttons, 2, "ThirdClassName", thirdClass);
            buttons = util.bindAllButtonsFalseWithout(buttons, generalizationButton);
        }

        @Test
        void キャンバスに描画しているクラスの1つ目をクリックすると汎化の描画を待機し正規ノードを待機していなかったとする() {

            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());

            assertThat(actual).isFalse();
            assertThat(cdd.getNowStateType()).isEqualByComparingTo(ContentType.Generalization);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void キャンバスに描画しているクラスの1つ目をクリックした後に2つ目をクリックすると汎化の描画待機を解除し正規ノードを待機していたとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, secondClass.getX(), secondClass.getY());

            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void キャンバスに描画しているクラスの1つ目をクリックした後にクラスを描画していない箇所をクリックすると汎化の描画待機を解除し正規ノードを待機していなかったとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, secondClass.getX() + 100.0, secondClass.getY() + 100.0);

            assertThat(actual).isFalse();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void キャンバスに描画しているクラスの1つ目をクリックした後に1つ目をクリックすると汎化の描画待機を解除し正規ノードを待機していたとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());

            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void キャンバスに描画しているクラスの1つ目を汎化としてクリックした後に2つ目を汎化としてクリックするとコンポジションの描画を待機し正規ノードを待機していたとする() {

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());

            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Composition);
            assertThat(cdd.getCurrentNodeNumber()).isOne();
        }

        @Test
        void キャンバスに描画しているクラスの1つ目を汎化としてクリックして2つ目をコンポジションとしてクリックした後に3回目と4回目を汎化としてクリックすると汎化の描画待機を解除し正規ノードを待機していたとする() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Composition, secondClass.getX(), secondClass.getY());

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            boolean actual = cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());

            assertThat(actual).isTrue();
            assertThat(cdd.getNowStateType()).isEqualTo(ContentType.Undefined);
            assertThat(cdd.getCurrentNodeNumber()).isZero();
        }

        @Test
        void キャンバスに描画しているクラスを2つクリックすると汎化関係を描画する() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());

            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "", secondClass.getX(), secondClass.getY());

            assertThat(cdd.getCurrentNodeNumber()).isOne();
            assertThat(cdd.getEdgeDiagram().getEdgeContentText(0)).isEmpty();
            assertThat(cdd.getEdgeDiagram().getRelationId(ContentType.Composition, 0)).isOne();
            assertThat(cdd.getEdgeDiagram().getRelationSourceId(ContentType.Composition, 0)).isZero();
        }

        @Test
        void キャンバスに関係を描画している2つのクラス間をクリックすると真を返すがそれ以外の箇所では偽を返す() {
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
            cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
            cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, secondClass.getX(), secondClass.getY());
            cdd.addDrawnEdge(buttons, "attribute", secondClass.getX(), secondClass.getY());

            boolean actualTrue = cdd.isAlreadyDrawnAnyDiagram(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY());
            boolean actualFalse = cdd.isAlreadyDrawnAnyDiagram(betweenFirstAndSecondClass.getX(), betweenFirstAndSecondClass.getY() + 100.0);

            assertThat(actualTrue).isTrue();
            assertThat(actualFalse).isFalse();
        }
    }

    @Nested
    class パッケージインスタンスを抽出する際に extends ApplicationTest {
        @Tested
        ClassDiagramDrawer cdd;

        List<Button> buttons = new ArrayList<>();
        Button normalButton;
        Button classButton;
        Button noteButton;
        Button compositionButton;
        Button generalizationButton;
        Point2D firstClass;
        Point2D secondClass;
        Point2D thirdClass;
        Point2D betweenFirstAndSecondClass;
        Point2D betweenFirstAndThirdClass;

        UtilityJavaFXComponent util;

        @Nested
        class クラスを1つ記述している場合 {

            @BeforeEach
            void setUp() {
                cdd = new ClassDiagramDrawer();
                GraphicsContext mocked = mock(GraphicsContext.class);
                Canvas canvas = new Canvas();
                canvas.setWidth(1000.0);
                canvas.setHeight(1000.0);
                when(mocked.getCanvas()).thenReturn(canvas);
                cdd.setGraphicsContext(mocked);

                normalButton = new Button("Normal");
                classButton = new Button("Class");
                noteButton = new Button("Note");
                buttons = Arrays.asList(normalButton, classButton, noteButton);
                firstClass = new Point2D(100.0, 200.0);

                util = new UtilityJavaFXComponent();
                buttons = util.bindAllButtonsFalseWithout(buttons, classButton);

                ClassNodeDiagram.resetNodeCount();

                cdd.setNodeText("FirstClassName");
                createClasses(cdd, buttons, 0, "FirstClassName", firstClass);
            }

            @Test
            void クラス名のみのインスタンスを抽出する() {
                Class expected = new Class("FirstClassName");

                cdd.allReDrawCanvas();
                Package actual = cdd.getPackage();

                assertThat(actual.getClasses().get(0)).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 属性を1つ持つインスタンスを抽出する() {
                Class expected = new Class("FirstClassName");
                expected.addAttribute(new Attribute(new Name("attributeFromFirst")));

                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Attribute, "attributeFromFirst");
                cdd.allReDrawCanvas();
                Package actual = cdd.getPackage();

                assertThat(actual.getClasses().get(0)).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 操作を1つ持つインスタンスを抽出する() {
                Class expected = new Class("FirstClassName");
                Operation operation = new Operation(new Name("operationFromFirst"));
                operation.setVisibility(Visibility.Public);
                operation.setReturnType(new Type("float"));
                expected.addOperation(new OperationGraphic(operation));

                cdd.addDrawnNodeText(cdd.getCurrentNodeNumber(), ContentType.Operation, "+ operationFromFirst() : float");
                cdd.allReDrawCanvas();
                Package actual = cdd.getPackage();

                assertThat(actual.getClasses().get(0).getOperationGraphics().get(0).getOperation())
                        .isEqualToComparingFieldByFieldRecursively(expected.getOperationGraphics().get(0).getOperation());
            }
        }

        @Nested
        class クラスを3つ記述している場合 {

            @BeforeEach
            void setUp() {
                cdd = new ClassDiagramDrawer();
                GraphicsContext mocked = mock(GraphicsContext.class);
                Canvas canvas = new Canvas();
                canvas.setWidth(1000.0);
                canvas.setHeight(1000.0);
                when(mocked.getCanvas()).thenReturn(canvas);
                cdd.setGraphicsContext(mocked);

                normalButton = new Button("Normal");
                classButton = new Button("Class");
                noteButton = new Button("Note");
                compositionButton = new Button("Composition");
                generalizationButton = new Button("Generalization");
                buttons = Arrays.asList(normalButton, classButton, noteButton, compositionButton, generalizationButton);
                firstClass = new Point2D(100.0, 200.0);
                secondClass = new Point2D(500.0, 600.0);
                thirdClass = new Point2D(100.0, 600.0);
                betweenFirstAndSecondClass = new Point2D(300.0, 400.0);
                betweenFirstAndThirdClass = new Point2D(100.0, 400.0);

                util = new UtilityJavaFXComponent();
                buttons = util.bindAllButtonsFalseWithout(buttons, classButton);

                ClassNodeDiagram.resetNodeCount();

                cdd.setNodeText("FirstClassName");
                createClasses(cdd, buttons, 0, "FirstClassName", firstClass);
                createClasses(cdd, buttons, 1, "SecondClassName", secondClass);
                createClasses(cdd, buttons, 2, "ThirdClassName", thirdClass);
            }

            @Test
            void クラス名のみのインスタンスを抽出する() {
                Package expected = new Package();
                expected.addClass(new Class("FirstClassName"));
                expected.addClass(new Class("SecondClassName"));
                expected.addClass(new Class("ThirdClassName"));

                cdd.allReDrawCanvas();
                Package actual = cdd.getPackage();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 継承クラスを持つインスタンスを抽出する() {
                Package expected = new Package();
                Class extendingClass = new Class("FirstClassName");
                Class extendedClass = new Class("SecondClassName");
                extendingClass.setGeneralizationClass(extendedClass);
                expected.addClass(extendingClass);
                expected.addClass(extendedClass);
                expected.addClass(new Class("ThirdClassName"));

                buttons = util.bindAllButtonsFalseWithout(buttons, generalizationButton);
                cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, firstClass.getX(), firstClass.getY());
                cdd.setMouseCoordinates(firstClass.getX(), firstClass.getY());
                cdd.hasWaitedCorrectDrawnDiagram(ContentType.Generalization, secondClass.getX(), secondClass.getY());
                cdd.addDrawnEdge(buttons, "", secondClass.getX(), secondClass.getY());

                cdd.allReDrawCanvas();
                Package actual = cdd.getPackage();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }
    }

    private void createClasses(ClassDiagramDrawer cdd, List<Button> buttons, int classCount, String className, Point2D classPosition) {
        GraphicsContext mocked;
        mocked = mock(GraphicsContext.class);
        Canvas canvas = new Canvas();
        canvas.setWidth(1000.0);
        canvas.setHeight(1000.0);
        when(mocked.getCanvas()).thenReturn(canvas);
        cdd.setGraphicsContext(mocked);
        cdd.setNodeText(className);
        cdd.setMouseCoordinates(classPosition);
        cdd.addDrawnNode(buttons);
        ((ClassNodeDiagram) cdd.getNodes().get(classCount)).calculateWidthAndHeight(100.0, 80.0);
    }
}