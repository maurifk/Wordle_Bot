PASCAL_COMPILER = fpc
PASCAL_FLAGS = -Mtp

TARGETS = Wordle.exe WordleENG.exe WordleENGFil.exe

all: $(TARGETS)

%.exe: %.pas
	$(PASCAL_COMPILER) $(PASCAL_FLAGS) $<

clean:
	rm -f $(TARGETS) *.o

# Phony targets
.PHONY: all clean

