sudo snap install microk8s --classic --channel=1.19/stable
sudo snap install kubectl --classic

apt update
apt install docker.io -y
apt install jq -y
apt install gpg -y

wget https://github.com/mozilla/sops/releases/download/v3.6.1/sops_3.6.1_amd64.deb -O /tmp/sops.deb
dpkg -i /tmp/sops.deb
rm /tmp/sops.deb
