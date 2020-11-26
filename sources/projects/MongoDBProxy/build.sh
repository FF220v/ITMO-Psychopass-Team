#!/usr/bin/env bash

docker rm -f civilladev-builder-mongodbproxyserver
docker run --name civilladev-builder-mongodbproxyserver \
        -v $PWD:/project \
        -v civilla-maven-repo:/root/.m2 \
        civilladev/builder
docker build -t civilla/mongodbproxy .
