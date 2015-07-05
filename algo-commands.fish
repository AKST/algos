#! /usr/local/bin/fish

set src_path 'src'
set test_path 'test'
set dist_dir 'dist'
set lib_dir 'lib'
set zip_dir 'zips'
set tool_dir 'tools'

set class_path "$dist_dir:$lib_dir/*"
set package_path 'io.akst.algo'

#
# creates the $dist_dir if it does not exist
#
function init
  mkdir -p $dist_dir
  mkdir -p $lib_dir
  mkdir -p $zip_dir
  mkdir -p $tool_dir

  #
  # deps
  #
  if [ ! -f $lib_dir/stdlib.jar ]
    curl -o $lib_dir/stdlib.jar 'http://algs4.cs.princeton.edu/code/stdlib.jar'
  end
  if [ ! -f $lib_dir/algs4.jar ]
    curl -o $lib_dir/algs4.jar 'http://algs4.cs.princeton.edu/code/algs4.jar'
  end
  if [ ! -f $lib_dir/junit-4.11.jar ]
    curl -L -o $lib_dir/junit-4.11.jar 'http://search.maven.org/remotecontent?filepath=junit/junit/4.11/junit-4.11.jar'
  end
  if [ ! -f $lib_dir/hamcrest-core-1.3.jar ]
    curl -L -o $lib_dir/hamcrest-core-1.3.jar 'http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar'
  end

  #
  # tooling
  #
  if [ ! -f $tool_dir/checkstyle.jar ]
    curl -o $tool_dir/checkstyle.jar 'http://jaist.dl.sourceforge.net/project/checkstyle/checkstyle/6.8.1/checkstyle-6.8.1-all.jar'
  end
end

#
# empties the $dist_dir
#
function clean
  rm -rf "$dist_dir/*"
end

#
# watches a certain task
#
function watch_command
  if [ (count $argv) -gt 1 ]
    while true
      sleep 1.75
      set changes (find $src_path -type f -mtime -2s -name '*.java')
      if test $changes
        pick_operation $argv[2..(count $argv)]
      end
    end
  else
    help
  end
end

function build
  clean
  set src_src (tree -f -i -a $src_path | ag ".java\$")
  set test_src (tree -f -i -a $test_path | ag ".java\$")
  set source_tree $src_src $test_src
  javac -sourcepath src:test -cp "$lib_dir/*" -d $dist_dir $source_tree
end

function package
  set week_number $argv[1]
  set zip_location "$zip_dir/week_$week_number.zip"
  set unzip_location "$zip_dir/week_$week_number"
  #
  # preserve old file
  #
  if [ -f $zip_location ]
    set old_file_c_time (stat -f '%c' "$zip_location")
    mv $zip_location "$zip_dir/.week_$week_number-$old_file_c_time.zip"
  end
  #
  # package exercise
  #
  python scripts/package.py $week_number $zip_location
  #
  # extract output
  #
  unzip $zip_location -d $unzip_location
  #
  # validate output
  #
  set java_source (find $unzip_location -name '*.java')
  javac $java_source
  for file in $java_source
    java -jar $tool_dir/checkstyle.jar \
        com.puppycrawl.tools.checkstyle.Main \
        -c config/styles.xml $file
  end
  #
  # remove extracted output
  #
  rm -rf $unzip_location
end

function run
  switch $argv[2]
    case 1
      java -cp $class_path "$package_path.week1.PercolationStats" $argv[3] $argv[4]
    case 2
      java -cp $class_path "$package_path.week2.Subset" $argv[3]
  end
end

function test_command
  echo ''
  java -cp $class_path org.junit.runner.JUnitCore "$package_path.RunAllTheTests"
end

function pick_operation
  switch $argv[1]
    case build
      build
    case clean
      clean
    case run
      run $argv
    case package
      package $argv[2]
    case test
      test_command
    case help
      help
    case watch
      watch_command $argv
  end
end

function help
  echo
  echo 'USAGE:'
  echo
  echo "  build             | compiles the source"
  echo "  clean             | removes compiled source"
  echo "  run     n         | runs the source from week n"
  echo "  package n         | zips the source from week n"
  echo "  test              | runs all tests"
  echo "  watch cmd ...args | continously runs a command (with arguments) on changes"
  echo
end


if [ (count $argv) -gt 0 ]
  init
  pick_operation $argv
else
  help
end


