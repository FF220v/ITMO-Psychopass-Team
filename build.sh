#!/usr/bin/env bash

pushd $HOME/ITMO-Psychopass-Team/scripts/builder > /dev/null
    ./build.sh
popd > /dev/null

pushd $HOME/ITMO-Psychopass-Team/sources/projects > /dev/null
    for DIR in */; do
        echo "Building project $DIR"
        pushd $DIR > /dev/null
            ./build.sh
        popd > /dev/null
    done
popd > /dev/null
