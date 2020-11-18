pushd $HOME > /dev/null
    sudo ITMO-Psychopass-Team/pipelines/dev/packages_install.sh
    
    sudo groupadd docker
    sudo usermod -aG docker $USER
    sudo usermod -aG microk8s $USER
    sudo chown -f -R $USER ~/.kube
    sudo chown -f -R $USER ~/tmp
    iptables -P FORWARD ACCEPT

    microk8s enable dashboard dns

    mkdir .kube
    microk8s config > .kube/config
    cat .kube/config

    kubectl get deployment -nkube-system kubernetes-dashboard -o yaml > /tmp/kubernetes_dashboard.yaml
    pushd ITMO-Psychopass-Team/pipelines/dev/configure_dashboard > /dev/null
        sudo ./configure.sh
    popd > /dev/null
    kubectl apply -f /tmp/kubernetes_dashboard.yaml
    rm /tmp/kubernetes_dashboard.yaml

popd > /dev/null
