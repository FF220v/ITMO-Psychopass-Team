#!/usr/bin/env bash

docker run -it \
        -v civilla-maven-repo:/root/.m2 \
        -v $PWD:/project \
        civilladev/builder