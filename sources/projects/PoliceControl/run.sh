#!/usr/bin/env bash

docker rm -f civilla-police-control
docker run --name=civilla-policecontrol civilla/policecontrol
