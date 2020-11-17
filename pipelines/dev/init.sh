pushd $HOME
    sudo ITMO-Psychopass-Team/pipelines/dev/packages_install.sh

    microk8s enable dashboard dns

    mkdir .kube
    microk8s config > .kube/config
    echo -e "\nCopy and paste configs to your \$HOME/.kube/config in order to use kubectl from host machine"
    cat .kube/config
    echo ""

    kubectl get deployment -nkube-system kubernetes-dashboard -o yaml > /tmp/kubernetes-dashboard.yaml
    cat /tmp/kubernetes-dashboard.yaml | yq -j | jq '.spec.template.spec.args = ["--auto-generate-certificates", "--namespace=kube-system", "--enable-skip-login"]' > /tmp/kubernetes-dashboard.yaml
    kubectl apply -f /tmp/kubernetes-dashboard.yaml
    rm /tmp/kubernetes-dashboard.yaml

    ssh-keygen -t rsa -f $HOME/.ssh/id_rsa -q -P "" <<< y >> /dev/null
    cat .ssh/id_rsa.pub > authorized_keys
    echo -e "\nUse the following key to access dev machine through ssh"
    cat  .ssh/id_rsa
    echo ""
popd
