## This manual is dedicated to help setting up a civilla dev machine

1. install multipass (usage of Hyper-V is preferable), use this link https://multipass.run/docs/installing-on-windows
2. copy `dev_config.yaml` somewhere and put your git info and stage name (pi for Pavel Ivanov for example) in it
3. ensure that you have access to both public and secret repo
4. use `multipass launch 20.04 --name civilladev --mem 5G --cpus 2 --disk 50G --cloud-init dev_config.yaml`
5. get ip address of your machine `multipass list`, ask another developer to provide you with an RSA private key, use the key with your ssh client to connect to dev machine with `ubuntu` username
6. (optional) copy and paste kubectl configs `cat ~/.kube/config` to your `$HOME/.kube/config` in order to use kubectl from host machine
7. `kubectl proxy --context microk8s` to run a proxy server on host machine (or `./helpers/kube_proxy.sh` on dev VM)
8. access dashboard on `http://your-kubectl-proxy-ip:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/` to ensure that it works
9. add your dev environment params to `kube_params/kube_params.json` and bot secrets (create a bot using Botfather) to `secrets/kube_secrets.json`
10. do `./build.sh && ./deploy.sh` to deploy product to kubernetes on your (development) stage
11. Dev stage is ready =) 