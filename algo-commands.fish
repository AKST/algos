#! /usr/local/bin/fish

set src_path 'src'
set test_path 'test'
set dist_dir 'dist'
set lib_dir 'lib'

set class_path "$dist_dir:$lib_dir/*"
set package_path 'io.akst.algo'

#
# creates the $dist_dir if it does not exist
#
function init
  mkdir -p $dist_dir
  mkdir -p $lib_dir
end

#
# deps
#
function deps
  if [ ! -f lib/stdlib.jar ]
    curl -o lib/stdlib.jar http://algs4.cs.princeton.edu/code/stdlib.jar
  end
  if [ ! -f lib/algs4.jar ]
    curl -o lib/algs4.jar http://algs4.cs.princeton.edu/code/algs4.jar
  end
  if [ ! -f lib/junit-4.11.jar ]
    curl -L -o lib/junit-4.11.jar http://search.maven.org/remotecontent?filepath=junit/junit/4.11/junit-4.11.jar
  end
  if [ ! -f lib/hamcrest-core-1.3.jar ]
    curl -L -o lib/hamcrest-core-1.3.jar http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
  end
end

#
# empties the $dist_dir
#
function clean
  rm -rf "$dist_dir/*"
end


function build
  clean
  set src_src (tree -f -i -a $src_path | ag ".java\$")
  set test_src (tree -f -i -a $test_path | ag ".java\$")
  set source_tree $src_src $test_src
  javac -sourcepath src:test -cp "$lib_dir/*" -d $dist_dir $source_tree
end

function run
  switch $argv[2]
    case 1
      java -cp $class_path "$package_path.week1.PercolationStats" $argv[3] $argv[4]
  end
end

function test
  echo ''
  java -cp $class_path org.junit.runner.JUnitCore "$package_path.RunAllTheTests"
end

if [ (count $argv) -gt 0 ]
  init
  switch $argv[1]
    case build
      build
    case clean
      clean
    case deps
      deps
    case run
      run $argv
    case test
      test
  end
end

