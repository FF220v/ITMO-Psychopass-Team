#!/usr/bin/env bash

docker volume rm civilla-maven-repo
docker volume create --name civilla-maven-repo
docker run -it -v civilla-maven-repo:/root/.m2 civilladev/builder mvn archetype:generate

pushd $HOME/ITMO-Psychopass-Team/scripts/builder > /dev/null
    ./build.sh
popd > /dev/null

pushd ~/ITMO-Psychopass-Team/sources/projects/Base > /dev/null
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
