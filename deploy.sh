pushd $HOME/ITMO-Psychopass-Team/Civilla-Project > /dev/null
    for DIR in */; do
        echo "Pushing images for project $DIR"
        pushd $DIR > /dev/null
            ./push.sh
        popd > /dev/null
    done

    if [ "$STAGE" == "prod" ]; then
        VAR_PATH="prod"
    else
        VAR_PATH="dev"
    fi

popd > /dev/null

