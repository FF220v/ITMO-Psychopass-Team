#!/usr/bin/env bash

docker rm -f civilladev-builder-analysisserver
docker run --name civilladev-builder-analysisserver \
        -v $PWD:/project \
        -v civilla-maven-repo:/root/.m2 \
        civilladev/builder
docker build -t civilla/analysisserver .
