#!/bin/bash

if [ $# -lt 1 ]
then 
	echo "Run the script $0 <userid>"
	exit 1
fi

idx=$1

cat logins.txt | grep "^$idx "| awk '{print $NF}' | sed -e 's/(//' -e 's/)//' | awk -F: 'BEGIN {total=0} {total+=$1*60 + $2;} END {print total;}'
