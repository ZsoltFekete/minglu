#!/bin/bash -eu

mkdir -p build

javac -Xlint -cp lib/junit-4.9b3.jar -d build $(find src -type f | grep \.java$ ) $(find test -type f | grep \.java$ )

exit
