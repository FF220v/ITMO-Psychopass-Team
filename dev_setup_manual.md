## This manual is dedicated to help setting up a civilla dev machine

1. install multipass (usage of Hyper-V is preferable), use this link https://multipass.run/docs/installing-on-windows
2. use `multipass launch 20.04 --name civilladev --mem 5G --cpus 2 --disk 50G --cloud-init dev_config.yaml`
3. ask another developer to provide you with an RSA private key, use the key with your ssh client to connect dev machine
4. do `docker login -u civilla -p PASSWORD` to access container registry
5. do `./build.sh && ./deploy.sh` to deploy product to kubernetes on your (development) stage
