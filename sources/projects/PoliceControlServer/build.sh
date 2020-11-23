#!/usr/bin/env bash

docker rm -f civilladev-builder-policecontrolserver
docker run --name civilladev-builder-policecontrolserver \
        -v $PWD:/project \
        -v civilla-maven-repo:/root/.m2 \
        civilladev/builder
docker build -t civilla/policecontrolserver .
