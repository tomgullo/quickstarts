#!/bin/sh
tcpdump -qn -i eth0 "not (port ssh or port 53) " >> dump.log &
