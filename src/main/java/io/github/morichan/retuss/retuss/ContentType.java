package io.github.morichan.retuss.retuss;

/**
 * <p> 内容の種類の列挙型 </p>
 *
 * <p>
 *     内容の種類については、クラス図におけるクラスやノート、クラス図のクラスにおける属性や操作、
 *     クラス図のクラスの属性における可視性やクラス図キャンバスにおける描画の有無などが存在する。
 * </p>
 */
public enum ContentType {
    Title,
    Class,
    Note,
    Attribute,
    Operation,
    Indication,
    Visibility,
    Abstraction,
    Static,
    Derived,
    Composition,
    Generalization,
    Undefined
}
