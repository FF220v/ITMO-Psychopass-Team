
ssh-keygen -t rsa -f $HOME/.ssh/id_rsa -q -P "" <<< y >> /dev/null
cat ~/.ssh/id_rsa.pub > ~/.ssh/authorized_keys
echo -e "\nUse the following key to access dev machine through ssh"
cat  ~/.ssh/id_rsa
