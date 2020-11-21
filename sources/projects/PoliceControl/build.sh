#!/usr/bin/env bash

docker rm -f civilladev-builder-policecontrol
docker run --name civilladev-builder-policecontrol -v $PWD:/project civilladev/builder
docker build -t civilla/policecontrol .
