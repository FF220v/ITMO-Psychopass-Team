sudo ./packages_install.sh

microk8s enable dashboard dns

mkdir /home/ubuntu/.kube
microk8s config > /home/ubuntu/.kube/config
echo -e "\nCopy and paste configs to your \$HOME/.kube/config in order to use kubectl from host machine"
cat /home/ubuntu/.kube/config
echo ""

kubectl get deployment -nkube-system kubernetes-dashboard -o yaml > /tmp/kubernetes_dashboard.yaml
cd configure_dashboard > /dev/null
    sudo ./configure.sh
cd .. > /dev/null
kubectl apply -f /tmp/kubernetes_dashboard.yaml
rm /tmp/kubernetes_dashboard.yaml

ssh-keygen -t rsa -f /home/ubuntu/.ssh/id_rsa -q -P "" <<< y >> /dev/null
cat .ssh/id_rsa.pub > authorized_keys
echo -e "\nUse the following key to access dev machine through ssh"
cat  .ssh/id_rsa
echo ""
