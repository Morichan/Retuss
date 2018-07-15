package io.github.morichan.retuss.language.java;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Javaにおけるクラスに関するクラス </p>
 */
public class Class {

    private String name;
    private Class extendsClass;
    private List<Field> fields;
    private List<Method> methods;

    public Class() {
        name = "ClassName";
        fields = new ArrayList<>();
        methods = new ArrayList<>();
    }

    public Class(String className) {
        name = className;
        fields = new ArrayList<>();
        methods = new ArrayList<>();
    }

    /**
     * <p> クラス名を設定します </p>
     *
     * <p>
     * {@code null} または空文字を設定した場合は {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param name クラス名 <br> {@code null} および空文字不可
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }

    /**
     * <p> クラス名を取得します </p>
     *
     * @return クラス名 <br> {@code null} および空文字の可能性なし
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
     * 継承クラスが存在しない場合は {@code null} を返します。
     * </p>
     *
     * @return 継承クラス名 <br> {@code null} の可能性あり
     */
    public String getExtendsClassName() {
        if (extendsClass == null) return null;
        return extendsClass.getName();
    }

    /**
     * <p> フィールドのリストにフィールドを追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param field フィールド <br> {@code null} 無視
     */
    public void addField(Field field) {
        if (field != null) fields.add(field);
    }

    /**
     * <p> フィールドのリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param fields フィールドのリスト
     */
    public void setFields(List<Field> fields) {
        if (fields != null) for (Field field : fields) addField(field);
        else this.fields.clear();
    }

    /**
     * <p> フィールドのリストを取得します </p>
     *
     * @return フィールドのリスト <br> 要素数0の可能性あり
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * <p> フィールドのリストを空にします </p>
     *
     * <p>
     * {@link #setFields(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyFields() {
        setFields(null);
    }

    /**
     * <p> メソッドのリストにメソッドを追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param method メソッド <br> {@code null} 無視
     */
    public void addMethod(Method method) {
        if (method != null) methods.add(method);
    }

    /**
     * <p> メソッドのリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param methods メソッドのリスト
     */
    public void setMethod(List<Method> methods) {
        if (methods != null) for (Method method : methods) addMethod(method);
        else this.methods.clear();
    }

    /**
     * <p> メソッドのリストを取得します </p>
     *
     * @return メソッドのリスト <br> 要素数0の可能性あり
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * <p> メソッドのリストを空にします </p>
     *
     * <p>
     * {@link #setMethod(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyMethods() {
        setMethod(null);
    }


    /**
     * <p> ソースコードの文字列を組立てます </p>
     *
     * @return ソースコードの文字列
     */
    private String manufacture() {
        StringBuilder sb = new StringBuilder();

        sb.append("class ");
        sb.append(name);

        if (extendsClass != null) {
            sb.append(" extends ");
            sb.append(extendsClass.getName());
        }

        sb.append(" {\n");

        for (Field field : fields) {
            sb.append("    ");
            sb.append(field);
            sb.append("\n");
        }

        if (!fields.isEmpty() && !methods.isEmpty()) sb.append("\n");

        for (Method method : methods) {
            sb.append("    ");
            sb.append(method);
            sb.append("\n");
        }

        sb.append("}\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return manufacture();
    }
}
