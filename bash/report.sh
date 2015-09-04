#!/bin/sh
grep -v -E '(?|?)' dump.log | awk ' { print $3,  $5 } ' | sort | uniq -c
