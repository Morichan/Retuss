package io.github.morichan.retuss.retuss;

/**
 * <p> クラスの属性に関するクラス </p>
 *
 * <p>
 *     このクラスは、クラス図におけるクラスの属性に関するクラスです。
 *     {@link ClassData} クラスを継承しています。
 * </p>
 */
public class Attribute extends ClassData {

    Attribute() {
        name = "";
        visibility = "";
        type = ContentType.Undefined;
        isIndicate = true;
    }

    Attribute(String name ) {
        this.name = name;
        isIndicate = true;
    }
}
