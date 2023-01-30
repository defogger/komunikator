#!/bin/bash

path=$0

len=${#path}
path=${path:1:len-9}

pwd="${PWD}"
cd=$pwd
cd+=$path

echo $cd

cd $cd

mvn clean
mvn install

cd $pwd
