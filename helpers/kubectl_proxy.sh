#!/usr/bin/env bash

kubectl proxy --context microk8s --address='0.0.0.0' --port=8001 --accept-hosts='.*'
