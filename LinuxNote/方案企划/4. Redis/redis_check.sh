#!/bin/bash
# ps -ef | grep 7000 | grep -v grep | wc -l
counter=$(ps -ef | grep 7000 | grep -v grep | wc -l)
if [ "${counter}" = "0" ]; then
    redis-server /data/redis-cluster/conf/redis_7000.conf
    sleep 2
    counter=$(ps -ef | grep 7000 | grep -v grep | wc -l)
    if [ "${counter}" = "0" ]; then
        /etc/init.d/keepalived stop
    fi
fi