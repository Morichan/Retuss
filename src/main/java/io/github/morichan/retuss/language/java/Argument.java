package io.github.morichan.retuss.language.java;

/**
 * <p> Javaにおけるメソッドの引数クラス </p>
 */
public class Argument {

    private Type type;
    private String name;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     型は {@code int} 、引数名は {@code argument} として設定します。
     * </p>
     */
    public Argument() {
        type = new Type("int");
        name = "argument";
    }

    /**
     * <p> 型と引数名を設定するコンストラクタ </p>
     *
     * @param type 型 <br> {@link #setType(Type)} を利用
     * @param name 引数名 <br> {@link #setName(String)} を利用
     */
    public Argument(Type type, String name) {
        setType(type);
        setName(name);
    }

    /**
     * <p> 引数名を設定します </p>
     *
     * <p>
     *     {@code null} または空文字または引数名に適さない文字列（文字列内に空文字が入っているなど）を設定しようとした場合は、
     *     {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param name 引数名 <br> {@code null} 不可
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }

    /**
     * <p> 引数名を取得します </p>
     *
     * @return 引数名 <br> {@code null} および空文字の可能性なし
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(type);
        sb.append(" ");
        sb.append(name);

        return sb.toString();
    }
}
