package io.github.morichan.retuss.window.utility;

import io.github.morichan.retuss.window.diagram.ContentType;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UtilityJavaFXComponentTest extends ApplicationTest {
    UtilityJavaFXComponent util;

    @BeforeEach
    void setUp() {
        util = new UtilityJavaFXComponent();
    }

    @Nested
    class ボタンに関して {

        @BeforeEach
        void setUp() {
            util = new UtilityJavaFXComponent();
        }

        @Test
        void リストに1つ存在するボタンをtrueにする() {
            Button normalButton = new Button();
            List<Button> buttons = new ArrayList<>();
            buttons.add(normalButton);

            buttons = util.bindAllButtonsFalseWithout(buttons, normalButton);

            assertThat(buttons.get(0).isDefaultButton()).isTrue();
        }

        @Test
        void リストに2つ存在するボタンの内1つ目をtrueにする() {
            Button normalButton = new Button();
            Button classButton = new Button();
            List<Button> buttons;
            buttons = Arrays.asList(normalButton, classButton);

            buttons = util.bindAllButtonsFalseWithout(buttons, normalButton);

            assertThat(buttons.get(0).isDefaultButton()).isTrue();
            assertThat(buttons.get(1).isDefaultButton()).isFalse();
        }

        @Test
        void リストに3つ存在するボタンの内2つ目をtrueにする() {
            Button normalButton = new Button();
            Button classButton = new Button();
            Button noteButton = new Button();
            List<Button> buttons;
            buttons = Arrays.asList(normalButton, classButton, noteButton);

            buttons = util.bindAllButtonsFalseWithout(buttons, classButton);

            assertThat(buttons.get(0).isDefaultButton()).isFalse();
            assertThat(buttons.get(1).isDefaultButton()).isTrue();
            assertThat(buttons.get(2).isDefaultButton()).isFalse();
        }

        @Test
        void リストに3つ存在するボタンの内3つ目をtrueにする() {
            Button normalButton = new Button();
            Button classButton = new Button();
            Button noteButton = new Button();
            List<Button> buttons;
            buttons = Arrays.asList(normalButton, classButton, noteButton);

            buttons = util.bindAllButtonsFalseWithout(buttons, noteButton);

            assertThat(buttons.get(0).isDefaultButton()).isFalse();
            assertThat(buttons.get(1).isDefaultButton()).isFalse();
            assertThat(buttons.get(2).isDefaultButton()).isTrue();
        }

        @Test
        void リストに2つ存在するボタンの内1つ目をtrueにした後2つ目をtrueにする() {
            Button normalButton = new Button();
            Button classButton = new Button();
            List<Button> buttons;
            buttons = Arrays.asList(normalButton, classButton);

            buttons = util.bindAllButtonsFalseWithout(buttons, normalButton);
            buttons = util.bindAllButtonsFalseWithout(buttons, classButton);

            assertThat(buttons.get(0).isDefaultButton()).isFalse();
            assertThat(buttons.get(1).isDefaultButton()).isTrue();
        }

        @Test
        void リストに2つ存在するボタンの内trueになっている1つ目のボタンを返す() {
            Button normalButton = new Button();
            Button classButton = new Button();
            List<Button> buttons;
            buttons = Arrays.asList(normalButton, classButton);

            buttons = util.bindAllButtonsFalseWithout(buttons, normalButton);
            Button clickedButton = util.searchSelectedButtonIn(buttons);

            assertThat(clickedButton).isEqualTo(normalButton);
        }

        @Test
        void リストに2つ存在するボタンの内trueになっている2つ目のボタンを返す() {
            Button normalButton = new Button();
            Button classButton = new Button();
            List<Button> buttons;
            buttons = Arrays.asList(normalButton, classButton);

            buttons = util.bindAllButtonsFalseWithout(buttons, classButton);
            Button clickedButton = util.searchSelectedButtonIn(buttons);

            assertThat(clickedButton).isEqualTo(classButton);
        }
    }

    @Nested
    class 右クリックに関して {

        @BeforeEach
        void setUp() {
            util = new UtilityJavaFXComponent();
        }

        @Test
        void クラスの右クリックメニューを整形する() {
            String className = "ClassName";

            ContextMenu expected = new ContextMenu();
            Menu attributeMenu = new Menu("属性");
            Menu operationMenu = new Menu("操作");

            MenuItem addAttributeMenuItem = new MenuItem("追加");
            Menu changeAttributeMenu = new Menu("変更");
            Menu deleteAttributeMenu = new Menu("削除");
            Menu displayAttributeMenu = new Menu("表示選択");
            MenuItem addOperationMenuItem = new MenuItem("追加");
            Menu changeOperationMenu = new Menu("変更");
            Menu deleteOperationMenu = new Menu("削除");
            Menu displayOperationMenu = new Menu("表示選択");

            List<String> attributes = Arrays.asList("- content1 : int", "- content2 : double", "- content3 : char");
            List<String> operations = Arrays.asList("- content1() : int", "- content2( argv : int ) : double", "- content3( argv1 : int, argv2 : double ) : char");
            List<Boolean> attributesVisibility = Arrays.asList(true, true, true);
            List<Boolean> operationsVisibility = Arrays.asList(true, true, true);

            for (String attribute : attributes) {
                changeAttributeMenu.getItems().add(new MenuItem(attribute));
                deleteAttributeMenu.getItems().add(new MenuItem(attribute));
                displayAttributeMenu.getItems().add(new CheckMenuItem(attribute));
            }
            for (String operation : operations) {
                changeOperationMenu.getItems().add(new MenuItem(operation));
                deleteOperationMenu.getItems().add(new MenuItem(operation));
                displayOperationMenu.getItems().add(new CheckMenuItem(operation));
            }

            attributeMenu.getItems().add(addAttributeMenuItem);
            attributeMenu.getItems().add(changeAttributeMenu);
            attributeMenu.getItems().add(deleteAttributeMenu);
            attributeMenu.getItems().add(displayAttributeMenu);
            operationMenu.getItems().add(addOperationMenuItem);
            operationMenu.getItems().add(changeOperationMenu);
            operationMenu.getItems().add(deleteOperationMenu);
            operationMenu.getItems().add(displayOperationMenu);

            expected.getItems().add(new MenuItem(className + "クラスの名前の変更"));
            expected.getItems().add(new MenuItem(className + "クラスをモデルから削除"));
            expected.getItems().add(new SeparatorMenuItem());
            expected.getItems().add(attributeMenu);
            expected.getItems().add(operationMenu);

            ContextMenu actual = util.createClassContextMenuInCD(className, ContentType.Class, attributes, operations, attributesVisibility, operationsVisibility);

            assertThat(actual.getItems().size()).isEqualTo(expected.getItems().size());
            assertThat(actual.getItems().size()).isEqualTo(5);
            for (int i = 0; i < actual.getItems().size(); i++) {
                assertThat(actual.getItems().get(i).getText()).isEqualTo(expected.getItems().get(i).getText());
            }

            assertThat(actual.getItems().get(0)).isInstanceOf(expected.getItems().get(0).getClass());
            assertThat(actual.getItems().get(1)).isInstanceOf(expected.getItems().get(1).getClass());
            assertThat(actual.getItems().get(2)).isInstanceOf(expected.getItems().get(2).getClass());
            assertThat(actual.getItems().get(3)).isInstanceOf(expected.getItems().get(3).getClass());
            assertThat(actual.getItems().get(4)).isInstanceOf(expected.getItems().get(4).getClass());

            assertThat(((Menu) actual.getItems().get(3)).getItems().size()).isEqualTo(((Menu) expected.getItems().get(3)).getItems().size());
            for (int i = 0; i < ((Menu) actual.getItems().get(3)).getItems().size(); i++) {
                assertThat(((Menu) actual.getItems().get(3)).getItems().get(i).getText()).isEqualTo(((Menu) expected.getItems().get(3)).getItems().get(i).getText());
                assertThat(((Menu) actual.getItems().get(3)).getItems().get(i)).isInstanceOf(((Menu) expected.getItems().get(3)).getItems().get(i).getClass());

                if (i == 0) continue;
                assertThat(((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().size()).isEqualTo(((Menu) ((Menu) expected.getItems().get(3)).getItems().get(i)).getItems().size());
                for (int j = 0; j < ((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().size(); j++) {
                    assertThat(((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().get(j).getText()).isEqualTo(((Menu) ((Menu) expected.getItems().get(3)).getItems().get(i)).getItems().get(j).getText());
                    assertThat(((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().get(j)).isInstanceOf(((Menu) ((Menu) expected.getItems().get(3)).getItems().get(i)).getItems().get(j).getClass());
                }

                if (i != 3) continue;
                for (int j = 0; j < ((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().size(); j++) {
                    assertThat(((CheckMenuItem) ((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().get(j)).isSelected()).isTrue();
                }
            }

            assertThat(((Menu) actual.getItems().get(4)).getItems().size()).isEqualTo(((Menu) expected.getItems().get(4)).getItems().size());
            for (int i = 0; i < ((Menu) actual.getItems().get(4)).getItems().size(); i++) {
                assertThat(((Menu) actual.getItems().get(4)).getItems().get(i).getText()).isEqualTo(((Menu) expected.getItems().get(4)).getItems().get(i).getText());
                assertThat(((Menu) actual.getItems().get(4)).getItems().get(i)).isInstanceOf(((Menu) expected.getItems().get(4)).getItems().get(i).getClass());

                if (i == 0) continue;
                assertThat(((Menu) ((Menu) actual.getItems().get(4)).getItems().get(i)).getItems().size()).isEqualTo(((Menu) ((Menu) expected.getItems().get(3)).getItems().get(i)).getItems().size());
                for (int j = 0; j < ((Menu) ((Menu) actual.getItems().get(4)).getItems().get(i)).getItems().size(); j++) {
                    assertThat(((Menu) ((Menu) actual.getItems().get(4)).getItems().get(i)).getItems().get(j).getText()).isEqualTo(((Menu) ((Menu) expected.getItems().get(4)).getItems().get(i)).getItems().get(j).getText());
                    assertThat(((Menu) ((Menu) actual.getItems().get(4)).getItems().get(i)).getItems().get(j)).isInstanceOf(((Menu) ((Menu) expected.getItems().get(4)).getItems().get(i)).getItems().get(j).getClass());
                }

                if (i != 3) continue;
                for (int j = 0; j < ((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().size(); j++) {
                    assertThat(((CheckMenuItem) ((Menu) ((Menu) actual.getItems().get(4)).getItems().get(i)).getItems().get(j)).isSelected()).isTrue();
                }
            }
        }

        @Test
        void クラスの右クリックメニューにおいて属性や操作が空の場合はなしと表示し操作もできない() {
            String className = "ClassName";

            ContextMenu expected = new ContextMenu();
            Menu attributeMenu = new Menu("属性");
            Menu operationMenu = new Menu("操作");

            MenuItem addAttributeMenuItem = new MenuItem("追加");
            Menu changeAttributeMenu = new Menu("変更");
            Menu deleteAttributeMenu = new Menu("削除");
            Menu displayAttributeMenu = new Menu("表示選択");
            MenuItem addOperationMenuItem = new MenuItem("追加");
            Menu changeOperationMenu = new Menu("変更");
            Menu deleteOperationMenu = new Menu("削除");
            Menu displayOperationMenu = new Menu("表示選択");

            String expectedMenuText = "なし";
            List<String> attributes = new ArrayList<>();
            List<String> operations = new ArrayList<>();
            List<Boolean> attributesVisibility = new ArrayList<>();
            List<Boolean> operationsVisibility = new ArrayList<>();

            attributeMenu.getItems().add(addAttributeMenuItem);
            attributeMenu.getItems().add(changeAttributeMenu);
            attributeMenu.getItems().add(deleteAttributeMenu);
            attributeMenu.getItems().add(displayAttributeMenu);
            operationMenu.getItems().add(addOperationMenuItem);
            operationMenu.getItems().add(changeOperationMenu);
            operationMenu.getItems().add(deleteOperationMenu);
            operationMenu.getItems().add(displayOperationMenu);

            expected.getItems().add(new MenuItem(className + "クラスをモデルから削除"));
            expected.getItems().add(new MenuItem(className + "クラスの名前の変更"));
            expected.getItems().add(new SeparatorMenuItem());
            expected.getItems().add(attributeMenu);
            expected.getItems().add(operationMenu);

            ContextMenu actual = util.createClassContextMenuInCD(className, ContentType.Class, attributes, operations, attributesVisibility, operationsVisibility);

            assertThat(actual.getItems().size()).isEqualTo(expected.getItems().size());
            assertThat(actual.getItems().size()).isEqualTo(5);

            assertThat(((Menu) actual.getItems().get(3)).getItems().size()).isEqualTo(((Menu) expected.getItems().get(3)).getItems().size());
            for (int i = 1; i < ((Menu) actual.getItems().get(3)).getItems().size(); i++) {
                assertThat(((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().size()).isEqualTo(1);
                assertThat(((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().get(0).getText()).isEqualTo(expectedMenuText);
                assertThat(((Menu) ((Menu) actual.getItems().get(3)).getItems().get(i)).getItems().get(0).isDisable()).isTrue();
            }
            assertThat(((Menu) actual.getItems().get(4)).getItems().size()).isEqualTo(((Menu) expected.getItems().get(4)).getItems().size());
            for (int i = 1; i < ((Menu) actual.getItems().get(4)).getItems().size(); i++) {
                assertThat(((Menu) ((Menu) actual.getItems().get(4)).getItems().get(i)).getItems().size()).isEqualTo(1);
                assertThat(((Menu) ((Menu) actual.getItems().get(4)).getItems().get(i)).getItems().get(0).getText()).isEqualTo(expectedMenuText);
                assertThat(((Menu) ((Menu) actual.getItems().get(4)).getItems().get(i)).getItems().get(0).isDisable()).isEqualTo(true);
            }
        }

        @Test
        void コンポジション関係の右クリックメニューを整形する() {
            String composition = "- composition";

            ContextMenu expected = new ContextMenu();

            expected.getItems().add(new MenuItem(composition + " の変更"));
            expected.getItems().add(new MenuItem(composition + " の削除"));

            ContextMenu actual = util.createClassContextMenuInCD(composition, ContentType.Composition);

            assertThat(actual.getItems().size()).isEqualTo(expected.getItems().size());
            assertThat(actual.getItems().size()).isEqualTo(2);
            for (int i = 0; i < actual.getItems().size(); i++) {
                assertThat(actual.getItems().get(i).getText()).isEqualTo(expected.getItems().get(i).getText());
            }
        }

        @Test
        void 汎化関係の右クリックメニューを整形する() {

            ContextMenu expected = new ContextMenu();

            expected.getItems().add(new MenuItem("汎化の削除"));

            ContextMenu actual = util.createClassContextMenuInCD("", ContentType.Generalization);

            assertThat(actual.getItems().size()).isEqualTo(expected.getItems().size());
            assertThat(actual.getItems().size()).isEqualTo(1);
            assertThat(actual.getItems().get(0).getText()).isEqualTo(expected.getItems().get(0).getText());
        }
    }

    @Nested
    class 多角形における内外判定に関して {

        @BeforeEach
        void setUp() {
            util = new UtilityJavaFXComponent();
        }

        @Test
        void 旧版_角が複数存在する多角形の内側に点が存在するか否かを確認する() {
            List<Point2D> quadrangle1 = Arrays.asList(
                    new Point2D(100.0, 100.0), new Point2D(100.0, 500.0),
                    new Point2D(200.0, 500.0), new Point2D(200.0, 100.0));
            Point2D insidePoint1 = new Point2D(150.0, 300.0);
            Point2D outsidePoint1 = new Point2D(250.0, 300.0);
            Point2D onLinePoint1 = new Point2D(100.0, 300.0);
            Point2D onEdgePoint1 = new Point2D(100.0, 100.0);
            List<Point2D> quadrangle2 = Arrays.asList(
                    new Point2D(200.0, 100.0), new Point2D(400.0, 300.0),
                    new Point2D(300.0, 400.0), new Point2D(100.0, 200.0));
            Point2D insidePoint2 = new Point2D(200.0, 200.0);
            Point2D outsidePoint2 = new Point2D(120.0, 120.0);
            Point2D onLinePoint2 = new Point2D(200.0, 300.0);
            Point2D onEdgePoint2 = new Point2D(400.0, 300.0);

            boolean actual1 = util.isInsidePointFromPolygonUsingJavaAwtPolygonMethod(quadrangle1, insidePoint1);
            boolean actual2 = util.isInsidePointFromPolygonUsingJavaAwtPolygonMethod(quadrangle1, outsidePoint1);
            boolean actual3 = util.isInsidePointFromPolygonUsingJavaAwtPolygonMethod(quadrangle1, onLinePoint1);
            boolean actual4 = util.isInsidePointFromPolygonUsingJavaAwtPolygonMethod(quadrangle1, onEdgePoint1);
            boolean actual5 = util.isInsidePointFromPolygonUsingJavaAwtPolygonMethod(quadrangle2, insidePoint2);
            boolean actual6 = util.isInsidePointFromPolygonUsingJavaAwtPolygonMethod(quadrangle2, outsidePoint2);
            boolean actual7 = util.isInsidePointFromPolygonUsingJavaAwtPolygonMethod(quadrangle2, onLinePoint2);
            boolean actual8 = util.isInsidePointFromPolygonUsingJavaAwtPolygonMethod(quadrangle2, onEdgePoint2);

            assertThat(actual1).isTrue();
            assertThat(actual2).isFalse();
            assertThat(actual3).isTrue();
            assertThat(actual4).isTrue();
            assertThat(actual5).isTrue();
            assertThat(actual6).isFalse();
            assertThat(actual7).isTrue();
            assertThat(actual8).isTrue();
        }

        @Test
        void 角が複数存在する多角形の内側に点が存在するか否かを確認する() {
            List<Point2D> quadrangle1 = Arrays.asList(
                    new Point2D(100.0, 100.0), new Point2D(100.0, 500.0),
                    new Point2D(200.0, 500.0), new Point2D(200.0, 100.0));
            Point2D insidePoint1 = new Point2D(150.0, 300.0);
            Point2D outsidePoint1 = new Point2D(250.0, 300.0);
            Point2D onLinePoint1 = new Point2D(100.0, 300.0);
            Point2D onEdgePoint1 = new Point2D(100.0, 100.0);
            List<Point2D> quadrangle2 = Arrays.asList(
                    new Point2D(200.0, 100.0), new Point2D(400.0, 300.0),
                    new Point2D(300.0, 400.0), new Point2D(100.0, 200.0));
            Point2D insidePoint2 = new Point2D(200.0, 200.0);
            Point2D outsidePoint2 = new Point2D(120.0, 120.0);
            Point2D onLinePoint2 = new Point2D(200.0, 300.0);
            Point2D onEdgePoint2 = new Point2D(400.0, 300.0);
            List<Point2D> polygonWithSelfCross = Arrays.asList(
                    new Point2D(200.0, 100.0), new Point2D(300.0, 100.0),
                    new Point2D(300.0, 400.0), new Point2D(400.0, 400.0),
                    new Point2D(400.0, 300.0), new Point2D(100.0, 300.0),
                    new Point2D(100.0, 200.0), new Point2D(500.0, 200.0),
                    new Point2D(200.0, 100.0), new Point2D(300.0, 100.0),
                    new Point2D(500.0, 500.0), new Point2D(200.0, 500.0));
            Point2D holePoint = new Point2D(350.0, 350.0);
            Point2D selfCrossPoint = new Point2D(250.0, 250.0);

            boolean actual1 = util.isInsidePointFromPolygonUsingWNA(quadrangle1, insidePoint1);
            boolean actual2 = util.isInsidePointFromPolygonUsingWNA(quadrangle1, outsidePoint1);
            boolean actual3 = util.isInsidePointFromPolygonUsingWNA(quadrangle1, onLinePoint1);
            boolean actual4 = util.isInsidePointFromPolygonUsingWNA(quadrangle1, onEdgePoint1);
            boolean actual5 = util.isInsidePointFromPolygonUsingWNA(quadrangle2, insidePoint2);
            boolean actual6 = util.isInsidePointFromPolygonUsingWNA(quadrangle2, outsidePoint2);
            boolean actual7 = util.isInsidePointFromPolygonUsingWNA(quadrangle2, onLinePoint2);
            boolean actual8 = util.isInsidePointFromPolygonUsingWNA(quadrangle2, onEdgePoint2);
            boolean actual9 = util.isInsidePointFromPolygonUsingWNA(polygonWithSelfCross, holePoint);
            boolean actual10 = util.isInsidePointFromPolygonUsingWNA(polygonWithSelfCross, selfCrossPoint);

            assertThat(actual1).isTrue();
            assertThat(actual2).isFalse();
            assertThat(actual3).isTrue();
            assertThat(actual4).isTrue();
            assertThat(actual5).isTrue();
            assertThat(actual6).isFalse();
            assertThat(actual7).isTrue();
            assertThat(actual8).isTrue();
            assertThat(actual9).isFalse();
            assertThat(actual10).isTrue();
        }

        @Test
        void ノードの中央のポイントと幅と高さを入力するとその頂点のリストを返す() {
            List<Point2D> expected = Arrays.asList(
                    new Point2D(150.0, 60.0), new Point2D(150.0, 140.0),
                    new Point2D(50.0, 140.0), new Point2D(50.0, 60.0)
            );
            Point2D center = new Point2D(100.0, 100.0);
            double width = 100.0;
            double height = 80.0;

            List<Point2D> actual = util.calculateTopListFromNode(center, width, height);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 線分の始点と終点を2つ入力すると線分の交点が存在するか否かを確認する() {
            Point2D line1S = new Point2D(100.0, 100.0);
            Point2D line1E = new Point2D(200.0, 200.0);
            Point2D line2S = new Point2D(200.0, 100.0);
            Point2D line2E = new Point2D(100.0, 200.0);
            Point2D line3S = new Point2D(300.0, 100.0);
            Point2D line3E = new Point2D(300.0, 300.0);
            Point2D line4S = new Point2D(300.0, 100.0);
            Point2D line4E = new Point2D(100.0, 300.0);
            Point2D line5S = new Point2D(200.0, 100.0);
            Point2D line5E = new Point2D(200.0, 200.0);
            Point2D line6S = new Point2D(200.0, 200.0);
            Point2D line6E = new Point2D(300.0, 300.0);
            Point2D line7S = new Point2D(200.0, 100.0);
            Point2D line7E = new Point2D(300.0, 200.0);
            Point2D line8S = new Point2D(100.0, 0.0);
            Point2D line8E = new Point2D(0.0, 100.0);

            boolean actual1And2 = util.isIntersected(line1S, line1E, line2S, line2E);
            boolean actual1And3 = util.isIntersected(line1S, line1E, line3S, line3E);
            boolean actual1And4 = util.isIntersected(line1S, line1E, line4S, line4E);
            boolean actual1And5 = util.isIntersected(line1S, line1E, line5S, line5E);
            boolean actual1And6 = util.isIntersected(line1S, line1E, line6S, line6E);
            boolean actual1And7 = util.isIntersected(line1S, line1E, line7S, line7E);
            boolean actual1And8 = util.isIntersected(line1S, line1E, line8S, line8E);

            assertThat(actual1And2).isTrue();
            assertThat(actual1And3).isFalse();
            assertThat(actual1And4).isTrue();
            assertThat(actual1And5).isTrue();
            assertThat(actual1And6).isTrue();
            assertThat(actual1And7).isFalse();
            assertThat(actual1And8).isFalse();
        }

        @Test
        void 線分の始点と終点を2つ入力すると交点が存在する場合は線分の交点を返し存在しない場合はnullを返す() {
            Point2D line1S = new Point2D(100.0, 100.0);
            Point2D line1E = new Point2D(200.0, 200.0);
            Point2D line2S = new Point2D(200.0, 100.0);
            Point2D line2E = new Point2D(100.0, 200.0);
            Point2D line3S = new Point2D(300.0, 100.0);
            Point2D line3E = new Point2D(300.0, 300.0);
            Point2D line4S = new Point2D(300.0, 100.0);
            Point2D line4E = new Point2D(100.0, 300.0);
            Point2D line5S = new Point2D(200.0, 100.0);
            Point2D line5E = new Point2D(200.0, 200.0);
            Point2D line6S = new Point2D(200.0, 200.0);
            Point2D line6E = new Point2D(300.0, 300.0);
            Point2D line7S = new Point2D(200.0, 100.0);
            Point2D line7E = new Point2D(300.0, 200.0);
            Point2D line8S = new Point2D(100.0, 0.0);
            Point2D line8E = new Point2D(0.0, 100.0);
            Point2D expected1And2 = new Point2D(150.0, 150.0);
            Point2D expected1And3 = null;
            Point2D expected1And4 = new Point2D(200.0, 200.0);
            Point2D expected1And5 = new Point2D(200.0, 200.0);
            Point2D expected1And6 = new Point2D(200.0, 200.0);
            Point2D expected1And7 = null;
            Point2D expected1And8 = null;

            Point2D actual1And2 = util.calculateCrossPoint(line1S, line1E, line2S, line2E);
            Point2D actual1And3 = util.calculateCrossPoint(line1S, line1E, line3S, line3E);
            Point2D actual1And4 = util.calculateCrossPoint(line1S, line1E, line4S, line4E);
            Point2D actual1And5 = util.calculateCrossPoint(line1S, line1E, line5S, line5E);
            Point2D actual1And6 = util.calculateCrossPoint(line1S, line1E, line6S, line6E);
            Point2D actual1And7 = util.calculateCrossPoint(line1S, line1E, line7S, line7E);
            Point2D actual1And8 = util.calculateCrossPoint(line1S, line1E, line8S, line8E);

            assertThat(actual1And2).isEqualTo(expected1And2);
            assertThat(actual1And3).isEqualTo(expected1And3);
            assertThat(actual1And4).isEqualTo(expected1And4);
            assertThat(actual1And5).isEqualTo(expected1And5);
            assertThat(actual1And6).isEqualTo(expected1And6);
            assertThat(actual1And7).isEqualTo(expected1And7);
            assertThat(actual1And8).isEqualTo(expected1And8);
        }
    }
}