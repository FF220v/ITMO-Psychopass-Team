#!/usr/bin/env bash

docker rm -f civilladev-builder-devicesserver
docker run --name civilladev-builder-devicesserver \
        -v $PWD:/project \
        -v civilla-maven-repo:/root/.m2 \
        civilladev/builder
docker build -t civilla/devicesserver .
