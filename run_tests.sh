#!/bin/bash -eu

for testClass in $(find test -type f | grep \.java$ | sed 's/\.java$//g' | sed 's/^test\///g' | grep /Test[a-zA-Z]*$) ; do
  echo "====${testClass}========"
  java -cp lib/junit-4.9b3.jar:build "$testClass"
done

exit
