#!/usr/bin/env bash

docker rm -f civilla-mongodbproxy
docker run --name=civilla-mongodbproxy civilla/mongodbproxy
