TESTER=../../tester/tester.jar

for i in `seq 1 10`; do 
    echo "case:$i"
    java -jar $TESTER -exec "python SlidingPuzzle.py" -seed $i -vis -delay 0.1
done
