# Typical-MM
## はじめに
TopCoder の Marathon Match に最近ハマっています．皆んなやろう．練習問題のようなものを作ってみました．マラソン形式のコンテストに興味があるけど，何から始めたら良いか分からない方のお役に立てれば幸いです．「最適化」だったり「NP困難」で検索してヒットする有名な問題を Marathon Match 風テスタにしているだけです，理論的な事は全く分かりません．何か間違っていれば指摘していただけるとありがたいです．また，ゲーム AI の様な問題も特に案が無いためここにはありません．  
  
テスタは Marathon Match 風にしています．使い方は本家と同じなので[診断人さん - @nico_shindannin](https://twitter.com/nico_shindannin)のブログが参考になります．ついでにアカウントを作ってコンテストに出てみましょう．  
- [じじいのプログラミング - TopCoderマラソンマッチのはじめかた](http://shindannin.hatenadiary.com/entry/2014/10/05/003714)  


## 利用方法
### 入手
```
git clone --depth 1 https://github.com/kosakkun/Typical-MM.git
```
### 環境
macOS，Linux(Ubuntu) で JRE，JDK，ソルバ用の開発・実行環境があれば動くことを確認しています．これ以外の OS は分からないのですみません．テスタは一応コンパイルしたものを問題毎に用意しましたが，古い Java の環境だと多分動かないので皆さんの環境でコンパイルし直してください．

### ファイル構成 
各問題は以下の様なファイル構成になっています．tester の下にはテスタ本体の Tester.java と，私の手元の環境でコンパイルして jar にした Tester.jar があります．実行出来ない場合は```make clean && make```でコンパイルできるのでそれでもう一度実行できるか確認してください．それでもダメなら連絡してください．  
sample の下には，C++，Java，Python のサンプルプログラムがあります．C++，Javaは```make && make run```，Pythonは```sh run.sh```で実行出来ます．これも私の手元では動くことを確認していますが，無理ならすみません．  
```
.
├── README.md
├── image
│   ├── 1.png
│   └── ...
├── tester
│   ├── Makefile
│   ├── Tester.jar
│   ├── Tester.java
│   └── tester.mf
└── sample
    ├── cpp
    │   ├── Makefile
    │   └── TravelingSalesman.cpp
    ├── java
    │   ├── Makefile
    │   └── TravelingSalesman.java
    └── python
        ├── TravelingSalesman.py
        └── run.sh
  
```

### 問題
#### [Traveling Salesman](TravelingSalesman/)
巡回セールスマン問題です．有名です．  
<img src="TravelingSalesman/image/1.png" width="400px">

#### [Vehicle Routing](VehicleRouting/)
配送計画を考える問題です．巡回セールスマン問題と少し似ています．  
<img src="VehicleRouting/image/1.png" width="400px">

#### [Rectangle Packing](RectanglePacking/)
長方形を良い感じで詰め込む問題です．  
<img src="RectanglePacking/image/1.png" width="400px">

#### [Graph Coloring](GraphColoring/)
グラフの彩色問題です．  
<img src="GraphColoring/image/1.png" width="400px">

#### [Clustering](Clustering/)
クラスタリングです．  
<img src="Clustering/image/1.png" width="400px">

#### [Sliding Puzzle](SlidingPuzzle)
15-Puzzle(スライドパズル)のN×Mバージョン．  
<img src="SlidingPuzzle/image/6x6.gif" width="400px">

#### [Rectilinear Steiner Tree](RectilinearSteinerTree/)
シュタイナー木のカクカクしたタイプのやつです．  
<img src="RectilinearSteinerTree/image/1.png" width="400px">

#### [Disk Covering](DiskCovering/)
円で点をカバーする問題です．  
<img src="DiskCovering/image/1.png" width="400px">



## コンテストのリンク
### TopCoder
一部過去問は Practice Contests から提出が可能です．その他の問題もテスタや問題文は今でも読めるのでトライしてみてください．  
- [Marathon Match Archive](https://community.topcoder.com/longcontest/stats/?module=MatchList)  
- [Marathon Match Practice Contests](https://community.topcoder.com/longcontest/?module=ViewPractice)
- [TopCoder Event](https://www.topcoder.com/community/events/)


### AtCoder
まだマラソン形式のコンテストはレートが付いていませんが，[@chokudai](https://twitter.com/chokudai) さんが気まぐれで開催したり，企業コンが行われたり最近増えてきています．[AtCoder Problems](https://kenkoooo.com/atcoder/) から探せますが，一応下に列挙しておきます．
- Chokudai Contest
	- [001 - 高橋君の山崩しゲーム](https://atcoder.jp/contests/chokudai001/)
	- [002 - 約数をたくさんつくろう！](https://atcoder.jp/contests/chokudai002/)
	- [003 - ○×ブロック](https://atcoder.jp/contests/chokudai003/)
- RCO日本橋ハーフマラソン
	- [第一回予選](https://atcoder.jp/contests/rco-contest-2017-qual/)
	- [第一回本選](https://atcoder.jp/contests/rco-contest-2017-final-open/)
	- [第二回予選](https://atcoder.jp/contests/rco-contest-2018-qual/)
	- [第二回本選](https://atcoder.jp/contests/rco-contest-2018-final-open/)
	- [第三回予選](https://atcoder.jp/contests/rco-contest-2019-qual/)
- HACK TO THE FUTURE
	- [2018 予選](https://atcoder.jp/contests/future-contest-2018-qual/)
	- [2018 本選](https://atcoder.jp/contests/future-contest-2018-final-open/)
	- [2019 予選](https://atcoder.jp/contests/future-contest-2019-qual/)
	- [2019 本選](https://atcoder.jp/contests/future-contest-2019-final/)
