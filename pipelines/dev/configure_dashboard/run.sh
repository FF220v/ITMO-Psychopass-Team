YAML_PATH=${1:-"/tmp"}
docker run -v $YAML_PATH:/src/work_dir devtools/configure_dashboard
