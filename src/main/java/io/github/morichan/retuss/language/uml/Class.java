package io.github.morichan.retuss.language.uml;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> クラス図のクラスに関するクラス </p>
 */
public class Class {

    private String name;
    private Class generalizationClass;
    private List<Attribute> attributes;
    private List<Operation> operations;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     クラス名は {@code ClassName} として設定します。
     * </p>
     */
    public Class() {
        setName("ClassName");
        attributes = new ArrayList<>();
        operations = new ArrayList<>();
    }

    /**
     * <p> クラス名を設定するコンストラクタ </p>
     *
     * @param className クラス名 <br> {@link #setName(String)} を利用します
     */
    public Class(String className) {
        setName(className);
        attributes = new ArrayList<>();
        operations = new ArrayList<>();
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
     * <p> 汎化クラスを設定します </p>
     *
     * <p>
     *     {@code null} は汎化クラス無しとして定義します。
     * </p>
     *
     * @param generalizationClass 汎化クラス <br> {@code null} 可
     */
    public void setGeneralizationClass(Class generalizationClass) {
        this.generalizationClass = generalizationClass;
    }

    /**
     * <p> 汎化クラスを取得します </p>
     *
     * <p>
     *     汎化クラスが存在しない場合は {@code null} を返します。
     * </p>
     *
     * @return 汎化クラス <br> {@code null} の可能性あり
     */
    public Class getGeneralizationClass() {
        return generalizationClass;
    }

    /**
     * <p> 属性のリストに属性を追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param attribute 属性 <br> {@code null} 無視
     */
    public void addAttribute(Attribute attribute) {
        if (attribute != null) attributes.add(attribute);
    }

    /**
     * <p> 属性のリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param attributes 属性のリスト
     */
    public void setAttributes(List<Attribute> attributes) {
        if (attributes != null) for (Attribute attribute : attributes) addAttribute(attribute);
        else this.attributes.clear();
    }

    /**
     * <p> 属性のリストを取得します </p>
     *
     * @return 属性のリスト <br> 要素数0の可能性あり
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * <p> 属性のリストを空にします </p>
     *
     * <p>
     * {@link #setAttributes(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyAttribute() {
        setAttributes(null);
    }

    /**
     * <p> 操作のリストに属性を追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param operation 操作 <br> {@code null} 無視
     */
    public void addOperation(Operation operation) {
        if (operation != null) operations.add(operation);
    }

    /**
     * <p> 操作のリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param operations 操作のリスト
     */
    public void setOperations(List<Operation> operations) {
        if (operations != null) for (Operation operation : operations) addOperation(operation);
        else this.operations.clear();
    }

    /**
     * <p> 操作のリストを取得します </p>
     *
     * @return 操作のリスト <br> 要素数0の可能性あり
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     * <p> 操作のリストを空にします </p>
     *
     * <p>
     * {@link #setOperations(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyOperation() {
        setOperations(null);
    }
}
