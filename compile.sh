#!/bin/bash
# Compile all Java source files into out/ directory
# Usage: ./compile.sh

echo "Compiling..."
mkdir -p out
javac -d out src/*.java

if [ $? -eq 0 ]; then
    echo "Build successful! Class files are in out/"
else
    echo "Build FAILED. Please fix the errors above."
    exit 1
fi
