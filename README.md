|Main|Develop|
|:--:|:--:|
|[![Build Status](https://travis-ci.org/Morichan/Retuss.svg?branch=master)](https://travis-ci.org/Morichan/Retuss)|[![Build Status](https://travis-ci.org/Morichan/Retuss.svg?branch=develop)](https://travis-ci.org/Morichan/Retuss)|
|[![codecov](https://codecov.io/gh/Morichan/Retuss/branch/master/graph/badge.svg)](https://codecov.io/gh/Morichan/Retuss)|[![codecov](https://codecov.io/gh/Morichan/Retuss/branch/develop/graph/badge.svg)](https://codecov.io/gh/Morichan/Retuss)|
|![GitHub last commit (master)](https://img.shields.io/github/last-commit/Morichan/Retuss/master.svg)|![GitHub last commit (develop)](https://img.shields.io/github/last-commit/Morichan/Retuss/develop.svg)|

[![license](https://img.shields.io/github/license/Morichan/Retuss.svg)](LICENSE)

[![Java version](https://img.shields.io/badge/java-9+-4c7e9f.svg)](https://www.java.com/en/)
[![JUnit version](https://img.shields.io/badge/junit-5+-dc524a.svg)](https://junit.org/junit5/)
[![Gradle version](https://img.shields.io/badge/gradle-4.4+-007042.svg)](https://gradle.org/docs/)
[![ANTLR version](https://img.shields.io/badge/antlr-4+-ec312e.svg)](http://www.antlr.org/)
[![ANTLR version](https://img.shields.io/badge/fescue-2+-00dc00.svg)](https://github.com/Morichan/fescue/)

[![GitHub tag](https://img.shields.io/github/tag/Morichan/Retuss.svg)](https://github.com/Morichan/Retuss/tags)
[![GitHub release](https://img.shields.io/github/release/Morichan/Retuss/all.svg)](https://github.com/Morichan/Retuss/releases)
[![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed-raw/Morichan/Retuss.svg)](https://github.com/Morichan/Retuss/pulls?q=is%3Apr+is%3Aclosed)



# RETUSS

__RETUSS__ (Real-time Ensure Traceability between UML and Source-code System) は、UMLとソースコード間のトレーサビリティをリアルタイムに維持するツールです。


## 言い訳

現在はただの低レベルなクラス図描画ツールですが、これから少しずつ大きくしていこうと思います。

## 開発環境

* Windows 10 Pro (64 bit) 1803
* IntelliJ IDEA 2018.1.5 (Community Edition)

## 注意点

* テストコードのコンパイル時には `-Djdk.attach.allowAttachSelf` システムプロパティを追加してください。
* ビルドする際にTestFXによるGUIテストを実行するため、マウスなどを動かさないようにしてください。
* JMockitは他のライブラリと比べても、バージョンの違いによる仕様変更が激しいようです。
  そこまで核となるような事はしていないと思いますが、気を付けてください。
* テストコード内ではHamcrestではなくAssertJのみを使用していますが、TestFXがHamcrestを利用しているため、Hamcrestを除外できません。
  テストコード内ではAssertJのみを使用してもらえると助かります。