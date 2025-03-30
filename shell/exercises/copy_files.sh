#!/bin/bash

if [ $# -lt 2 ]
then
	echo "USAGE: `basename $0` srcdir/ destdir/"
	exit 1
fi

srcdir=$1
destdir=$2

if [ ! -d $srcdir ]
then
	echo "Source directory $srcdir is not a directory"
	exit 1
fi

if [ ! -d $destdir ]
then
	mkdir $destdir
fi

files=`ls -l $srcdir | grep "^-" | awk '{print $9;}' | grep "^[0-9][0-9]*[a-z]*\.out"`

for file in $files
do
	`cp ${srcdir}${file} ${destdir}${file} `
done

fileswithex=`ls -l $destdir | grep "^-..x" | awk '{ print $5; }'`
total=0

for size in $fileswithex
do
	total=$(($total + $size))
done

echo $total
