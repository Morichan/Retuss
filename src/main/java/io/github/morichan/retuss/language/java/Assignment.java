package io.github.morichan.retuss.language.java;

public class Assignment implements BlockStatement {

    private Type type;
    private String name;
    private ArrayLength arrayLength;
    private Value value;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     型は {@code int} 、ローカル変数名は {@code field} として設定します。
     * </p>
     */
    public Assignment() {
        type = new Type("int");
        name = "localValue";
        value = new Value("0");
    }

    /**
     * <p> ローカル変数名を設定するコンストラクタ </p>
     *
     * @param name ローカル変数名 <br> {@link #setName(String)} を利用
     */
    public Assignment(String name) {
        type = new Type("int");
        setName(name);
    }

    /**
     * <p> ローカル変数名と既定値を設定するコンストラクタ </p>
     *
     * @param name ローカル変数名 <br> {@link #setName(String)} を利用
     * @param defaultValue 既定値 <br> {@link #setValue(String)} を利用
     */
    public Assignment(String name, String defaultValue) {
        type = new Type("int");
        setName(name);
        setValue(defaultValue);
    }

    /**
     * <p> ローカル変数名を設定します </p>
     *
     * <p>
     *     {@code null} または空文字またはローカル変数名に適さない文字列（文字列内に空文字が入っているなど）を設定しようとした場合は、
     *     {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param name ローカル変数名 <br> {@code null} 不可
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }

    /**
     * <p> ローカル変数名を取得します </p>
     *
     * @return ローカル変数名 <br> {@code null} および空文字の可能性なし
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
