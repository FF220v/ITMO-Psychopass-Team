#!/usr/bin/env bash

docker rm -f civilla-devicesserver
docker run --name=civilla-devicesserver civilla/devicesserver
