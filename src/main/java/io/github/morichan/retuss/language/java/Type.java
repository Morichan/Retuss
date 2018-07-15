package io.github.morichan.retuss.language.java;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Javaにおける型クラス </p>
 */
public class Type {

    private List<String> types;
    private int arrangementCount = 0;
    private List<Type> dataTypes;

    public Type() {
        types = new ArrayList<>();
        dataTypes = new ArrayList<>();
    }

    public Type(String name) {
        types = new ArrayList<>();
        dataTypes = new ArrayList<>();
        types.add(name);
    }

    /**
     * <p> 型名を追加します </p>
     *
     * @param type 型名 <br> {@code null} 不可
     */
    public void addTypeName(String type) {
        types.add(type);
    }

    /**
     * <p> 型名をリセットします </p>
     */
    public void resetTypes() {
        types = new ArrayList<>();
    }

    /**
     * <p> 配列の次元数を設定します </p>
     *
     * @param count 配列の次元数
     */
    public void setArrangementCount(int count) {
        arrangementCount = count;
    }

    /**
     * <p> 型の引数を設定します </p>
     *
     * @param type 型の引数 <br> {@code null} 可
     */
    public void addDataType(Type type) {
        dataTypes.add(type);
    }

    /**
     * <p> 型の引数をリセットします </p>
     */
    public void resetDataTypes() {
        dataTypes = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (types.size() <= 0) {
            sb.append("int");
        } else if (dataTypes.size() <= 0) {
            sb.append(String.join(".", types));
        } else {
            List<String> data = new ArrayList<>();
            for(Type t : dataTypes) data.add(t.toString());
            sb.append(String.join(".", types));
            sb.append("<");
            sb.append(String.join(", ", data));
            sb.append(">");
        }

        for (int i = 0; i < arrangementCount; i++) sb.append("[]");

        return sb.toString();
    }
}
