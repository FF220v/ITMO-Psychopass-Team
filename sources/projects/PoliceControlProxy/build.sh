#!/usr/bin/env bash

docker rm -f civilladev-builder-policecontrolproxy
docker run --name civilladev-builder-policecontrolproxy -v $PWD:/project civilladev/builder
docker build -t civilla/policecontrolproxy .
