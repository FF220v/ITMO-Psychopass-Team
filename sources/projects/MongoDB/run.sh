#!/usr/bin/env bash

docker rm -f civilla-mongodb
docker run --name=civilla-mongodb civilla/mongodb
