#!/bin/bash

port=${1-8080}
echo "端口:$port"

result=$(lsof -i:"$port") || exit
echo "根据端口查询进程: $result"

array=($result)
pid=${array[10]}
echo "根据进程查询结果取得进程号:$pid"

if [ -n "$pid" ]; then
  echo "杀掉进程$pid"
  kill -9 $pid
fi
