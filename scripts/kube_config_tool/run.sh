WORK_DIR=$1

docker run -v $WORK_DIR:/src/work_dir \
           civilladev/kube_config_tool
