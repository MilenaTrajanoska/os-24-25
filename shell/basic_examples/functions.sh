#!/bin/bash

sumTwoNumbers() {
	echo $(($1 + $2))
}

sumTwoNumbers $*

