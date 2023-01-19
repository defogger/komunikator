#!/bin/sh
curl -O https://download.java.net/java/GA/jdk18/43f95e8614114aeaa8e8a5fcf20a682d/36/GPL/openjdk-18_linux-x64_bin.tar.gz
sudo tar xzvf openjdk-18_linux-x64_bin.tar.gz -C/opt/

curl -O https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.tar.gz
sudo tar xzvf apache-maven-3.8.7-bin.tar.gz -C/opt/
