package io.github.morichan.retuss.language.java;

/**
 * <p> Javaにおけるフィールドクラス </p>
 */
public class Field {

    private Type type = new Type();
    private String name = "field";
    private String value;

    /**
     * <p> フィールド名を設定します </p>
     *
     * @param name フィールド名 <br> {@code null} 不可
     */
    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
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
