#!/usr/bin/env bash

docker volume rm civilla-maven-repo
docker volume create --name civilla-maven-repo

pushd $HOME/ITMO-Psychopass-Team/scripts/builder > /dev/null
    ./build.sh
popd > /dev/null

pushd ~/ITMO-Psychopass-Team/sources/Base > /dev/null
    ./add_to_repo.sh
popd > /dev/null

pushd ~/ITMO-Psychopass-Team/sources/projects > /dev/null
    for DIR in */; do
        echo "Building project $DIR"
        pushd $DIR > /dev/null
            ./build.sh
        popd > /dev/null
    done
popd > /dev/null
