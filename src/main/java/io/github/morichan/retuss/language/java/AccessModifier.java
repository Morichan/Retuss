package io.github.morichan.retuss.language.java;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> Javaにおけるアクセス修飾子クラス </p>
 */
public enum AccessModifier {

    /**
     * <p> パブリックアクセス修飾子 </p>
     *
     * <p>
     *     {@link #is(String)} をオーバーライドしています。
     *     {@code "public"} を入力した場合のみ真を返します。
     * </p>
     */
    Public {
        @Override
        public boolean is(String accessModifier) {
            boolean isPublic = false;
            if (accessModifier.equals("public")) isPublic = true;
            return isPublic;
        }

        @Override
        public String toString() {
            return "public";
        }
    },

    /**
     * <p> プライベートアクセス修飾子 </p>
     *
     * <p>
     *     {@link #is(String)} をオーバーライドしています。
     *     {@code "private"} を入力した場合のみ真を返します。
     * </p>
     */
    Private {
        @Override
        public boolean is(String accessModifier) {
            boolean isPrivate = false;
            if (accessModifier.equals("private")) isPrivate = true;
            return isPrivate;
        }

        @Override
        public String toString() {
            return "private";
        }
    },

    /**
     * <p> パッケージアクセス修飾子 </p>
     *
     * <p>
     *     {@link #is(String)} をオーバーライドしています。
     *     {@code ""} （空文字）を入力した場合のみ真を返します。
     * </p>
     */
    Package {
        @Override
        public boolean is(String accessModifier) {
            boolean isPackage = false;
            if (accessModifier.isEmpty()) isPackage = true;
            return isPackage;
        }

        @Override
        public String toString() {
            return "";
        }
    },

    /**
     * <p> プロテクテッドアクセス修飾子 </p>
     *
     * <p>
     *     {@link #is(String)} をオーバーライドしています。
     *     {@code "protected"} を入力した場合のみ真を返します。
     * </p>
     */
    Protected {
        @Override
        public boolean is(String accessModifier) {
            boolean isProtected = false;
            if (accessModifier.equals("protected")) isProtected = true;
            return isProtected;
        }

        @Override
        public String toString() {
            return "protected";
        }
    },
    ;

    final static private Map<String, AccessModifier> string2access = new HashMap<>() {{
        put("public", Public);
        put("private", Private);
        put("", Package);
        put("protected", Protected);
    }};



    /**
     * <p> インスタンス状態が最初に設定した状態であれば真を返す真偽値判定を行う抽象メソッドです。 </p>
     *
     * <p>
     *     インスタンス状態であるかどうかを文字列で判定できます。
     *     入力判定を行う文字列は {@code "public", "private", "", "protected"} の4種類です。
     * </p>
     *
     * @param accessModifier インスタンス状態であるかどうかの文字列
     * @return インスタンス状態が判定値と等しい場合は真を返す真偽値
     */
    abstract public boolean is(String accessModifier);

    /**
     * <p> インスタンス状態を文字列から選択します。 </p>
     *
     * <p>
     *     インスタンス状態を文字列 {@code "public", "private", "", "protected"} から選択し、そのインスタンス状態を返します。
     *     上記の文字列以外または {@code null} を入力すると {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param accessModifierText インスタンス状態の文字列
     * @return 受取った文字列と等しいインスタンス状態
     */
    static public AccessModifier choose(String accessModifierText) {
        if (accessModifierText == null || !string2access.containsKey(accessModifierText)) throw new IllegalArgumentException();

        return string2access.get(accessModifierText);
    }
}
