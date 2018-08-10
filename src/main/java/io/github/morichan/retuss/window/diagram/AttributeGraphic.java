package io.github.morichan.retuss.window.diagram;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.sculptor.AttributeSculptor;

/**
 * <p> クラスの属性に関するクラス </p>
 *
 * <p>
 *     このクラスは、クラス図におけるクラスの属性に関するクラスです。
 *     {@link ClassDiagramGraphic} クラスを継承しています。
 * </p>
 */
public class AttributeGraphic extends ClassDiagramGraphic {

    private AttributeSculptor sculptor = new AttributeSculptor();
    private Attribute attribute;

    public AttributeGraphic() {
        sculptor.parse("defaultAttribute");
        attribute = sculptor.carve();
        visibility = "";
        type = ContentType.Undefined;
        isIndicate = true;
    }

    public AttributeGraphic(String name) {
        sculptor.parse(name);
        attribute = sculptor.carve();
        isIndicate = true;
    }

    @Override
    public void setText(String name) {
        sculptor.parse(name);
        attribute = sculptor.carve();
    }

    @Override
    public String getText() {
        return attribute.toString();
    }
}
