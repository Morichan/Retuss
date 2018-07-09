package io.github.morichan.retuss.window;

import io.github.morichan.retuss.window.diagram.Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AttributeTest {
    Attribute attribute;
    List<Attribute> attributes = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        attribute = new Attribute();

        attributes.add( new Attribute() );
        attributes.add( new Attribute() );
        attributes.add( new Attribute() );
    }

    @Test
    public void 属性を追加するとその属性を返す() {
        String expected = "attribute";

        attribute.setName( expected );

        assertThat( attribute.getName() ).isEqualTo( expected );
    }

    @Test
    public void 属性の表示を真にすると真を返す() {
        boolean expected = true;

        attribute.setIndication( expected );

        assertThat( attribute.isIndicate() ).isEqualTo( expected );
    }

    @Test
    public void 属性のリストに名前を追加するとその名前のリストを返す() {
        List< String > expected = Arrays.asList( "attribute1", "attribute2", "attribute3" );

        for( int i = 0; i < expected.size(); i++ )
            attributes.get( i ).setName( expected.get( i ) );

        for( int i = 0; i < expected.size(); i++ )
            assertThat( attributes.get( i ).getName() ).isEqualTo( expected.get( i ) );
    }

    @Test
    public void 属性のリストに真偽を追加するとその真偽を返す() {
        List< Boolean > expected = Arrays.asList( true, false, false );

        for( int i = 0; i < expected.size(); i++ )
            attributes.get( i ).setIndication( expected.get( i ) );

        for( int i = 0; i < expected.size(); i++ )
            assertThat( attributes.get( i ).isIndicate() ).isEqualTo( expected.get( i ) );
    }

}