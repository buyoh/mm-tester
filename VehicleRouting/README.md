<h2>Vehicle Routing</h2>

<h3>問題文</h3>
配送最適化問題です．目的地の数$N$，トラックの台数$M$が与えられます．$N$個の目的地には$0$から$N-1$までの番号が振られており，$i$番目の目的地の座標は$(x_{i},y_{i})$です．$M$台のトラックには$0$から$M-1$までの番号が振られており，$j$番目のトラックの容量は$cap_{j}$，スピードは$speed_{j}$です．倉庫の座標は$(depot_{x},depot_{y})$です．全ての目的地に荷物を$1$つ配達し終わるのにかかる時間をなるべく小さくしてください．倉庫ではトラックの容量以下の任意の個数の荷物を積む事が出来ます．各トラックが配達する荷物の総量に上限はありません．必ず全てのトラックを使う必要もありません．

<h4>制約</h4>
<ul>
<li>$50 \leqq N \leqq 500$</li>
<li>$0 \leqq x_{i},y_{i},depot_{x},depot_{y} \leqq 1000$</li>
<li>$When \ i \neq j,(x_{i},y_{i}) \neq (x_{j},y_{j})$</li>
<li>$For \ each \ i (1 \leq i \leq N), (x_{i},y_{i}) \neq (depot_{x},depot_{y})$</li>
<li>$3 \leqq M \leqq 10$</li>
<li>$5 \leqq cap_{j} \leqq 20$</li>
<li>$1 \leqq speed_{j} \leqq 20$</li>
</ul>

<h4>入力</h4>
<div class = "iodata">
<pre>
$N \ M$
$depot_{x} \ depot_{y}$
$x_{0} \ y_{0}$
$x_{1} \ y_{1}$
$\vdots$
$x_{N-1} \ y_{N-1}$
$cap_{0} \ speed_{0}$
$cap_{1} \ speed_{1}$
$\vdots$
$cap_{M-1} \ speed_{M-1}$
</pre>
</div>

<h4>出力</h4>
$1$行目にトラックの出撃回数$K$を出力してください．続く$K$行の各行には，トラックの種類$T_{i}(0 \leqq T_{i} \leqq M-1)$，荷物の個数$L_{i}(1 \leqq L_{i} \leqq cap_{T_{i}})$，$L$個の目的地$D_{i,0},D_{i,1},\cdots,D_{i,L_{i}-1} \ (0 \leqq D_{i,j} \leqq N-1)$を出力してください．

<div class = "iodata">
<pre>
$K$
$T_{0} \ L_{0} \ D_{0,0} \ D_{0,1} \ \cdots \ D_{0,L_{0}-1}$
$T_{1} \ L_{1} \ D_{1,0} \ D_{1,1} \ \cdots \ D_{1,L_{1}-1}$
$\vdots$
$T_{K-1} \ L_{K-1} \ D_{K-1,0} \ D_{K-1,1} \ \cdots \ D_{K-1,L_{K-1}-1}$
</pre>
</div>
<br>

出力例）
<div class = "iodata">
<pre>
3
1 5 3 9 4 1 2
0 2 0 8
2 3 5 7 6
</pre>
</div>

<h3>スコア</h3>
各トラック毎に，担当分の中で最後の荷物を配達するまでの移動距離を算出する．この移動距離をトラックのスピードで割り，そのトラックの配達時間とする．スコアは各トラックの配達時間のうち最も時間がかかったものとする．

<h3>テスタ</h3>
TopCoder の Marathon Match と同じです．<code>"[command]"</code>にプログラムの実行コマンド，<code>[seed]</code>に乱数のシードを入れてください．
<div class = "iodata">
<pre>
$ java -jar Tester.jar -exec "[command]" -seed [seed]
</pre>
</div>

<h4>その他オプション</h4>
<pre>
-vis   : ビジュアライズ
-save  : 画像の保存
</pre>
