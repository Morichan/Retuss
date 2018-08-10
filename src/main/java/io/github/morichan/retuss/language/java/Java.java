package io.github.morichan.retuss.language.java;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Javaクラス </p>
 */
public class Java {

    private List<Class> classes = new ArrayList<>();

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
