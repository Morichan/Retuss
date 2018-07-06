package io.github.morichan.retuss.retuss;

import javafx.geometry.Point2D;

public class RelationshipAttribute extends Attribute {
    private int relationId;
    private int relationSourceId;
    private Point2D relationPoint;
    private Point2D relationSourcePoint;

    RelationshipAttribute() {
        name = "";
        visibility = "";
        type = ContentType.Undefined;
        isIndicate = true;
        relationPoint = new Point2D( 0.0, 0.0 );
        relationSourcePoint = new Point2D( 0.0, 0.0 );
    }

    RelationshipAttribute(String name ) {
        this.name = name;
        isIndicate = true;
        relationPoint = new Point2D( 0.0, 0.0 );
        relationSourcePoint = new Point2D( 0.0, 0.0 );
    }

    public void setRelationId( int id ) {
        relationId = id;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationSourceId( int id ) {
        relationSourceId = id;
    }

    public int getRelationSourceId() {
        return relationSourceId;
    }

    public void setRelationPoint( Point2D point ) {
        relationPoint = point;
    }

    public Point2D getRelationPoint() {
        return relationPoint;
    }

    public void setRelationSourcePoint( Point2D point ) {
        relationSourcePoint = point;
    }

    public Point2D getRelationSourcePoint() {
        return relationSourcePoint;
    }
}
