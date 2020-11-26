#!/usr/bin/env bash

ssh-keygen -t rsa -f ~/.ssh/id_rsa -q -P "" <<< y >> /dev/null
cat ~/.ssh/id_rsa.pub > ~/.ssh/authorized_keys
echo -e "\nUse the following key to access machine through ssh"
cat  ~/.ssh/id_rsa
