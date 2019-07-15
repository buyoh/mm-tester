# Typical-MM

## 環境

### Ubuntu
```
$ sudo apt update
$ sudo apt install default-jre default-jdk build-essential git
```

### macOS
```
$
```

## 使い方

### 入手
```
$ git clone --depth 1 https://github.com/kosakkun/Typical-MM.git
$ cd Typical-MM
$ make
```

### 問題毎のファイル構成
testerの下にTester.jarがあります．C++，Java，Pythonはサンプルを用意しました．
```
	.
	├── README.md
	├── image
	│   ├── 1.png
	│   ...
	├── tester
	│   ├── Tester.jar
	│   ├── Tester.java
	│   ...
	└── sample
	    ├── cpp
	    │   ├── Makefile
	    │   └── TravelingSalesman.cpp
	    ├── java
	    │   ├── Makefile
	    │   └── TravelingSalesman.java
	    └── python
	        ├── run.sh
	        └── TravelingSalesman.py
```

## 問題
### [Traveling Salesman](TravelingSalesman/)
巡回セールスマン問題です．有名です．  
<img src="TravelingSalesman/image/1.png" width="400px">

### [Vehicle Routing](VehicleRouting/)
配送計画を考える問題です．巡回セールスマン問題と少し似ています．  
<img src="VehicleRouting/image/1.png" width="400px">

### [Rectangle Packing](RectanglePacking/)
長方形を良い感じで詰め込む問題です．  
<img src="RectanglePacking/image/1.png" width="400px">

### [Graph Coloring](GraphColoring/)
グラフの彩色問題です．  
<img src="GraphColoring/image/1.png" width="400px">

### [Clustering](Clustering/)
クラスタリングです．  
<img src="Clustering/image/1.png" width="400px">

### [Sliding Puzzle](SlidingPuzzle)
15-Puzzle(スライドパズル)のN×Mバージョン．  
<img src="SlidingPuzzle/image/6x6.gif" width="400px">

### [Rectilinear Steiner Tree](RectilinearSteinerTree/)
シュタイナー木のカクカクしたタイプのやつです．  
<img src="RectilinearSteinerTree/image/1.png" width="400px">

### [Disk Covering](DiskCovering/)
円で点をカバーする問題です．  
<img src="DiskCovering/image/1.png" width="400px">
