package io.github.morichan.retuss.language.java;

/**
 * <p> Javaにおけるフィールドクラス </p>
 */
public class Field {

    private AccessModifier accessModifier;
    private Type type;
    private String name;
    private ArrayLength arrayLength;
    private Value value;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     アクセス修飾子は {@link AccessModifier#Private} 、 型は {@code int} 、フィールド名は {@code field} として設定します。
     * </p>
     */
    public Field() {
        accessModifier = AccessModifier.Private;
        type = new Type("int");
        name = "field";
    }

    /**
     * <p> 型とフィールド名を設定するコンストラクタ </p>
     *
     * @param type 型 <br> {@link #setType(Type)} を利用
     * @param name フィールド名 <br> {@link #setName(String)} を利用
     */
    public Field(Type type, String name) {
        accessModifier = AccessModifier.Private;
        setType(type);
        setName(name);
    }

    /**
     * <p> 型とフィールド名と既定値を設定するコンストラクタ </p>
     *
     * @param type 型 <br> {@link #setType(Type)} を利用
     * @param name フィールド名 <br> {@link #setName(String)} を利用
     * @param defaultValue 既定値 <br> {@link #setValue(String)} を利用
     */
    public Field(Type type, String name, String defaultValue) {
        accessModifier = AccessModifier.Private;
        setType(type);
        setName(name);
        setValue(defaultValue);
    }

    /**
     * <p> アクセス修飾子を設定します </p>
     *
     * <p>
     *     {@code null} を設定しようとすると {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param accessModifier アクセス修飾子 <br> {@code null} 不可
     */
    public void setAccessModifier(AccessModifier accessModifier) {
        if (accessModifier == null) throw new IllegalArgumentException();
        this.accessModifier = accessModifier;
    }

    /**
     * <p> アクセス修飾子を取得します </p>
     *
     * @return アクセス修飾子 <br> {@code null} の可能性なし
     */
    public AccessModifier getAccessModifier() {
        return accessModifier;
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

    public void setArrayLength(ArrayLength arrayLength) {
        this.arrayLength = arrayLength;
        if (arrayLength == null) return;

        createArrayValue();
    }

    public ArrayLength getArrayLength() {
        return arrayLength;
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
        if (value == null) {
            this.value = null;
        } else {
            this.value = new Value(value);
        }
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
    public Value getValue() {
        return value;
    }

    private void createArrayValue() {
        if (type == null || arrayLength == null || !arrayLength.isEnabled()) return;

        value = new Value("new " + type + arrayLength);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (!accessModifier.is(AccessModifier.Package.toString())) {
            sb.append(accessModifier);
            sb.append(" ");
        }

        sb.append(type);
        sb.append(" ");

        sb.append(name);

        if (arrayLength != null) {
            sb.append("[]");
        }

        if (value != null) {
            sb.append(" = ");
            sb.append(value);
        }

        sb.append(";");

        return sb.toString();
    }
}
