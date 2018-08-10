package io.github.morichan.retuss.language.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> クラス図におけるパッケージクラス </p>
 */
public class Package {

    private String name;
    private final String defaultName = "main";
    private List<Class> classes;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     パッケージ名は {@code main} として設定します。
     * </p>
     */
    public Package() {
        name = defaultName;
        classes = new ArrayList<>();
    }

    /**
     * <p> パッケージ名を設定するコンストラクタ </p>
     *
     * <p>
     *     {@link #setName(String)} を利用しています。
     * </p>
     *
     * @param name パッケージ名
     */
    public Package(String name) {
        setName(name);
        classes = new ArrayList<>();
    }

    /**
     * <p> パッケージ名を設定します </p>
     *
     * <p>
     *     {@code null} および空文字を設定する場合は、自動的にデフォルトパッケージ名として {@code main} を設定します。
     * </p>
     *
     * @param name パッケージ名 {@code null} および空文字は {@code "main"} を入力したのと同義
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) this.name = defaultName;
        else this.name = name;
    }

    /**
     * <p> パッケージ名を取得します </p>
     *
     * @return パッケージ名
     */
    public String getName() {
        return name;
    }

    /**
     * <p> クラスのリストにクラスを追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param javaClass クラス <br> {@code null} 無視
     */
    public void addClass(Class javaClass) {
        if (javaClass != null) classes.add(javaClass);
    }

    /**
     * <p> クラスのリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param classes クラスのリスト
     */
    public void setClasses(List<Class> classes) {
        if (classes != null) for (Class javaClass : classes) addClass(javaClass);
        else this.classes.clear();
    }

    /**
     * <p> クラスのリストを取得します </p>
     *
     * @return クラスのリスト <br> 要素数0の可能性あり
     */
    public List<Class> getClasses() {
        return classes;
    }

    /**
     * <p> クラスのリストを空にします </p>
     *
     * <p>
     * {@link #setClasses(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyClasses() {
        setClasses(null);
    }
}
