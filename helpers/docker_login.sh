pushd ~/ITMO-Psychopass-Team/secrets
    docker login -u `sops -d docker.json | jq -r .username` -p `sops -d docker.json | jq -r .password`
popd
