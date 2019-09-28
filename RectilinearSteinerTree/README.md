<h2>Rectilinear Steiner Tree</h2>

<h3>問題文</h3>
$100×100$の盤面にパネルが$N$枚置かれています．パネルの座標は$(x_{i},y_{i})$です．追加でパネルを足してこれら$N$枚を連結にしてください．追加するパネルの数はなるべく小さくしてください．ただし，パネルが連結であるとは，上下左右でパネルが辺を共有しているということです．

<h3>制約</h3>
<ul>
<li>$10 \leqq N \leqq 200$</li>
<li>$0 \leqq x_{i}, y_{i} \leqq 99$</li>
</ul>

<h4>入力</h4>
パネルの枚数$N$と，続く$N$行にパネルの座標$(x_{i},y_{i})$が与えられます．
<div class = "iodata">
$N$<br>
$x_{0} \ y_{0}$<br>
$x_{1} \ y_{1}$<br>
$\vdots$<br>
$x_{N-1} \ y_{N-1}$<br>
</div>

<h4>出力</h4>
盤面に追加するパネルの数$M$と，続く$M$行にそれぞれの座標を出力してください．もともと配置されているパネルと座標が重複しないようにしてください．また，出力するパネル同士でも座標が重複しないようにしてください．
<div class = "iodata">
$M$<br>
$ax_{0} \ ay_{0}$<br>
$ax_{1} \ ay_{1}$<br>
$\vdots$<br>
$ax_{M-1} \ ay_{M-1}$<br>
</div>

<h3>スコア</h3>
入力で与えられたのパネルが連結になった場合，追加したパネルの数$M$枚がスコアになります．連結になってなかったら$-1$を出力します．

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
-json  : 結果をJSON形式で表示
-debug : 実行コマンドの入出力を保存
</pre>
