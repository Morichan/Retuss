|Main|Develop|
|:--:|:--:|
|[![Build Status](https://travis-ci.org/Morichan/Retuss.svg?branch=master)](https://travis-ci.org/Morichan/Retuss)|[![Build Status](https://travis-ci.org/Morichan/Retuss.svg?branch=develop)](https://travis-ci.org/Morichan/Retuss)|
|[![codecov](https://codecov.io/gh/Morichan/Retuss/branch/master/graph/badge.svg)](https://codecov.io/gh/Morichan/Retuss)|[![codecov](https://codecov.io/gh/Morichan/Retuss/branch/develop/graph/badge.svg)](https://codecov.io/gh/Morichan/Retuss)|
|![GitHub last commit (master)](https://img.shields.io/github/last-commit/Morichan/Retuss/master.svg)|![GitHub last commit (develop)](https://img.shields.io/github/last-commit/Morichan/Retuss/develop.svg)|

[![license](https://img.shields.io/github/license/Morichan/Retuss.svg)](LICENSE)

[![Java version](https://img.shields.io/badge/java-11+-4c7e9f.svg)](https://www.java.com/en/)
[![JUnit version](https://img.shields.io/badge/junit-5+-dc524a.svg)](https://junit.org/junit5/)
[![Gradle version](https://img.shields.io/badge/gradle-5.4+-007042.svg)](https://gradle.org/docs/)
[![ANTLR version](https://img.shields.io/badge/antlr-4+-ec312e.svg)](http://www.antlr.org/)
[![ANTLR version](https://img.shields.io/badge/fescue-2+-00dc00.svg)](https://github.com/Morichan/fescue/)

[![GitHub tag](https://img.shields.io/github/tag/Morichan/Retuss.svg)](https://github.com/Morichan/Retuss/tags)
[![GitHub release](https://img.shields.io/github/release/Morichan/Retuss/all.svg)](https://github.com/Morichan/Retuss/releases)
[![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed-raw/Morichan/Retuss.svg)](https://github.com/Morichan/Retuss/pulls?q=is%3Apr+is%3Aclosed)



# RETUSS

__RETUSS__ (Real-time Ensure Traceability between UML and Source-code System) は、UMLとソースコード間のトレーサビリティをリアルタイムに維持するツールです。

現在、次に示す項目に対応しています。

* UML
    * クラス図
    * シーケンス図
* ソースコード
    * Java
    * C++（シーケンス図には未対応）



# 開発環境

* Windows 10 Pro (64 bit) 1803
* IntelliJ IDEA 2019.1 (Community Edition)
* Java 11
* Gradle 5.4



# 開発環境の構築

手順の概要を、次に示します。

1. OpenJDK11のインストール
1. Gradleのインストール
1. IntelliJのインストール
1. Gitコマンドのインストール
1. GitHub上のRETUSSのフォークおよびクローン
1. IntelliJの既存システム展開でRETUSSのbuild.gradleの選択
1. ビルドコマンドの作成

詳細な説明は、RETUSSの開発環境構築を説明した[Wiki](https://github.com/Morichan/Retuss/wiki/RETUSSの開発環境構築)をご覧ください。



# 構造の説明

RETUSSは、次の5つの構造を持ちます。

* 表示部
* 対応部
* 記述部
* 変換部
* 保管部

各項目と、対応するファイルを説明します。


## 表示部

ユーザにメインウィンドウとソースコードウィンドウを表示する機能を提供します。

というとカッコイイですが、実際は単なるGUI画面です。
`src/main/resources` ディレクトリ下の `retussMain.fxml` ファイルおよび `retussCode.fxml` に該当します。


## 対応部

表示部が呼出す命令をまとめて保持しており、表示部のイベントハンドラと対応した処理を提供します。

`src/main/java/io/github/morichan/retuss/window` ディレクトリ下の `MainController.java` ファイルおよび `CodeController.java` ファイルに該当します。


## 記述部

クラス図、シーケンス図、およびソースコードの記述に必要となる処理を提供します。

上記のファイル以外のほぼ全てに該当しますが、特に該当するのは、 `src/main/java/io/github/morichan/retuss/window` ディレクトリ下の `ClassDiagramDrawer.java` ファイルおよび `SequenceDiagramDrawer.java` ファイルです。


## 変換部

クラス図、シーケンス図、またはソースコードの記述機能の実行後、他のクラス図、シーケンス図、およびソースコードに変換する処理を提供します。

これと言って対応するファイルは存在せず、各メソッド内に点在しています。
全体的に利用しているのは、 `src/main/java/io/github/morichan/retuss/translator` パッケージです。

### クラス図からシーケンス図

ありません。

基本的に、クラス図からソースコードに変換するため、ソースコードからシーケンス図に変換するだけです。
結果的にはクラス図からシーケンス図に変換していますが、そもそもクラス図に存在しない振舞いをシーケンス図で表しているため、変換できるところがありません。

### クラス図からソースコード

基本的に `src/main/java/io/github/morichan/retuss/window/ClassDiagramDrawer.java` ファイルです。

### シーケンス図からクラス図

基本的に `src/main/java/io/github/morichan/retuss/window/SequenceDiagramDrawer.java` ファイルです。

### シーケンス図からソースコード

ありません。

シーケンス図の記述機能がほぼ存在しないため、この仕組み自体も機能していません。
唯一の機能であるシーケンス図の削除は、クラス図の構造レベルを変更するため、シーケンス図 -> クラス図 -> ソースコードの順に変換します。

### ソースコードからクラス図

基本的に `src/main/java/io/github/morichan/retuss/window/CodeController.java` ファイルです。

### ソースコードからシーケンス図

基本的に `src/main/java/io/github/morichan/retuss/window/CodeController.java` ファイルです。


## 保管部

「クラス図」情報、「シーケンス図」情報、および「ソースコード」情報を内部データとして保持、また、他の部にデータの提供をします。

というと、何か仕事をしているみたいですが、そんなことはありません。
結局は情報という名の内部データなので、各地に点在しています。

多くは、 `src/main/java/io/github/morichan/retuss/language` パッケージです。



# 将来の展望

このRETUSSがどのようになっていくと良いのか、開発者の主観で書いていきます。


## やらなければならないこと

なにより、これはやっておきたいです。

### 構造の変更

せっかくJavaFXを利用しているのに、まったくMVC構造じゃありません。

Cの中にMが存在し、各クラスが責任を持ち切れておらず、修正が大変です。

一番の問題は、SequenceDiagramDrawerクラスをClassDiagramDrawerが保持していない点だと思っています。
MainControllerクラスがSequenceDiagramDrawerクラスを保持しているのはいいのですが、ClassDiagramDrawerクラスから直接変換したシーケンス図を設定できません。
MainControllerに一度戻してから設定しなおさなければならず、回りくどいことこの上ありません。
せっかくJavaを利用しているのだから、参照渡しでSequenceDiagramDrawerクラスのインスタンスを両人が持てばいいのにと思います。

上記の話は、ClassDiagramDrawerクラスについても全く同じです。

### シーケンス図からクラス図に変換する際の処理

実は、簡単にシーケンス図からクラス図に変換できません。

というのも、シーケンス図を1つ削除しても、実は何も変わっていません。
次の手順を踏む必要があります。

1. クラス図タブを選択し、ソースコードにシーケンス図の内容を反映する。
1. ソースコードを記述（改行を追加する程度でよい）し、クラス図に反映する。

また、この時にメソッド内の構文は全て消えてしまいます。

恐らく、シーケンス図を削除後にクラス図タブを選択すると、クラス図の内部情報に反映したものをソースコードに変換しているからだと推測します。
ClassDiagramDrawerクラスでソースコードに変換する際、シーケンス図の情報まで対応していないのが原因です。

### テストコードの記述

シーケンス図とC++に関する項目については、全くテストコードが存在していません。
カバレッジも70%を切ってしまい、このままでは問題だらけです。

### C++に関するコードの整形

せめてインデントは揃えてほしかったです。
そうでなくても、書き方などにはまだまだ改善の余地はあると思います。
コメントなんて消してもいいと思うのですが。


## やってみたいこと

問題はあれど、理想も語りたいです。

### シーケンス図の記述機能の拡張

なにより最重要項目だと思っています。

せめて、メッセージの追加・変更・削除くらいはできるようにしたいです。
また、ライフラインの変更はあってもいいと思いますが、追加と削除は無くてもいいと思います。
ライフラインの追加は、メッセージ先を変な場所に置くことで、自動的に追加すればよいです。
ライフラインの削除は、関係しているメッセージを全て削除した際に削除すればよいです。

### シーケンス図のメッセージの種類の拡張

戻り値がないvoid型で引数が無いメソッドと、代入文とローカル変数定義文だけでは明らかに力不足です。
もっと増やしたいです。

というか、一番はif文やfor文に対応する複合フラグメントを記述できるようにすべきです。
シーケンス図側での記述機能としても追加したいです。

### クラス図の記述機能の拡張

関係の関係元または関係先の変更や、関係の線の折れ線対応など、できることはまだまだあります。

特に、クラスまたは関係の非表示は、いつかやってみたいです。
クラスと関係が多くなりすぎると、やっぱり見づらくなるため、インライン属性に置けば問題ないと思います。

あと、パッケージに対応したいです。
可視性のパッケージが、何の意味も持っていないのがもったいないです。

### クラス図における未対応の項目の対応

プロパティのreadOnlyや静的属性など、JavaやC++でも比較的簡単に変換規則を作りやすいものは作りたいです。
せっかくfescueを利用しているのに、RETUSSでできることが正規表現とほぼ変わらないのはつまらないです。

変換規則については、一番の研究項目だと思うので、是非とも考えてみてください。

### 同じ属性および同じ操作の対応

これはそんなに必要性を感じていません。
しかし、あれば便利であることには変わらず、適当にエラーを出せばいいような気もします。

しかし、ソースコード上で記述する際に、いちいちエラーが出てくると鬱陶しいです。
変数hogeが既に存在する状態で、hoge2を書こうとhogeまで書くとエラーが出て戻されるのは、厄介この上ありません。
この辺のUIをどうするかで、一応研究対象にならないことも無いと思います。

けどそんなに面白いかと言われると、よくわかりません。

### 保存機能の実装

せめてソースコードは外部ファイルに保存したいです。

UMLの外部ファイル保存については、XMIを使うべきだと思います。
仕様がわからず断念しましたが、やってみてもいいと思います。
特に、XMIの入出力ができると、別のUMLツールと併用できるため、メリットは地味に高いです。

個人的には、PlantUML形式に出力というのも面白いと思います。
パーサはPlantUMLが持っているのかどうか謎ですが、試してみる価値はあるのではないでしょうか。

### Java以外の言語の対応

RETUSSは、別にオブジェクト指向言語にのみ対応しているわけではありません。
UMLとの対応関係さえ実装できれば、またソースコードの構文解析ができれば、どのような言語にも対応できます。

インタプリタ言語や関数型言語に対応できると、それはそれで面白そうです。

### ノート記述機能およびコメント記述機能の追加

クラス図でノートを記述できる機能は無くなりました。
これは、ノートを書いてもソースコードに反映できないためです。

また、ソースコード上でコメントを書いても、すぐに消えてしまいます。
これは、コメントを構文解析しておらず、クラス図に対応できないためです。

しかし、どうにも不便でなりません。
ノートはメモとして役立つのに、それが無いのはつらいです。
もちろん、コメントがないのにソースコードを書けなんて、苦行でしかありません。
ぜひ対応してほしいです。

### OCL文の対応

せっかくUMLにはOCLというオブジェクト制約言語があるのだから、ぜひ使うべきです。
属性および操作のプロパティで使うのももちろんですが、ノートに書くというのも手だと思います。

どちらにせよ、OCLの構文解析器の登場が待たれます。

### シーケンス図とC++言語のトレーサビリティ維持

これが無いと、RETUSSはC++に対応している、とまで言えないでしょう。

しかし、ヘッダファイルしか対応していない現在、どのように実装を書くべきかは考えどころです。
また、C++の構文解析器があまり優秀でないらしく、define文があるとダメとか、C++の構文解析自体にも問題が多そうです。

楽しいかもしれませんよ。



# 注意点

* テストコードのコンパイル時には `-Djdk.attach.allowAttachSelf` システムプロパティを追加してください。
* ビルドする際にTestFXによるGUIテストを実行するため、マウスなどを動かさないようにしてください。
* JMockitは他のライブラリと比べても、バージョンの違いによる仕様変更が激しいようです。
  そこまで核となるような事はしていないと思いますが、気を付けてください。
* テストコード内ではHamcrestではなくAssertJのみを使用していますが、TestFXがHamcrestを利用しているため、Hamcrestを除外できません。
  テストコード内ではAssertJのみを使用してもらえると助かります。
