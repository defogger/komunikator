#!/bin/bash

path=$0

len=${#path}
path=${path:1:len-7}

pwd="${PWD}"
cd=$pwd
cd+=$path

echo $cd

cd $cd

java -jar target/klient-1.0-SNAPSHOT-shaded.jar

cd $pwd
