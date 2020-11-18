KUBE_TEMPLATES_DIR=${1:-"$HOME/ITMO-Psychopass-Team/kubernetes_templates"}
KUBE_VAR_DIR=${2:-"$HOME/ITMO-Psychopass-Team/kubernetes_variables"}
KUBE_RESULT_DIR=${3:-"$HOME/ITMO-Psychopass-Team/kube_configs"}

rm -r $KUBE_RESULT_DIR
mkdir $KUBE_RESULT_DIR

./build.sh && ./run.sh $KUBE_TEMPLATES_DIR $KUBE_VAR_DIR $KUBE_RESULT_DIR
