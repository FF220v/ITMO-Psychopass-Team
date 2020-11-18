snap install microk8s --classic --channel=1.18/stable
iptables -P FORWARD ACCEPT

snap install kubectl --classic

apt update
apt install docker.io -y
apt install jq -y