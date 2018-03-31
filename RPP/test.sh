#!/bin/sh

TESTER=tester/tester.jar
EXEC=./a.out

for i in `seq 1 10`
do
    echo "case:$i"
    java -jar $TESTER -exec $EXEC -seed $i -vis -save
    # java Tester -exec ./a.out -seed $i -vis -save
done