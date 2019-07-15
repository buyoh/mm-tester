###############################################################################
##### Makefile                                                             ####
###############################################################################

CXX      = g++
CXXFLAGS = -O2 -Wall -std=c++11
TARGET   = main
TESTER   = ../../tester/Tester.jar

.PHONY: all clean run

###############################################################################

all: $(TARGET)

$(TARGET): $(TARGET).cpp
	$(CXX) $(CXXFLAGS) -o $@ $^

clean:
	rm -f $(TARGET)

run:
	for i in `seq 1 10`; do \
		echo "case:$$i"; \
		java -jar $(TESTER) -exec "./$(TARGET)" -seed $$i -vis -save; \
	done
