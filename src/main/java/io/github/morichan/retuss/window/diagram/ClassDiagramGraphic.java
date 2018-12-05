package io.github.morichan.retuss.window.diagram;

/**
 * <p> クラス図グラフィックに関するクラス </p>
 *
 * <p>
 * このクラスは、クラス図における図としての形式に関するクラスです。
 * 次の属性を持ちます。
 * </p>
 *
 * <ul>
 * <li> 種類 </li>
 * <li> 可視性 </li>
 * <li> 表示しているか否か </li>
 * </ul>
 */
abstract public class ClassDiagramGraphic {

    /**
     * データの可視性
     */
    protected String visibility = "";

    /**
     * データの種類
     */
    private ContentType type = ContentType.Undefined;

    /**
     * データを図中に表示しているか否か
     */
    private boolean isIndicate = true;

    /**
     * データのテキストを入力する。
     *
     * @param text データの名前
     */
    abstract public void setText(String text);

    /**
     * データのテキストを取得する。
     *
     * @return データの名前 <br> 入力していない場合は空文字を返す。
     */
    abstract public String getText();

    /**
     * データを図中に表示しているか否かを設定する。
     *
     * @param isIndicate データの表示非表示の設定 <br> 真の場合は表示している。
     */
    public void setIndication(boolean isIndicate) {
        this.isIndicate = isIndicate;
    }

    /**
     * データを図中に表示しているか否かを取得する。
     *
     * @return データの表示非表示の設定 <br> 真の場合は表示している。
     */
    public boolean isIndicate() {
        return isIndicate;
    }

    /**
     * データの種類を設定する。
     *
     * @param type データの種類
     */
    public void setType(ContentType type) {
        this.type = type;
    }

    /**
     * データの種類を取得する。
     *
     * @return データの種類 <br> 何も設定していない場合は {@code ContentType.Undefined} を返す。
     */
    public ContentType getType() {
        return type;
    }
}
