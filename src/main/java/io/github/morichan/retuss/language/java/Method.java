package io.github.morichan.retuss.language.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Method implements BlockStatement {

    private AccessModifier accessModifier;
    private boolean isAbstract = false;
    private boolean isOwned = false;
    private Type type;
    private String name;
    private List<Argument> arguments;
    private MethodBody methodBody;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     アクセス修飾子は {@link AccessModifier#Public} 、型は {@code void} 、メソッド名は {@code method} 、引数なしで設定します。
     * </p>
     */
    public Method() {
        accessModifier = AccessModifier.Public;
        type = new Type("void");
        name = "method";
        arguments = new ArrayList<>();
    }

    /**
     * <p> 型とメソッド名を設定するコンストラクタ </p>
     *
     * @param type 型 <br> {@link #setType(Type)} を利用します
     * @param name メソッド名 <br> {@link #setName(String)} を利用します
     */
    public Method(Type type, String name) {
        accessModifier = AccessModifier.Public;
        setType(type);
        setName(name);
        arguments = new ArrayList<>();
    }

    /**
     * <p> 型とメソッド名と引数を設定するコンストラクタ </p>
     *
     * @param type 型 <br> {@link #setType(Type)} を利用します
     * @param name メソッド名 <br> {@link #setName(String)} を利用します
     * @param arguments 複数の引数 <br> {@link #setArguments(List)} を利用します
     */
    public Method(Type type, String name, Argument... arguments) {
        accessModifier = AccessModifier.Public;
        setType(type);
        setName(name);
        this.arguments = new ArrayList<>();
        setArguments(new ArrayList<>(Arrays.asList(arguments)));
    }

    /**
     * <p> アクセス修飾子を設定します </p>
     *
     * <p>
     *     {@code null} を設定しようとすると {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param accessModifier アクセス修飾子 <br> {@code null} 不可
     */
    public void setAccessModifier(AccessModifier accessModifier) {
        if (accessModifier == null) throw new IllegalArgumentException();
        this.accessModifier = accessModifier;
    }

    /**
     * <p> アクセス修飾子を取得します </p>
     *
     * @return アクセス修飾子 <br> {@code null} の可能性なし
     */
    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    /**
     * <p> メソッド名を設定します </p>
     *
     * <p>
     *     {@code null} または空文字またはメソッド名に適さない文字列（文字列内に空文字が入っているなど）を設定しようとした場合は、
     *     {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param name メソッド名 <br> {@code null} 不可
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }

    /**
     * <p> メソッド名を取得します </p>
     *
     * @return メソッド名 <br> {@code null} および空文字の可能性なし
     */
    @Override
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
    @Override
    public Type getType() {
        return type;
    }

    /**
     * <p> 引数のリストにフィールドを追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param argument 引数 <br> {@code null} 無視
     */
    public void addArgument(Argument argument) {
        if (argument != null) arguments.add(argument);
    }

    /**
     * <p> 引数のリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param arguments 引数のリスト
     */
    public void setArguments(List<Argument> arguments) {
        if (arguments != null) for (Argument argument : arguments) addArgument(argument);
        else this.arguments.clear();
    }

    /**
     * <p> 引数のリストを取得します </p>
     *
     * @return 引数のリスト <br> 要素数0の可能性あり
     */
    public List<Argument> getArguments() {
        return arguments;
    }

    /**
     * <p> 引数のリストを空にします </p>
     *
     * <p>
     * {@link #setArguments(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyArguments() {
        setArguments(null);
    }

    public void setMethodBody(MethodBody methodBody) {
        this.methodBody = methodBody;
    }

    public MethodBody getMethodBody() {
        return methodBody;
    }

    public void setAbstract(boolean abstractFlag) {
        isAbstract = abstractFlag;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    public boolean isOwned() {
        return isOwned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (isAbstract) sb.append("abstract ");

        if (!accessModifier.is(AccessModifier.Package.toString())) {
            sb.append(accessModifier);
            sb.append(" ");
        }

        sb.append(type);
        sb.append(" ");

        sb.append(name);

        sb.append("(");

        if (!arguments.isEmpty()) {
            List<String> args = new ArrayList<>();
            for (Argument arg : arguments) args.add(arg.toString());
            sb.append(String.join(", ", args));
        }

        sb.append(")");

        if (isAbstract) sb.append(";");
        else sb.append(" {}");

        return sb.toString();
    }
}
