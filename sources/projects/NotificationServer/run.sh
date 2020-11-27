#!/usr/bin/env bash

docker rm -f civilla-notificationserver
docker run --name=civilla-notificationserver civilla/notificationserver
