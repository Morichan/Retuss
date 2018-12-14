package io.github.morichan.retuss.language.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> Javaにおける値クラス </p>
 */
public class Value {

    private Pattern newString;
    private Pattern arrayString;

    private String name;
    private boolean isNewContext;
    private ArrayLength arrayLength;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     値の名前を {@code identifier} として設定します。
     * </p>
     */
    public Value() {
        newString = Pattern.compile("new ([a-zA-Z_][a-zA-Z0-9_]*)");
        arrayString = Pattern.compile("\\[([0-9]*)]");
        name = "identifier";
        isNewContext = false;
    }

    /**
     * <p> 値の名前を設定するコンストラクタ </p>
     *
     * <p>
     *     {@link #setName(String)} を利用しています。
     * </p>
     *
     * @param valueName 値の名前 <br> {@code null} および空文字不可（ {@link #setName(String)} を参照）
     */
    public Value(String valueName) {
        newString = Pattern.compile("new ([a-zA-Z_][a-zA-Z0-9_]*)");
        arrayString = Pattern.compile("\\[([0-9]*)]");
        setName(valueName);
    }
    /**
     * <p> 値の名前を設定します </p>
     *
     * <p>
     *     設定時に {@code new} 文か否かを判定し、出力時の判断材料とします。
     *     {@code null} および空文字を設定しようとすると {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param name 値の名前 <br> {@code null} および空文字不可
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();

        Matcher newMatcher = newString.matcher(name);
        Matcher arrayMatcher = arrayString.matcher(name);

        if (newMatcher.find()) {
            this.name = newMatcher.group(1);
            isNewContext = true;
        } else {
            this.name = name;
            isNewContext = false;
        }

        if (arrayMatcher.find()) {
            arrayLength = new ArrayLength(Integer.parseInt(arrayMatcher.group(1)));
            isNewContext = false;
        }
    }

    /**
     * <p> 値の名前を取得します </p>
     *
     * <p>
     *     {@link #setName(String)} 時に {@code new} 文を設定した場合、 {@code new} や括弧などを取り除いた名前を返します。
     * </p>
     *
     * @return 値の名前 <br> {@code null} および空文字の可能性なし
     */
    public String getName() {
        return name;
    }

    public ArrayLength getArrayLength() {
        return arrayLength;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (arrayLength != null) {
            sb.append("new ");
            sb.append(name);
            sb.append(arrayLength);
        } else if (isNewContext) {
            sb.append("new ");
            sb.append(name);
            sb.append("()");
        } else {
            sb.append(name);
        }

        return sb.toString();
    }
}
