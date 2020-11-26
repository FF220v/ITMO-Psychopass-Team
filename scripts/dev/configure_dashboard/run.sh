#!/usr/bin/env bash

YAML_PATH=${1:-"/tmp"}
docker run -v /tmp:/src/work_dir civilladev/configure_dashboard
