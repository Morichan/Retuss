package io.github.morichan.retuss.language.java;

/**
 * <p> Javaにおける配列の長さを表現するクラス </p>
 */
public class ArrayLength {

    private int length;
    private boolean isEnabled;

    public ArrayLength() {
        length = 0;
        isEnabled = false;
    }

    public ArrayLength(int length) {
        this.length = length;
        isEnabled = true;
    }

    public void setLength(int length) {
        this.length = length;
        isEnabled = true;
    }

    public int getLength() {
        return length;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {

        if (!isEnabled) {
            return "";
        } else if (length != 0) {
            return "[" + length + "]";
        } else {
            return "[]";
        }
    }
}
