# MM-Tester 

## 環境

### Ubuntu
1. apt
```
$ sudo apt update
$ sudo apt install default-jre default-jdk build-essential git curl
```
2. sdkman
```
$ curl -s http://get.sdkman.io | bash
$ source ~/.sdkman/bin/sdkman-init.sh
$ sdk install gradle
```

### macOS
1. [Xcode](https://apps.apple.com/jp/app/xcode/id497799835?mt=12)とCommand Line Tools
```
$ xcode-select --install
```
2. [Homebrew](https://brew.sh)
```
$ brew update
$ brew cask install java
$ brew install git gradle
```

## 使い方

### テスタのビルド
```
$ git clone --depth 1 https://github.com/kosakkun/MM-Tester.git
$ cd MM-Tester
$ gradle build
```

### サンプル
例）Traveling Salesmanのjavaのサンプルを実行する場合．
```
$ cd TravelingSalesman/solver/java
$ sh run.sh
```

### 問題文が読めない時
[ここ](http://marxi.co)とかに貼り付けて読んでください．

### 問題毎のファイル構成
```
.
├── README.md
├── build.gradle
├── build
│   └── libs
│       └── Tester.jar
├── tester
│   └── Tester.java
└── solver
    ├── cpp
    │   ├── run.sh
    │   └── main.cpp
    ├── java
    │   ├── run.sh
    │   └── Main.java
    └── python
        ├── run.sh
        └── main.py
```

## 問題
### [Traveling Salesman](TravelingSalesman/)
<img src="images/TravelingSalesman.png" width="400px">

### [Vehicle Routing](VehicleRouting/) 
<img src="images/VehicleRouting.png" width="400px">

### [Rectangle Packing](RectanglePacking/)
<img src="images/RectanglePacking.png" width="400px">

### [Graph Coloring](GraphColoring/)
<img src="images/GraphColoring.png" width="400px">

### [Clustering](Clustering/)
<img src="images/Clustering.png" width="400px">

### [Sliding Puzzle](SlidingPuzzle)
<img src="images/SlidingPuzzle.gif" width="400px">

### [Rectilinear Steiner Tree](RectilinearSteinerTree/)
<img src="images/RectilinearSteinerTree.png" width="400px">

### [Disk Covering](DiskCovering/)
<img src="images/DiskCovering.png" width="400px">

### [Longest Path](LongestPath/)
<img src="images/LongestPath.png" width="400px">

### [Euclidean Steiner Tree](EuclideanSteinerTree/)
<img src="images/EuclideanSteinerTree.png" width="400px">
