#!/bin/bash

# Check for the number of command-line arguments
if [ $# -ne 5 ]; then
    echo "Usage: $0 <C++ Program> <Java Program> <Python Program> <Output File> <Command>"
    exit 1
fi

# Assign the command-line arguments to variables
cpp_program="$1"
java_program="$2"
python_program="$3"
output_file="$4"
command="$5"

# C++
echo -e "CPP" >> "${output_file}"
g++ -g -pthread -std=c++11 "${cpp_program}.cpp" -o "${cpp_program}"
"${cpp_program}" "$command" >> "${output_file}"


# Java
echo -e "\nJAVA" >> "${output_file}"
javac "${java_program}.java"
java "${java_program}.java" "$command" >> "${output_file}"

# Python
echo -e "\nPYTHON" >> "${output_file}"
python3 "${python_program}.py" "$command" >> "${output_file}"

echo "Done! Tests are:"
cat "${output_file}"