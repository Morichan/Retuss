package io.github.morichan.retuss.retuss;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RelationshipAttributeTest {
    RelationshipAttribute attribute;
    List<RelationshipAttribute> attributes = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        attribute = new RelationshipAttribute();

        attributes.add( new RelationshipAttribute() );
        attributes.add( new RelationshipAttribute() );
        attributes.add( new RelationshipAttribute() );
    }

    @Test
    public void 関係属性を追加するとその属性を返す() {
        String expected = "relationshipAttribute";

        attribute.setName( expected );

        assertThat( attribute.getName() ).isEqualTo( expected );
    }

    @Test
    public void 関係属性の表示を真にすると真を返す() {
        boolean expected = true;

        attribute.setIndication( expected );

        assertThat( attribute.isIndicate() ).isEqualTo( expected );
    }

    @Test
    public void 関係属性のリストに名前を追加するとその名前のリストを返す() {
        List< String > expected = Arrays.asList( "attribute1", "attribute2", "attribute3" );

        for( int i = 0; i < expected.size(); i++ )
            attributes.get( i ).setName( expected.get( i ) );

        for( int i = 0; i < expected.size(); i++ )
            assertThat( attributes.get( i ).getName() ).isEqualTo( expected.get( i ) );
    }

    @Test
    public void 関係属性のリストに真偽を追加するとその真偽を返す() {
        List< Boolean > expected = Arrays.asList( true, false, false );

        for( int i = 0; i < expected.size(); i++ )
            attributes.get( i ).setIndication( expected.get( i ) );

        for( int i = 0; i < expected.size(); i++ )
            assertThat( attributes.get( i ).isIndicate() ).isEqualTo( expected.get( i ) );
    }

    @Test
    public void 関係属性の関係先のIDを設定するとそのIDを返す() {
        int expected = 1;

        attribute.setRelationId( expected );

        assertThat( attribute.getRelationId() ).isEqualTo( expected );
    }

    @Test
    public void 関係属性の関係元のIDを設定するとそのIDを返す() {
        int expected = 0;

        attribute.setRelationSourceId( expected );

        assertThat( attribute.getRelationSourceId() ).isEqualTo( expected );
    }

    @Test
    public void 関係属性の関係先のポイントを設定するとそのポイントを返す() {
        Point2D expected = new Point2D( 100.0, 200.0 );

        attribute.setRelationPoint( expected );
        Point2D actual = attribute.getRelationPoint();

        assertThat( actual ).isEqualTo( expected );
    }

    @Test
    public void 関係属性の関係元のポイントを設定するとそのポイントを返す() {
        Point2D expected = new Point2D( 300.0, 400.0 );

        attribute.setRelationSourcePoint( expected );
        Point2D actual = attribute.getRelationSourcePoint();

        assertThat( actual ).isEqualTo( expected );
    }
}