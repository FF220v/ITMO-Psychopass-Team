#!/usr/bin/env bash

docker rm -f civilladev-builder-notificationserver
docker run --name civilladev-builder-notificationserver \
        -v $PWD:/project \
        -v civilla-maven-repo:/root/.m2 \
        civilladev/builder
docker build -t civilla/notificationserver .
