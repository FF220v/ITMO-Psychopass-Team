groupadd docker
usermod -aG docker $USER
usermod -aG microk8s $USER
chown -f -R $USER ~/.kube
chown $USER /tmp
chown $USER /run
