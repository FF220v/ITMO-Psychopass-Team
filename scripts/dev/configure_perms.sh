groupadd docker
usermod -aG docker ubuntu
usermod -aG microk8s ubuntu
chown -f -R ubuntu ~/.kube
chown ubuntu /tmp
chown ubuntu /run
