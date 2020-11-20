pushd ~/ITMO-Psychopass-Team/Civilla-Project > /dev/null
    ../helpers/docker_login.sh
    for DIR in */; do
        echo "Pushing images for project $DIR"
        pushd $DIR > /dev/null
            ./push.sh
        popd > /dev/null
    done
popd > /dev/null

pushd ~/ITMO-Psychopass-Team > /dev/null
    mkdir deploy_tmp
    sops -d secrets/kube_secrets.json |jq -r .$STAGE > deploy_tmp/kube_secrets.json
    cat kube_params/kube_params.json | jq -r .$STAGE > deploy_tmp/kube_params.json
    ./helpers/get_images_list.sh > deploy_tmp/kube_images.json
    cp -r kubernetes_templates deploy_tmp/
    pushd scripts/kube_config_tool/ > /dev/null
        ./gen_config.sh ~/ITMO-Psychopass-Team/deploy_tmp
    popd > /dev/null
    kubectl apply -f deploy_tmp/kube_config.yaml
    rm -rf deploy_tmp
popd > /dev/null
