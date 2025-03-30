#!/bin/bash

if [ $# -ne 2 ]
then
	echo "Usage: $0 <toReplace> <replaceWith>" # $0 e ime na skriptata 
	exit 1
fi

toReplace=$1  # test
replaceWith=$2 # nest

shift
shift

for file in $* # test1.txt test2.txt ...
do
	# file=test1.txt
	newName=`echo $file | sed "s/${toReplace}/${replaceWith}/"`
	echo $newName
	# nest1.txt
	mv $file $newName
done
