#!/usr/bin/env bash

get_image(){
   docker inspect --format='{{index .RepoDigests 0}}' $IMAGE
}

cat <<EOF
{
    "civilla_policecontrolproxy_image": "`IMAGE=civilla/policecontrolproxy get_image`"
}
EOF
