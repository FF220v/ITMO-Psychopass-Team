git clone https://github.com/FF220v/ITMO-Psychopass-Team-Keys.git /tmp/keys_repo
pushd /tmp/keys_repo/sops
    gpg --import public.key
    gpg --allow-secret-key-import --import private.key
    cp .sops.yaml ~/ITMO-Psychopass-Team
popd
rm -rf /tmp/keys_repo
