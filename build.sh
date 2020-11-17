pushd $HOME/ITMO-Psychopass-Team/pipelines/builder > /dev/null
    ./build.sh
popd > /dev/null

pushd $HOME/ITMO-Psychopass-Team/Civilla-Project > /dev/null
    for DIR in */; do
        echo "Building project $DIR"
        pushd $DIR > /dev/null
            ./build.sh
        popd > /dev/null
    done
popd > /dev/null
