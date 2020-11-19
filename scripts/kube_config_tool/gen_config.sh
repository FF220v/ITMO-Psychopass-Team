WORK_DIR=${1:-"$HOME/ITMO-Psychopass-Team/deploy_tmp"}

./build.sh && ./run.sh $WORK_DIR
