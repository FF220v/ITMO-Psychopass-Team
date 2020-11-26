#!/usr/bin/env bash

docker rm -f civilla-policecontrolproxy
docker run --name=civilla-policecontrolproxy civilla/policecontrolproxy
