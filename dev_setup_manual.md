## This manual is dedicated to help setting up a civilla dev machine

1. install multipass (usage of Hyper-V is preferable), use this link https://multipass.run/docs/installing-on-windows
2. copy `dev_config.yaml` somethere and put your git info in there
3. use `multipass launch 20.04 --name civilladev --mem 5G --cpus 2 --disk 50G --cloud-init dev_config.yaml`
4. get ip address of your machine `multipass list`, ask another developer to provide you with an RSA private key, use the key with your ssh client to connect to dev machine
5. copy and paste kubectl configs `cat ~/.kube/config` to your `$HOME/.kube/config` in order to use kubectl from host machine
6. do `docker login -u civilla -p PASSWORD` to access container registry
7. do `./build.sh && ./deploy.sh` to deploy product to kubernetes on your (development) stage
