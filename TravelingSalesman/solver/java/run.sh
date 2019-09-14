#!/bin/bash

JAVAC="javac"
TESTER="../../tester/Tester.jar"
TESTNUM=10

if [ -e *.class ]; then
    rm *.class
fi

$JAVAC Main.java
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

for i in `seq 1 $TESTNUM`; do
    java -jar $TESTER -exec "java Main" -seed $i -save | { read rslt; echo "case $i: $rslt"; }
done