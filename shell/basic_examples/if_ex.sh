#!/bin/bash

if [ -f $1 || $1 = "abc" ]
then
	echo "Prviot argument e file"
elif [ $# -gt 5 ]
then
	echo "Ima povekje od 5 argumenti"
else
	echo "Default"
fi
