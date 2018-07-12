package io.github.morichan.retuss.language.java;

/**
 * <p> Javaにおけるクラスに関するクラス </p>
 */
public class Class {
    private String code = "class ClassName {\n}\n";

    private String name = "ClassName";
    private Class extendsClass;

    public void manufacture() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ");
        sb.append(name);

        if (extendsClass != null) {
            sb.append(" extends ");
            sb.append(extendsClass.getName());
        }

        sb.append(" {\n");

        sb.append("}\n");

        code = sb.toString();
    }

    /**
     * <p> クラス名を設定します </p>
     *
     * <p>
     *     空文字を設定した場合は {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param name クラス名
     */
    public void setName(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }

    /**
     * <p> クラス名を取得します </p>
     *
     * @return クラス名
     */
    public String getName() {
        return name;
    }

    /**
     * <p> 継承クラスを設定します </p>
     *
     * @param extendsClass 継承クラス <br> {@code null} 可
     */
    public void setExtendsClass(Class extendsClass) {
        this.extendsClass = extendsClass;
    }

    /**
     * <p> 継承クラス名を取得します </p>
     *
     * <p>
     *     継承クラスが存在しない場合は空文字を返します。
     * </p>
     *
     * @return 継承クラス名 <br> 空文字の可能性あり
     */
    public String getExtendsClassName() {
        if (extendsClass == null) return "";
        return extendsClass.getName();
    }

    @Override
    public String toString() {
        return code;
    }
}
