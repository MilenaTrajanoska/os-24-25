#!/bin/bash

if [ $# -lt 2 ]
then
	echo "Run the script with $0 <season> <location>" # $0 e ime na skriptata
	exit 1
fi

season=$1
location=$2

result=`cat race_results.tsv | awk -v season=$season -v location=$location '($3 ~ season) && ($4 ~ location) {print $1}' | sort | uniq -c`

echo "$result"
