CXX = g++
CXXFLAGS = -std=c++20 -Wall

TARGET = main

SRCS = main.cpp
OBJS = $(SRCS:.cpp=.o)

FILES_TO_REMOVE = esp_matriz.txt eng_matriz.txt engF_matriz.txt

$(TARGET): $(OBJS)
	$(CXX) $(CXXFLAGS) -o $@ $^

%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c -o $@ $<

clean:
	rm -f $(OBJS) $(TARGET)

clean_all:
	rm -f $(FILES_TO_REMOVE) $(OBJS) $(TARGET)
