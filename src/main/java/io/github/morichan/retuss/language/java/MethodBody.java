package io.github.morichan.retuss.language.java;

import java.util.ArrayList;
import java.util.List;

public class MethodBody {

    private List<BlockStatement> statements = new ArrayList<>();

    /**
     * <p> 命令文のリストにフィールドを追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param statement 命令文 <br> {@code null} 無視
     */
    public void addStatement(BlockStatement statement) {
        if (statement != null) statements.add(statement);
    }

    /**
     * <p> 命令文のリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param statements 命令文のリスト
     */
    public void setStatements(List<BlockStatement> statements) {
        if (statements != null) for (BlockStatement statement : statements) addStatement(statement);
        else this.statements.clear();
    }

    /**
     * <p> 命令文のリストを取得します </p>
     *
     * @return 命令文のリスト <br> 要素数0の可能性あり
     */
    public List<BlockStatement> getStatements() {
        return statements;
    }

    /**
     * <p> 命令文のリストを空にします </p>
     *
     * <p>
     * {@link #setStatements(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyStatements() {
        setStatements(null);
    }
}
