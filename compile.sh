#!/bin/bash

set -euo pipefail

# Clean previous compilation
rm -rf bin/*

# Compile with MySQL connector in classpath
javac -cp "lib/mysql-connector-j-9.2.0.jar" -d bin src/com/guitar/**/*.java

echo "Compilation completed!"
