###############################################################################
##### Makefile                                                             ####
###############################################################################

CXX      = g++
CXXFLAGS = -O2 -Wall -std=c++11
TARGET   = main
TESTER   = ../tester/tester.jar

.PHONY: all clean run

###############################################################################

all: $(TARGET)
	$(MAKE) $(TARGET)

$(TARGET): main.cpp
	$(CXX) $(CXXFLAGS) -o $@ $^

clean:
	rm -f $(TARGET)

run:
	java -jar $(TESTER) -exec "./$(TARGET)" -seed 12 -vis
