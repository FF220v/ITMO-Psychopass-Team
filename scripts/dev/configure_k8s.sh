pushd $HOME > /dev/null
    
    sudo iptables -P FORWARD ACCEPT
    microk8s enable dashboard dns

    mkdir .kube
    microk8s config > .kube/config
    cat .kube/config

    kubectl get deployment -nkube-system kubernetes-dashboard -o yaml > /tmp/kubernetes_dashboard.yaml
    pushd ITMO-Psychopass-Team/scripts/dev/configure_dashboard > /dev/null
        ./configure.sh
    popd > /dev/null
    kubectl apply -f /tmp/kubernetes_dashboard.yaml
    rm /tmp/kubernetes_dashboard.yaml

popd > /dev/null
