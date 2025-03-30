#!/bin/bash

filtered_rentals=`cat rental_sys.txt | grep "^2024-0[3-7]" | grep "Luxury" | awk -F "\t" '{print $4, $5}'`
names=`echo "$filtered_rentals" | awk '{print $1 "_" $2}' | sort | uniq`  # doint a trick, will replace Emili Johnson with Emily_Johnson

for name in $names
do
	decoded_name=`echo $name | sed 's/_/ /'`  # Remove the underscore _ in the name, transforms Emily_Johnson to Emily Johnson
	total=`echo "$filtered_rentals" | grep "$decoded_name" | awk '{sum += $NF} END {print sum}'`
	echo "$name $total"
done


