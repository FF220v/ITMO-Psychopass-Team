## This manual is dedicated to help setting up a civilla dev machine

1. install multipass (usage of Hyper-V is preferable), use this link https://multipass.run/docs/installing-on-windows
2. copy `dev_config.yaml` somethere and put your git info and stage name (pi for Pavel Ivanov for example) in it
3. ensure that you have access to both public and secret repo
4. use `multipass launch 20.04 --name civilladev --mem 5G --cpus 2 --disk 50G --cloud-init dev_config.yaml`
5. get ip address of your machine `multipass list`, ask another developer to provide you with an RSA private key, use the key with your ssh client to connect to dev machine
6. copy and paste kubectl configs `cat ~/.kube/config` to your `$HOME/.kube/config` in order to use kubectl from host machine
7. `kubectl proxy --context microk8s-cluster` to run a proxy server on host machine
8. access dashboard on `http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/` to ensure that it works
9. do `./build.sh && ./deploy.sh` to deploy product to kubernetes on your (development) stage
10. Dev stage is ready =) 