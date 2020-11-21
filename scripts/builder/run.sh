#!/usr/bin/env bash

$PROJECT_PATH=$1
docker rm -f civilladev-builder
docker run -v $PROJECT_PATH:/project --name=civilladev-builder civilladev/builder
