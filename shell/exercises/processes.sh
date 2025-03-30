#!/bin/bash

if [ $# != 1 ]
then
	echo "Enter the user id"
	exit 1
fi

user_id=$1
process_ids=`ps -ef | awk -v user_id="$user_id" '$1 ~ " *" user_id "$" {print $1;}' | sort | uniq`

echo $process_ids

for pid in $process_ids
do

	children=`ps -ef | awk -v pid="$pid" '$3 ~ pid {print ;}' | wc -l`
	echo $pid $children >> out.txt
done
