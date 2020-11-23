#!/usr/bin/env bash

docker rm -f civilla-policecontrolserver
docker run --name=civilla-policecontrolserver civilla/policecontrolserver
