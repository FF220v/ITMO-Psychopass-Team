KUBE_CONFIG_DIR=${1:-"$HOME/ITMO-Psychopass-Team/kubernetes"}
./build.sh && ./run.sh $KUBE_CONFIG_DIR
