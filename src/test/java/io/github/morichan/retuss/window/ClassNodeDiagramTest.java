package io.github.morichan.retuss.window;

import io.github.morichan.retuss.window.diagram.*;
import javafx.geometry.Point2D;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ClassNodeDiagramTest {

    ClassNodeDiagram obj;

    @BeforeEach
    public void setObj() {
        obj = new ClassNodeDiagram();
    }

    @Test
    public void デフォルトのクラスの幅より大きい数値がクラス名に存在する場合大きな値にゆとり分を追加した幅を返す() {
        Text longClassName = new Text( "VeryVeryLongClassName" );
        List< Text > attributesAreNotWideWidthFromDefault = Arrays.asList( new Text( "short" ), new Text( "attribute" ) );
        List< Text > operationsAreNotWideWidthFromDefault = Arrays.asList( new Text( "short" ), new Text( "operation" ) );

        for( Text attribute :  attributesAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Attribute, attribute.getText() );
        for( Text operation :  operationsAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Operation, operation.getText() );

        double actual = obj.calculateMaxWidth( longClassName, attributesAreNotWideWidthFromDefault, operationsAreNotWideWidthFromDefault );

        assertThat( actual ).isEqualTo( longClassName.getLayoutBounds().getWidth() + obj.getClassNameSpace() );
    }

    @Test
    public void デフォルトのクラスの幅より大きい数値がクラス属性の1つ目に存在する場合大きな値にゆとり分を追加した幅を返す() {
        Text shortClassName = new Text( "a" );
        List< Text > attributesAreNotWideWidthFromDefault = Arrays.asList( new Text( "- veryVeryLongClassAttribute : double" ), new Text( "attribute" ) );
        List< Text > operationsAreNotWideWidthFromDefault = Arrays.asList( new Text( "short" ), new Text( "operation" ) );

        for( Text attribute :  attributesAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Attribute, attribute.getText() );
        for( Text operation :  operationsAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Operation, operation.getText() );

        double actual = obj.calculateMaxWidth( shortClassName, attributesAreNotWideWidthFromDefault, operationsAreNotWideWidthFromDefault );

        assertThat( actual ).isEqualTo( attributesAreNotWideWidthFromDefault.get( 0 ).getLayoutBounds().getWidth() + obj.getClassNameSpace() );
    }

    @Test
    public void デフォルトのクラスの幅より大きい数値がクラス属性の2つ目に存在する場合大きな値にゆとり分を追加した幅を返す() {
        Text shortClassName = new Text( "a" );
        List< Text > attributesAreNotWideWidthFromDefault = Arrays.asList( new Text( "attribute" ), new Text( "- veryVeryLongClassAttribute : double" ) );
        List< Text > operationsAreNotWideWidthFromDefault = Arrays.asList( new Text( "short" ), new Text( "operation" ) );

        for( Text attribute :  attributesAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Attribute, attribute.getText() );
        for( Text operation :  operationsAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Operation, operation.getText() );

        double actual = obj.calculateMaxWidth( shortClassName, attributesAreNotWideWidthFromDefault, operationsAreNotWideWidthFromDefault );

        assertThat( actual ).isEqualTo( attributesAreNotWideWidthFromDefault.get( 1 ).getLayoutBounds().getWidth() + obj.getClassNameSpace() );
    }

    @Test
    public void デフォルトのクラスの幅より大きい数値がクラス操作の3つ目に存在する場合大きな値にゆとり分を追加した幅を返す() {
        Text shortClassName = new Text( "a" );
        List< Text > attributesAreNotWideWidthFromDefault = Arrays.asList( new Text( "short" ), new Text( "attribute" ) );
        List< Text > operationsAreNotWideWidthFromDefault = Arrays.asList( new Text( "short" ), new Text( "operation" ), new Text( "+ isVeryVeryLongClassOperation() : boolean") );

        for( Text attribute :  attributesAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Attribute, attribute.getText() );
        for( Text operation :  operationsAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Operation, operation.getText() );

        double actual = obj.calculateMaxWidth( shortClassName, attributesAreNotWideWidthFromDefault, operationsAreNotWideWidthFromDefault );

        assertThat( actual ).isEqualTo( operationsAreNotWideWidthFromDefault.get( 2 ).getLayoutBounds().getWidth() + obj.getClassNameSpace() );
    }

    @Test
    public void デフォルトのクラスの幅より大きい数値が存在しない場合デフォルト幅を返す() {
        Text shortClassName = new Text( "a" );
        List< Text > attributesAreNotWideWidthFromDefault = Arrays.asList( new Text( "short" ), new Text( "attribute" ) );
        List< Text > operationsAreNotWideWidthFromDefault = Arrays.asList( new Text( "short" ), new Text( "operation" ) );

        for( Text attribute :  attributesAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Attribute, attribute.getText() );
        for( Text operation :  operationsAreNotWideWidthFromDefault )
            obj.createNodeText( ContentType.Operation, operation.getText() );

        double actual = obj.calculateMaxWidth( shortClassName, attributesAreNotWideWidthFromDefault, operationsAreNotWideWidthFromDefault );

        assertThat( actual ).isEqualTo( 100.0 );
    }

    @Test
    public void クラス属性が存在しない場合はデフォルト高を返す() {
        List<ClassData> attributesAreNot = new ArrayList<>();

        double actual = obj.calculateMaxAttributeHeight( attributesAreNot );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス属性が1つ存在する場合はデフォルト高を返す() {
        List< ClassData > attributeIsOne = Arrays.asList( new Attribute( "attribute1" ) );

        double actual = obj.calculateMaxAttributeHeight( attributeIsOne );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス属性が複数存在する場合はデフォルト高の複数倍を返す() {
        // Arrange
        List< ClassData > attributesAreMoreTwo = new ArrayList<>();

        // Act
        attributesAreMoreTwo.add( new Attribute( "attribute1" ) );
        attributesAreMoreTwo.add( new Attribute( "attribute2" ) );
        double actual1 = obj.calculateMaxAttributeHeight( attributesAreMoreTwo );

        attributesAreMoreTwo.add( new Attribute( "attribute3" ) );
        double actual2 = obj.calculateMaxAttributeHeight( attributesAreMoreTwo );

        attributesAreMoreTwo.add( new Attribute( "attribute4" ) );
        double actual3 = obj.calculateMaxAttributeHeight( attributesAreMoreTwo );

        attributesAreMoreTwo.add( new Attribute( "attribute5" ) );
        double actual4 = obj.calculateMaxAttributeHeight( attributesAreMoreTwo );

        // Assert
        assertThat( actual1 ).isEqualTo( 20.0 * 2 );
        assertThat( actual2 ).isEqualTo( 20.0 * 3 );
        assertThat( actual3 ).isEqualTo( 20.0 * 4 );
        assertThat( actual4 ).isEqualTo( 20.0 * 5 );
    }

    @Test
    public void クラス属性が1つ存在しそれが未表示の場合はデフォルト高を返す() {
        List< ClassData > attributeIsOne = Arrays.asList( new Attribute( "attribute1" ) );

        attributeIsOne.get( 0 ).setIndication( false );
        double actual = obj.calculateMaxAttributeHeight( attributeIsOne );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス属性が3つ存在し1つが未表示の場合はデフォルト高の3倍を返す() {
        List< ClassData > attributes = Arrays.asList(
                new Attribute( "notVisibilityAttribute" ),
                new Attribute( "visibilityAttribute1" ),
                new Attribute( "visibilityAttribute2" ) );

        attributes.get( 0 ).setIndication( false );
        double actual = obj.calculateMaxAttributeHeight( attributes );

        assertThat( actual ).isEqualTo( 20.0 * 3 );
    }

    @Test
    public void クラス属性が3つ存在し2つが未表示の場合はデフォルト高の2倍を返す() {
        List< ClassData > attributes = Arrays.asList(
                new Attribute( "notVisibilityAttribute1" ),
                new Attribute( "notVisibilityAttribute2" ),
                new Attribute( "visibilityAttribute" ) );

        attributes.get( 0 ).setIndication( false );
        attributes.get( 1 ).setIndication( false );
        double actual = obj.calculateMaxAttributeHeight( attributes );

        assertThat( actual ).isEqualTo( 20.0 * 2 );
    }

    @Test
    public void クラス属性が3つ存在し全て未表示の場合はデフォルト高を返す() {
        List< ClassData > attributes = Arrays.asList(
                new Attribute( "notVisibilityAttribute1" ),
                new Attribute( "notVisibilityAttribute2" ),
                new Attribute( "notVisibilityAttribute3" ) );

        attributes.get( 0 ).setIndication( false );
        attributes.get( 1 ).setIndication( false );
        attributes.get( 2 ).setIndication( false );
        double actual = obj.calculateMaxAttributeHeight( attributes );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス操作が存在しない場合はデフォルト高を返す() {
        List< ClassData > operationsAreNot = new ArrayList<>();

        double actual = obj.calculateMaxOperationHeight( operationsAreNot );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス操作が1つ存在する場合はデフォルト高を返す() {
        List< ClassData > operationIsOne = Arrays.asList( new Operation( "operation1" ) );

        double actual = obj.calculateMaxOperationHeight( operationIsOne );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス操作が複数存在する場合はデフォルト高の複数倍を返す() {
        // Arrange
        List< ClassData > operationsAreMoreTwo = new ArrayList<>();

        // Act
        operationsAreMoreTwo.add( new Operation( "operation1" ) );
        operationsAreMoreTwo.add( new Operation( "operation2" ) );
        double actual1 = obj.calculateMaxOperationHeight( operationsAreMoreTwo );

        operationsAreMoreTwo.add( new Operation( "operation3" ) );
        double actual2 = obj.calculateMaxOperationHeight( operationsAreMoreTwo );

        operationsAreMoreTwo.add( new Operation( "operation4" ) );
        double actual3 = obj.calculateMaxOperationHeight( operationsAreMoreTwo );

        operationsAreMoreTwo.add( new Operation( "operation5" ) );
        double actual4 = obj.calculateMaxOperationHeight( operationsAreMoreTwo );

        // Assert
        assertThat( actual1 ).isEqualTo( 20.0 * 2 );
        assertThat( actual2 ).isEqualTo( 20.0 * 3 );
        assertThat( actual3 ).isEqualTo( 20.0 * 4 );
        assertThat( actual4 ).isEqualTo( 20.0 * 5 );
    }

    @Test
    public void クラス操作が1つ存在しそれが未表示の場合はデフォルト高を返す() {
        List< ClassData > operationIsOne = Arrays.asList( new Operation( "operation1" ) );
        operationIsOne.get( 0 ).setIndication( false );

        double actual = obj.calculateMaxOperationHeight( operationIsOne );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス操作が3つ存在し1つが未表示の場合はデフォルト高の3倍を返す() {
        List< ClassData > operations = Arrays.asList(
                new Operation( "notVisibilityOperation" ),
                new Operation( "visibilityOperation1" ),
                new Operation( "visibilityOperation2" ) );
        operations.get( 0 ).setIndication( false );

        double actual = obj.calculateMaxOperationHeight( operations );

        assertThat( actual ).isEqualTo( 20.0 * 3 );
    }

    @Test
    public void クラス操作が3つ存在し2つが未表示の場合はデフォルト高の2倍を返す() {
        List< ClassData > operations = Arrays.asList(
                new Operation( "notVisibilityOperation1" ),
                new Operation( "notVisibilityOperation2" ),
                new Operation( "visibilityOperation" ) );

        operations.get( 0 ).setIndication( false );
        operations.get( 1 ).setIndication( false );
        double actual = obj.calculateMaxOperationHeight( operations );

        assertThat( actual ).isEqualTo( 20.0 * 2 );
    }

    @Test
    public void クラス操作が3つ存在し全て未表示の場合はデフォルト高を返す() {
        List< ClassData > operations = Arrays.asList(
                new Operation( "notVisibilityOperation1" ),
                new Operation( "notVisibilityOperation2" ),
                new Operation( "notVisibilityOperation3" ) );

        operations.get( 0 ).setIndication( false );
        operations.get( 1 ).setIndication( false );
        operations.get( 2 ).setIndication( false );
        double actual = obj.calculateMaxOperationHeight( operations );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス属性が存在しない場合は属性1つ分の高さを返す() {
        List< ClassData > attributes = new ArrayList<>();

        for( ClassData attribute : attributes ) {
            obj.createNodeText( ContentType.Attribute, attribute.getName() );
        }
        obj.calculateMaxAttributeHeight( attributes );
        double actual = obj.calculateStartOperationHeight( attributes );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス属性が1つ存在する場合は属性1つ分の高さを返す() {
        List< ClassData > attributes = Arrays.asList( new Attribute( "oneVisibilityAttribute" ) );

        for( ClassData attribute : attributes ) {
            obj.createNodeText( ContentType.Attribute, attribute.getName() );
        }
        obj.calculateMaxAttributeHeight( attributes );
        double actual = obj.calculateStartOperationHeight( attributes );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス属性が2つ存在する場合は属性2つ分の高さを返す() {
        List< ClassData > attributes = Arrays.asList(
                new Attribute( "visibilityAttribute1"),
                new Attribute( "visibilityAttribute2" ) );

        for( ClassData attribute : attributes ) {
            obj.createNodeText( ContentType.Attribute, attribute.getName() );
        }
        obj.calculateMaxAttributeHeight( attributes );
        double actual = obj.calculateStartOperationHeight( attributes );

        assertThat( actual ).isEqualTo( 40.0 );
    }

    @Test
    public void 非表示のクラス属性が1つ存在する場合は属性1つ分の高さを返す() {
        List< ClassData > attributes = Arrays.asList( new Attribute( "notVisibilityAttribute" ) );

        for( ClassData attribute : attributes ) {
            obj.createNodeText( ContentType.Attribute, attribute.getName() );
        }
        obj.setNodeContentBoolean( ContentType.Attribute, ContentType.Indication, 0, false );
        obj.calculateMaxAttributeHeight( attributes );
        double actual = obj.calculateStartOperationHeight( attributes );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラス属性が2つ存在し1つ目が非表示の場合は属性2つ分の高さを返す() {
        List< ClassData > attributes = Arrays.asList(
                new Attribute( "notVisibilityAttribute" ),
                new Attribute( "visibilityAttribute" ) );

        for( ClassData attribute : attributes ) {
            obj.createNodeText( ContentType.Attribute, attribute.getName() );
        }
        obj.setNodeContentBoolean( ContentType.Attribute, ContentType.Indication, 0, false );
        obj.calculateMaxAttributeHeight( attributes );
        double actual = obj.calculateStartOperationHeight( attributes );

        assertThat( actual ).isEqualTo( 40.0 );
    }

    @Test
    public void 非表示のクラス属性が2つ存在する場合は属性1つ分の高さを返す() {
        List< ClassData > attributes = Arrays.asList(
                new Attribute( "notVisibilityAttribute1" ),
                new Attribute( "notVisibilityAttribute2" ) );

        attributes.get( 0 ).setIndication( false );
        attributes.get( 1 ).setIndication( false );
        obj.calculateMaxAttributeHeight( attributes );
        double actual = obj.calculateStartOperationHeight( attributes );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void 非表示のクラス属性が3つ存在する場合は属性1つ分の高さを返す() {
        List< ClassData > attributes = Arrays.asList(
                new Attribute( "notVisibilityAttribute1" ),
                new Attribute( "notVisibilityAttribute2" ),
                new Attribute( "notVisibilityAttribute3" ) );

        attributes.get( 0 ).setIndication( false );
        attributes.get( 1 ).setIndication( false );
        attributes.get( 2 ).setIndication( false );
        obj.calculateMaxAttributeHeight( attributes );
        double actual = obj.calculateStartOperationHeight( attributes );

        assertThat( actual ).isEqualTo( 20.0 );
    }

    @Test
    public void クラスの位置に存在するかどうかを判定する() {
        double clickedX = 100;
        double clickedY = 200;
        obj.createNodeText( ContentType.Title, "ClassName" );
        obj.setMouseCoordinates( clickedX, clickedY );
        obj.calculateWidthAndHeight( 100, 80.0 );

        boolean actual = obj.isAlreadyDrawnNode( clickedX, clickedY );

        assertThat( actual ).isTrue();
    }

    @Test
    public void クラスが大きくなった場合の位置に存在するかどうかを判定する() {
        // Arrange
        double clickedX = 100;
        double clickedY = 200;
        obj.createNodeText( ContentType.Title, "ClassName" );
        List< String > attributes = Arrays.asList( "attribute1", "attribute2", "attribute3" );
        List< String > operations = Arrays.asList( "operation1", "operation2", "operation3" );

        // Act
        for( String attribute : attributes ) {
            obj.createNodeText( ContentType.Attribute, attribute );
        }
        for( String operation : operations ) {
            obj.createNodeText( ContentType.Operation, operation );
        }
        obj.setMouseCoordinates( clickedX, clickedY );
        obj.calculateWidthAndHeight( 140, 40.0 + 60.0 + 60.0 );

        // Assert
        List<Point2D> inClassPoint = Arrays.asList(
                new Point2D( 100, 200 ), new Point2D( 31, 121 ), new Point2D( 169, 121 ), new Point2D( 169, 279 ), new Point2D( 31, 279 ) );
        List< Point2D > outClassPoint = Arrays.asList(
                new Point2D( 30, 120 ), new Point2D( 170, 120 ), new Point2D( 170, 280 ), new Point2D( 30, 280 ),
                new Point2D( 100, 120 ), new Point2D( 170, 200 ), new Point2D( 100, 280 ), new Point2D( 30, 200 ) );
        for( Point2D point : inClassPoint ) {
            assertThat( obj.isAlreadyDrawnNode( point.getX(), point.getY() ) ).isTrue();
        }
        for( Point2D point : outClassPoint ) {
            assertThat( obj.isAlreadyDrawnNode( point.getX(), point.getY() ) ).isFalse();
        }
    }

    @Test
    public void クラス名を取得する() {
        String className = "ClassName";
        obj.createNodeText( ContentType.Title, className );

        String actual = obj.getNodeText();

        assertThat( actual ).isEqualTo( className );
    }

    @Test
    public void 属性を追加する() {
        String className = "ClassName";
        String expected = "- attribute : int";

        obj.createNodeText( ContentType.Title, className );
        obj.createNodeText( ContentType.Attribute, expected );
        String actual = obj.getNodeContentText( ContentType.Attribute, 0 );

        assertThat( actual ).isEqualTo( expected );
    }

    @Test
    public void 属性を変更する() {
        String className = "ClassName";
        String firstInputClassAttribute = "- attribute : int";
        String expected = "- attribute : double";

        obj.createNodeText( ContentType.Title, className );
        obj.createNodeText( ContentType.Attribute, firstInputClassAttribute );
        obj.changeNodeText( ContentType.Attribute, 0, expected );
        String actual = obj.getNodeContentText( ContentType.Attribute, 0 );

        assertThat( actual ).isEqualTo( expected );
    }

    @Test
    public void 属性を削除する() {
        String className = "ClassName";
        String expected = "- attribute : int";

        obj.createNodeText( ContentType.Title, className );
        obj.createNodeText( ContentType.Attribute, expected );
        obj.deleteNodeText( ContentType.Attribute, 0 );

        assertThrows( IndexOutOfBoundsException.class, () -> obj.getNodeContentText( ContentType.Attribute, 0 ) );
    }

    @Test
    public void 属性を非表示にする() {
        String className = "ClassName";
        String classAttribute = "- attribute : int";

        obj.createNodeText( ContentType.Title, className );
        obj.createNodeText( ContentType.Attribute, classAttribute );
        obj.setNodeContentBoolean( ContentType.Attribute, ContentType.Indication, 0, false );

        assertThat( obj.getNodeContentsBoolean( ContentType.Attribute, ContentType.Indication).get( 0 ) ).isFalse();
    }

    @Test
    public void 操作を追加する() {
        String className = "ClassName";
        String expected = "+ operation() : void";

        obj.createNodeText( ContentType.Title, className );
        obj.createNodeText( ContentType.Operation, expected );
        String actual = obj.getNodeContentText( ContentType.Operation, 0 );

        assertThat( actual ).isEqualTo( expected );
    }

    @Test
    public void 操作を変更する() {
        String className = "ClassName";
        String firstInputClassOperation = "+ operation() : void";
        String expected = "+ operation() : int";

        obj.createNodeText( ContentType.Title, className );
        obj.createNodeText( ContentType.Operation, firstInputClassOperation );
        obj.changeNodeText( ContentType.Operation, 0, expected );
        String actual = obj.getNodeContentText( ContentType.Operation, 0 );

        assertThat( actual ).isEqualTo( expected );
    }

    @Test
    public void 操作を削除する() {
        String className = "ClassName";
        String expected = "- operation() : void";

        obj.createNodeText( ContentType.Title, className );
        obj.createNodeText( ContentType.Operation, expected );
        obj.deleteNodeText( ContentType.Operation, 0 );

        assertThrows( IndexOutOfBoundsException.class, () -> obj.getNodeContentText( ContentType.Operation, 0 ) );
    }

    @Test
    public void 操作を非表示にする() {
        String className = "ClassName";
        String classOperation = "- operation() : void";

        obj.createNodeText( ContentType.Title, className );
        obj.createNodeText( ContentType.Operation, classOperation );
        obj.setNodeContentBoolean( ContentType.Operation, ContentType.Indication, 0, false );

        assertThat( obj.getNodeContentsBoolean( ContentType.Operation, ContentType.Indication).get( 0 ) ).isFalse();
    }

    @Test
    public void クラスの中央ポイントを取得する() {
        Point2D expected = new Point2D( 100, 200 );
        obj.setMouseCoordinates( expected.getX(), expected.getY() );
        obj.createNodeText( ContentType.Title, "ClassName" );

        Point2D actual = obj.getPoint();

        assertThat( actual ).isEqualTo( expected );
    }

    @Test
    public void クラスの幅と高さを取得する() {
        double expectedWidth = 100.0;
        double expectedHeight = 80.0;
        obj.setMouseCoordinates( 100.0, 200.0 );
        obj.createNodeText( ContentType.Title, "Test" );
        obj.calculateWidthAndHeight( 100.0, 80.0 );

        double actualWidth = obj.getWidth();
        double actualHeight = obj.getHeight();

        assertThat( actualWidth ).isEqualTo( expectedWidth );
        assertThat( actualHeight ).isEqualTo( expectedHeight );
    }
}