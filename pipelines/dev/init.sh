pushd $HOME
    sudo ITMO-Psychopass-Team/pipelines/dev/packages_install.sh
    
    sudo groupadd docker
    sudo usermod -aG docker ubuntu
    iptables -P FORWARD ACCEPT

    microk8s enable dashboard dns

    mkdir .kube
    microk8s config > .kube/config
    echo -e "\nCopy and paste configs to your \$HOME/.kube/config in order to use kubectl from host machine"
    cat .kube/config
    echo ""

    kubectl get deployment -nkube-system kubernetes-dashboard -o yaml > /tmp/kubernetes_dashboard.yaml
    pushd ITMO-Psychopass-Team/pipelines/dev/configure_dashboard
        sudo ./configure.sh
    popd > /dev/null
    kubectl apply -f /tmp/kubernetes_dashboard.yaml
    rm /tmp/kubernetes_dashboard.yaml

    ssh-keygen -t rsa -f $HOME/.ssh/id_rsa -q -P "" <<< y >> /dev/null
    cat .ssh/id_rsa.pub > authorized_keys
    echo -e "\nUse the following key to access dev machine through ssh"
    cat  .ssh/id_rsa
    echo ""
popd > /dev/null
