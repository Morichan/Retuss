package io.github.morichan.retuss.language.cpp;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Javaにおけるクラスに関するクラス </p>
 */
public class Class {

    private String name;
    private Class extendsClass;
    private List<MemberVariable> memberVariables;
    private List<MemberFunction> memberFunctions;

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     クラス名は {@code ClassName} として設定します。
     * </p>
     */
    public Class() {
        name = "ClassName";
        memberVariables = new ArrayList<>();
        memberFunctions = new ArrayList<>();
    }

    /**
     * <p> クラス名を設定するコンストラクタ </p>
     *
     * @param className クラス名 <br> {@link #setName(String)} を利用します
     */
    public Class(String className) {
        setName(className);
        memberVariables = new ArrayList<>();
        memberFunctions = new ArrayList<>();
    }

    /**
     * <p> クラス名を設定します </p>
     *
     * <p>
     * {@code null} または空文字を設定した場合は {@link IllegalArgumentException} を投げます。
     * </p>
     *
     * @param name クラス名 <br> {@code null} および空文字不可
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }

    /**
     * <p> クラス名を取得します </p>
     *
     * @return クラス名 <br> {@code null} および空文字の可能性なし
     */
    public String getName() {
        return name;
    }

    /**
     * <p> 継承クラスを設定します </p>
     *
     * @param extendsClass 継承クラス <br> {@code null} 可
     */
    public void setExtendsClass(Class extendsClass) {
        this.extendsClass = extendsClass;
    }

    /**
     * <p> 継承クラス名を取得します </p>
     *
     * <p>
     * 継承クラスが存在しない場合は {@code null} を返します。
     * </p>
     *
     * @return 継承クラス名 <br> {@code null} の可能性あり
     */
    public String getExtendsClassName() {
        if (extendsClass == null) return null;
        return extendsClass.getName();
    }

    /**
     * <p> フィールドのリストにフィールドを追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param memberVariable フィールド <br> {@code null} 無視
     */
    public void addMemberVariable(MemberVariable memberVariable) {
        if (memberVariable != null) memberVariables.add(memberVariable);
    }

    /**
     * <p> フィールドのリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param memberVariables フィールドのリスト
     */
    public void setMemberVariables(List<MemberVariable> memberVariables) {
        if (memberVariables != null) for (MemberVariable memberVariable : memberVariables) addMemberVariable(memberVariable);
        else this.memberVariables.clear();
    }

    /**
     * <p> フィールドのリストを取得します </p>
     *
     * @return フィールドのリスト <br> 要素数0の可能性あり
     */
    public List<MemberVariable> getMemberVariables() {
        return memberVariables;
    }

    /**
     * <p> フィールドのリストを空にします </p>
     *
     * <p>
     * {@link #setMemberVariables(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyMemberVariables() {
        setMemberVariables(null);
    }

    /**
     * <p> メソッドのリストにメソッドを追加します </p>
     *
     * <p>
     * {@code null} を追加しようとしても反映しません。
     * </p>
     *
     * @param memberFunction メソッド <br> {@code null} 無視
     */
    public void addMemberFunction(MemberFunction memberFunction) {
        if (memberFunction != null) memberFunctions.add(memberFunction);
    }

    /**
     * <p> メソッドのリストを設定します </p>
     *
     * <p>
     * リスト内に {@code null} を含んでいた場合はその要素を無視します。
     * また、 {@code null} を設定しようとした場合はリストを空にします。
     * </p>
     *
     * @param memberFunctions メソッドのリスト
     */
    public void setMemberFunction(List<MemberFunction> memberFunctions) {
        if (memberFunctions != null) for (MemberFunction memberFunction : memberFunctions) addMemberFunction(memberFunction);
        else this.memberFunctions.clear();
    }

    /**
     * <p> メソッドのリストを取得します </p>
     *
     * @return メソッドのリスト <br> 要素数0の可能性あり
     */
    public List<MemberFunction> getMemberFunctions() {
        return memberFunctions;
    }

    /**
     * <p> メソッドのリストを空にします </p>
     *
     * <p>
     * {@link #setMemberFunction(List)} に{@code null} を設定しています。
     * </p>
     */
    public void emptyMemberFunctions() {
        setMemberFunction(null);
    }


    /**
     * <p> ソースコードの文字列を組立てます </p>
     *
     * @return ソースコードの文字列
     */
    private String manufacture() {
        StringBuilder sb = new StringBuilder();
        boolean flagProtected =false;
boolean flagString=false;
        for(MemberVariable memberVariable : memberVariables){
            if(memberVariable.getFlagString()== true){
               flagString= true;
            }
        }
if(flagString == true){
            sb.append("#include <string>");
    sb.append(" \n");
        }

        sb.append("class ");
        sb.append(name);

        if (extendsClass != null) {
            sb.append(" : public ");
            sb.append(extendsClass.getName());
        }

        sb.append(" {\n");
        if (memberVariables.size() != 0  || memberFunctions.size() != 0) {
            sb.append("private:");
            sb.append("\n");
        }
        for (MemberVariable memberVariable : memberVariables) {
            if(memberVariable.getAccessSpecifier() == AccessSpecifier.Private) {
                sb.append("    ");
                sb.append(memberVariable);
                sb.append("\n");
            }
        }

        for (MemberFunction memberFunction : memberFunctions) {
            if (memberFunction.getAccessSpecifier() == AccessSpecifier.Private) {
                sb.append("    ");
                sb.append(memberFunction);
                sb.append("\n");
            }
        }

        if (memberVariables.size() != 0 ||  memberFunctions.size() != 0) {
            sb.append("public:");
            sb.append("\n");
        }
        for (MemberVariable memberVariable : memberVariables) {
            if(memberVariable.getAccessSpecifier() == AccessSpecifier.Public) {
                sb.append("    ");
                sb.append(memberVariable);
                sb.append("\n");
            }
        }

        for (MemberFunction memberFunction : memberFunctions) {
            if (memberFunction.getAccessSpecifier() == AccessSpecifier.Public) {
                sb.append("    ");
                sb.append(memberFunction);
                sb.append("\n");
            }
        }


        for (MemberVariable memberVariable : memberVariables) {
//            boolean flag =false;
        if(memberVariable.getAccessSpecifier() == AccessSpecifier.Protected) {
            if(flagProtected ==false){
                sb.append("protected:");
                sb.append("\n");
            }
            flagProtected=true;
            sb.append("    ");
            sb.append(memberVariable);
            sb.append("\n");
        }
        }

        for (MemberFunction memberFunction : memberFunctions) {
//            boolean flag =false;
            if(memberFunction.getAccessSpecifier() == AccessSpecifier.Protected) {
                if(flagProtected ==false){
                    sb.append("protected:");
                    sb.append("\n");
                }
                flagProtected=true;
                sb.append("    ");
                sb.append(memberFunction);
                sb.append("\n");
            }
        }


        if (!memberVariables.isEmpty() && !memberFunctions.isEmpty()) sb.append("\n");

//        for (MemberFunction memberFunction : memberFunctions) {
//            sb.append("    ");
//            sb.append(memberFunction);
//            sb.append("\n");
//        }

        sb.append("};\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return manufacture();
    }

    public  String cppFile_toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(" \n");
        sb.append(" \n");
        sb.append(" \n");

        for (MemberFunction memberFunction : memberFunctions) {
            sb.append(" \n");
            sb.append(name);
            sb.append("::");
            sb.append(memberFunction.getName());
            sb.append("(){");
            sb.append(" \n");
            sb.append("}\n");
        }

        return sb.toString();
    }
}
