#!/bin/bash

PYTHON="python3"
TESTER="../../build/libs/Tester.jar"
TESTNUM=10

for i in `seq 1 $TESTNUM`; do
    java -jar $TESTER -exec "$PYTHON main.py" -seed $i -vis -delay 0.2 | { read rslt; echo "case $i: $rslt"; }
done
