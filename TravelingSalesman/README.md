# Traveling Salesman

## 問題文
巡回セールスマン問題です．座標が重複しない$N$個の頂点の座標が与えられます．頂点には$0$から$N-1$の番号が振られており，$i$番目の頂点の座標は$(x_{i}, y_{i})$です．訪れる順に頂点番号を出力してください．

### 制約
\\(
・0 \leqq N \leqq 1000 \\\\
・0 \leqq x_{i}, y_{i} \leqq 1000 \\\\
・When \ i \neq j,\ (x_{i}, y_{i}) \neq (x_{j}, y_{j})
\\)

### 入力
\\(
N \\\\
x_{0} \ y_{0} \\\\
x_{1} \ x_{1} \\\\
\vdots \\\\
x_{N-1} \ y_{N-1}
\\)

### 出力
\\(
v_{0} \\\\
v_{1} \\\\
\vdots \\\\
v_{N-1}
\\)

## スコア
$v_{0} \ \rightarrow v_{1} \ \rightarrow \ \cdots \ \rightarrow \ v_{N-1} \ \rightarrow \ v_{0}$と頂点を訪れた時の移動距離をスコアとする．

## テスタ
TopCoder の Marathon Match と同じです．```"[command]"```にプログラムの実行コマンド，```[seed]```に乱数のシードを入れてください．
```
$ java -jar Tester.jar -exec "[command]" -seed [seed]
```

### その他オプション
```
-vis  : ビジュアライズ
-save : 画像の保存
```
