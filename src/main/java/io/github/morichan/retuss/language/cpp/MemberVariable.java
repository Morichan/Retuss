package io.github.morichan.retuss.language.cpp;

/**
 * <p> Javaにおけるフィールドクラス </p>
 */
public class MemberVariable {

    private AccessSpecifier accessSpecifier;
    private Type type;
    private String name;
    private Value value;
    private boolean flagString=false;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     アクセス修飾子は {@link AccessSpecifier#Private} 、 型は {@code int} 、フィールド名は {@code field} として設定します。
     * </p>
     */
    public MemberVariable() {
        accessSpecifier = AccessSpecifier.Private;
        type = new Type("int");
        name = "field";
    }

    /**
     * <p> 型とフィールド名を設定するコンストラクタ </p>
     *
     * @param type 型 <br> {@link #setType(Type)} を利用
     * @param name フィールド名 <br> {@link #setName(String)} を利用
     */
    public MemberVariable(Type type, String name) {
        accessSpecifier = AccessSpecifier.Private;
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
    public MemberVariable(Type type, String name, String defaultValue) {
        accessSpecifier = AccessSpecifier.Private;
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
     * @param accessSpecifier アクセス修飾子 <br> {@code null} 不可
     */
    public void setAccessSpecifier(AccessSpecifier accessSpecifier) {
        if (accessSpecifier == null) throw new IllegalArgumentException();
        this.accessSpecifier = accessSpecifier;
    }

    /**
     * <p> アクセス修飾子を取得します </p>
     *
     * @return アクセス修飾子 <br> {@code null} の可能性なし
     */
    public AccessSpecifier getAccessSpecifier() {
        return accessSpecifier;
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
        if(type.getTypeName().equals("String")){
            type.setTypesName("std::string");
            flagString=true;
        }
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


    public boolean getFlagString(){return  flagString;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

       // if (!accessSpecifier.is(AccessSpecifier.Package.toString())) {
//            sb.append(accessSpecifier);
//            sb.append(" ");
      //  }

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
