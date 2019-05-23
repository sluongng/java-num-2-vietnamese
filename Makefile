COMPILE=javac
EXECUTE=java

all: compile run clean

compile:
	${COMPILE} NumToViet.java

run: compile
	${EXECUTE} NumToViet

clean:
	rm -f *.class

