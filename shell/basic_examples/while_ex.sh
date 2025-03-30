#!/bin/bash

if [ $# -ne 1 ]
then
	echo "Usage: $0 <number>" # $0 e ime na skriptata
	exit 1
fi

counter=$1

while [ $counter -gt 0 ]
do
	echo $counter
	counter=$(($counter - 1))

done
