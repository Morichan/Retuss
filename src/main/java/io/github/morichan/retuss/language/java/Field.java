package io.github.morichan.retuss.language.java;

/**
 * <p> Javaにおけるフィールドクラス </p>
 */
public class Field {

    private Type type;
    private String name;
    private String value;

    public Field() {
        type = new Type("int");
        name = "field";
    }

    public Field(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Field(Type type, String name, String defaultValue) {
        this.type = type;
        this.name = name;
        value = defaultValue;
    }

    /**
     * <p> フィールド名を設定します </p>
     *
     * <p>
     *     {@code null} または空文字またはフィールド名に適さない文字列（文字列内に空文字が入っているなど）を設定しようとした場合は、
     *     {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param name フィールド名 <br> {@code null} 不可
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }

    /**
     * <p> フィールド名を取得します </p>
     *
     * @return フィールド名 <br> {@code null} および空文字の可能性なし
     */
    public String getName() {
        return name;
    }

    /**
     * <p> 型を設定します </p>
     *
     * <p>
     *     {@code null} を設定しようとした場合は {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param type 型 <br> {@code null} 不可
     */
    public void setType(Type type) {
        if (type == null) throw new IllegalArgumentException();
        this.type = type;
    }

    /**
     * <p> 型を取得します </p>
     *
     * @return 型 <br> {@code null} の可能性なし
     */
    public Type getType() {
        return type;
    }

    /**
     * <p> 既定値を設定します </p>
     *
     * <p>
     *     既定値が存在しない場合は {@code null} を設定してください。
     * </p>
     *
     * @param value 既定値 <br> {@code null} 可
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p> 既定値を取得します </p>
     *
     * <p>
     *     既定値が存在しない場合は {@code null} を返します。
     * </p>
     *
     * @return 既定値 <br> {@code null} の可能性あり
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(type);
        sb.append(" ");

        sb.append(name);

        if (value != null) {
            sb.append(" = ");
            sb.append(value);
        }

        sb.append(";");

        return sb.toString();
    }
}
