sudo ./packages_install.sh
sudo groupadd docker
sudo usermod -aG docker ubuntu
iptables -P FORWARD ACCEPT

microk8s enable dashboard dns

mkdir /home/ubuntu/.kube
microk8s config > /home/ubuntu/.kube/config
cat /home/ubuntu/.kube/config

sudo microk8s kubectl get deployment -nkube-system kubernetes-dashboard -o yaml > /tmp/kubernetes_dashboard.yaml
cd configure_dashboard > /dev/null
    sudo ./configure.sh
cd .. > /dev/null
sudo microk8s kubectl apply -f /tmp/kubernetes_dashboard.yaml
rm /tmp/kubernetes_dashboard.yaml
