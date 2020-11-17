KUBE_CONFIG_DIR=${1:-"$HOME/ITMO-Psychopass-Team/kubernetes"}
docker run -v $KUBE_CONFIG_DIR:/src/work_dir civilladev/kube_deploy
