KUBE_TEMPLATES_DIR=$1
KUBE_VAR_DIR=$2
KUBE_RESULT_DIR=$3

docker run -v $KUBE_TEMPLATES_DIR:/src/templates \
           -v $KUBE_VAR_DIR:/src/vars \
           -v $KUBE_RESULT_DIR:/src/results
           civilladev/kube_deploy
