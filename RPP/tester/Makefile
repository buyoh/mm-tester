#Makefile
ALLSOURCE = *.java

.PHONY: all
all: comp
comp:
	javac ${ALLSOURCE}
	jar cvfm tester.jar tester.mf *.class
	rm -rf *.class

.PHONY: clean
clean:
	rm -rf *.jar
