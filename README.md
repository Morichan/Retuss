# RETUSSについて

__RETUSS__ (Real-time Ensure Traceability between UML and Source-code System) は、UMLとソースコード間のトレーサビリティをリアルタイムに維持するツールです。

## 言い訳

現在はただの低レベルなクラス図描画ツールですが、これから少しずつ大きくしていこうと思います。

## 開発環境

* Windows 10 Pro (64 bit) 1703
* Java9
* JUnit5 (5.0.1)

## 依存ライブラリ

* assertj-core-3.8.0 ([AssertJ](http://joel-costigliola.github.io/assertj/index.html))
* guava-21.0 ([Guava](https://github.com/google/guava))
* hamcrest-core-1.3 ([Hamcrest](http://hamcrest.org/))
* jmockit-1.36.1 ([JMockit](http://jmockit.org/))
* junit-jupiter-api-5.0.1 ([JUnit5](http://junit.org/junit5/))
* junit-jupiter-engine-5.0.1 (JUnit5)
* opentest4j-1.0.0 ([Open Test Alliance for the JVM](https://github.com/ota4j-team/opentest4j))
* testfx-core-4.0.8-alpha ([TestFX](https://github.com/TestFX/TestFX))
* testfx-internal-java9-4.0.8-alpha (TestFX)
* testfx-junit5-4.0.8-alpha (TestFX)

### 依存関係

* assertj
* jmockit
* junit-jupiter
  * opentest4j
* testfx
  * guava
  * hamcrest

## コンパイルでの注意点

* テストコードのコンパイル時には `-Djdk.attach.allowAttachSelf` システムプロパティを追加してください。
* TestFX内で `--illegal-access` エラーを起こしますが、2017/11/10現在のJava9では特に問題ありません。
  そのうち使えなくなるかもしれませんが、それより前にTestFXが対応してくれると信じましょう。
* JMockitは他のライブラリと比べても、バージョンの違いによる仕様変更が激しいようです。
  そこまで核となるような事はしていないと思いますが、気を付けてください。