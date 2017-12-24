package translator;

import parser.java9.Java9BaseListener;
import parser.java9.Java9Parser;

/**
 * Java9のソースコードのパーサを利用したコンテキストの抽出クラス
 *
 * ANTLRのバージョンおよび文法ファイルに依存する。
 *
 * @deprecated
 * Java9.g4が不完全なため、使用しない。
 * {@link JavaEvalListener}の利用を推奨する。
 */
@Deprecated
public class Java9EvalListener extends Java9BaseListener {
}
