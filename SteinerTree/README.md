<h2>Steiner Tree</h2>

<h3>問題文</h3>
座標が重複しない$N$個の平面上の頂点が与えられます．頂点には$0$から$N-1$の番号が振られており，$i$番目の頂点の座標は$(x_{i},y_{i})$です．この平面に$M$個の頂点を追加します．入力て与えられた頂点と，追加した頂点からなる完全グラフの最小全域木の辺のコストをなるべく小さくしてください．辺の重みはユークリッド距離です．

<h4>制約</h4>
<ul>
<li>$10 \leqq N \leqq 200$</li>
<li>$0 \leqq M \leqq 1000$</li>
<li>$0 \leqq x_{i}, y_{i} \leqq 1000$</li>
<li>$When \ i \neq j,\ (x_{i}, y_{i}) \neq (x_{j}, y_{j})$</li>
</ul>

<h4>入力</h4>
<div class = "iodata">
$N$<br>
$x_{0} \ y_{0}$<br>
$x_{1} \ x_{1}$<br>
$\vdots$<br>
$x_{N-1} \ y_{N-1}$<br>
</div>

<h4>出力</h4>
$1$行目に追加する頂点の数$M$，続く$M$行に各頂点の座標$(ax_{i},ay_{i})$を整数で出力してください．出力の頂点の座標は他の頂点（入力・出力の頂点）と重ならないようにしてください．
<div class = "iodata">
$M$<br>
$ax_{0} \ ay_{0}$<br>
$ax_{1} \ ay_{1}$<br>
$\vdots$<br>
$ax_{M-1} \ ay_{M-1}$<br>
</div>

<h3>スコア</h3>
入力て与えられた頂点と，追加した頂点からなる完全グラフを作ります．各辺の重みは頂点間のユークリッド距離です．この完全グラフの最小全域木の辺の重みの総和がスコアです．制約を満たさない場合$-1$となります．

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
