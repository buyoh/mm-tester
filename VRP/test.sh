#!/bin/sh

EXEC=./a.out

for i in `seq 1 10`
do
   	echo "case:$i"
    java -jar tester.jar -exec $EXEC -seed $i -save -num -debug
    # java Tester -exec $EXEC -seed $i -debug -save -num
done
