#!/usr/bin/env bash

docker rm -f civilla-analysisserver
docker run --name=civilla-analysisserver civilla/analysisserver
