#!/usr/bin/env bash

docker rm -f civilladev-builder-policecontrolproxy
docker run --name civilladev-builder-policecontrolproxy \
        -v $PWD:/project \
        -v civilla-maven-repo:/root/.m2 \
        civilladev/builder
docker build -t civilla/policecontrolproxy .
