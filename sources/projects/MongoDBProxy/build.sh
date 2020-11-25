#!/usr/bin/env bash

docker rm -f civilladev-builder-mongodbproxy
docker run --name civilladev-builder-mongodbproxy \
        -v $PWD:/project \
        -v civilla-maven-repo:/root/.m2 \
        civilladev/builder
docker build -t civilla/mongodbproxy .
