#!/bin/sh

EXEC=./a.out

for i in `seq 2 2`
do
   	echo "case:$i"
    # java -jar tester.jar -exec $EXEC -seed $i
    java Tester -exec $EXEC -seed $i -debug -save
done
