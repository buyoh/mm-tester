###############################################################################
##### Makefile                                                             ####
###############################################################################

MAKEDIRS  := $(shell find . -type d -name '*tester')
CLEANDIRS := $(addprefix clean_, $(MAKEDIRS))

.PHONY: all clean force

###############################################################################

all: $(MAKEDIRS)

clean: $(CLEANDIRS)

$(MAKEDIRS): force
	make -C $@

$(CLEANDIRS): force
	make -C $(patsubst clean_%,%,$@) clean

