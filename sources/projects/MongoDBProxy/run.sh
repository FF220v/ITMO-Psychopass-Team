#!/usr/bin/env bash

docker rm -f civilla-mongodbproxyserver
docker run --name=civilla-mongodbproxy civilla/mongodbproxyserver
