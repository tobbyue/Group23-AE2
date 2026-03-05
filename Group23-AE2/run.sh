#!/bin/bash
# Run the application (compile first if needed)
# Usage: ./run.sh

if [ ! -d "out" ] || [ -z "$(ls -A out 2>/dev/null)" ]; then
    echo "No compiled files found. Compiling first..."
    ./compile.sh || exit 1
fi

echo "Starting Part-Time Teacher Management System..."
echo "================================================"
java -cp out App
