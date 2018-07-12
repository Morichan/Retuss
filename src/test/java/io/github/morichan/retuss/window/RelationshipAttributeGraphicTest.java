package io.github.morichan.retuss.window;

import io.github.morichan.retuss.window.diagram.RelationshipAttributeGraphic;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RelationshipAttributeGraphicTest {
    RelationshipAttributeGraphic attribute;
    List<RelationshipAttributeGraphic> attributes = new ArrayList<>();

    @BeforeEach
    void setUp() {
        attribute = new RelationshipAttributeGraphic();

        attributes.add( new RelationshipAttributeGraphic() );
        attributes.add( new RelationshipAttributeGraphic() );
        attributes.add( new RelationshipAttributeGraphic() );
    }

    @Test
    void 関係属性を追加するとその属性を返す() {
        String expected = "relationshipAttribute";

        attribute.setText( expected );

        assertThat( attribute.getText() ).isEqualTo( expected );
    }

    @Test
    void 関係属性の表示を真にすると真を返す() {
        boolean expected = true;

        attribute.setIndication( expected );

        assertThat( attribute.isIndicate() ).isEqualTo( expected );
    }

    @Test
    void 関係属性のリストに名前を追加するとその名前のリストを返す() {
        List< String > expected = Arrays.asList( "attribute1", "attribute2", "attribute3" );

        for( int i = 0; i < expected.size(); i++ )
            attributes.get( i ).setText( expected.get( i ) );

        for( int i = 0; i < expected.size(); i++ )
            assertThat( attributes.get( i ).getText() ).isEqualTo( expected.get( i ) );
    }

    @Test
    void 関係属性のリストに真偽を追加するとその真偽を返す() {
        List< Boolean > expected = Arrays.asList( true, false, false );

        for( int i = 0; i < expected.size(); i++ )
            attributes.get( i ).setIndication( expected.get( i ) );

        for( int i = 0; i < expected.size(); i++ )
            assertThat( attributes.get( i ).isIndicate() ).isEqualTo( expected.get( i ) );
    }

    @Test
    void 関係属性の関係先のIDを設定するとそのIDを返す() {
        int expected = 1;

        attribute.setRelationId( expected );

        assertThat( attribute.getRelationId() ).isEqualTo( expected );
    }

    @Test
    void 関係属性の関係元のIDを設定するとそのIDを返す() {
        int expected = 0;

        attribute.setRelationSourceId( expected );

        assertThat( attribute.getRelationSourceId() ).isEqualTo( expected );
    }

    @Test
    void 関係属性の関係先のポイントを設定するとそのポイントを返す() {
        Point2D expected = new Point2D( 100.0, 200.0 );

        attribute.setRelationPoint( expected );
        Point2D actual = attribute.getRelationPoint();

        assertThat( actual ).isEqualTo( expected );
    }

    @Test
    void 関係属性の関係元のポイントを設定するとそのポイントを返す() {
        Point2D expected = new Point2D( 300.0, 400.0 );

        attribute.setRelationSourcePoint( expected );
        Point2D actual = attribute.getRelationSourcePoint();

        assertThat( actual ).isEqualTo( expected );
    }
}