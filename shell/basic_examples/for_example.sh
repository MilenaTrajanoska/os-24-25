#!/bin/bash

if [ $# -ne 1 ]
then
	echo "Usage: $0 <folder>"  # $0 e ime na skriptata
 	exit 1
fi

folder=$1

if [ ! -d $folder ]
then
	echo "Ne e folder"
	exit 1
fi

files=`ls $folder`

for file in $files
do
	echo "Pechatam file $file"
	cat ${folder}${file}
done
