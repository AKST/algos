# note you should install the following
#
# - tree (recursive directory printing)
# - java sdk (which ever)

clean: init
	@rm -rf dist/*

init:
	@mkdir -p dist

build: clean
	@{ tree -f -i -a src ; tree -f -i -a test ; } \
	| grep ".java$$" \
	| xargs -I {} javac -sourcepath src:test -classpath "lib/*" -d dist {}

just-run:
	@java -cp "dist:lib/*" app.assignment1.PercolationStats 10 1

just-test:
	@echo ''
	@java -cp "dist:lib/*" org.junit.runner.JUnitCore io.akst.algo.RunAllTheTests \
		| grep -v at\ org.junit. \
		| grep -v at\ sun.reflect. \
		| grep -v at\ java.lang.reflect. \

deps:
	mkdir -p lib
	curl -o lib/stdlib.jar http://algs4.cs.princeton.edu/code/stdlib.jar
	curl -o lib/algs4.jar http://algs4.cs.princeton.edu/code/algs4.jar
	curl -L -o lib/junit-4.11.jar http://search.maven.org/remotecontent?filepath=junit/junit/4.11/junit-4.11.jar
	curl -L -o lib/hamcrest-core-1.3.jar http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

run: build just-run

test: build just-test
