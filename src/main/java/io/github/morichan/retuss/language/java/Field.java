package io.github.morichan.retuss.language.java;

/**
 * <p> Javaにおけるフィールドクラス </p>
 */
public class Field {

    private Type type = new Type();
    private String name = "field";
    private String variable;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(type);
        sb.append(" ");

        sb.append(name);

        sb.append(";");

        return sb.toString();
    }
}
