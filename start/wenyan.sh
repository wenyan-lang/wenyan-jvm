#!/usr/bin/env bash

#######
#1. wenyan.sh compile @MakeFile.txt targetPath sourcePath mainClass
#2. wenyan.sh wyg targetPath sourcePath
#3. wenyan,sh run targetPath mainClass args
#4. wenyan.sh help
#5. wenyan.sh common args
#######

compile='compile'
wyg='wyg'
run='run'
help='help'
common='common'
c="java -jar wenyan.jar"
if [ "$1" == $compile ]; then

    "$c" -c $2 $3 -sc $4 -m $5

elif [ "$1" == $wyg ]; then

    "$c" -o $1 -sc $2 -wyg $2

elif [ "$1" == $run ]; then
    ar="$4"
    for (( i = 5; i <= $#; ++i )); do
        ar+=" ${!i}"
    done
    "$c" -o $2 -n $3 -r "$ar"
elif [ "$1" == $help ]; then
    java -jar wenyan.jar
elif [ "$1" == $common ]; then
    ar=""
    for (( i = 0; i <= $#; ++i )); do
        ar+=" ${!i}"
    done
    "$c" "$ar"
fi