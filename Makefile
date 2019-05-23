COMPILE=javac
EXECUTE=java

compile:
	${COMPILE} NumToViet.java

run: compile
	${EXECUTE} NumToViet

clean:
	rm -f *.class
