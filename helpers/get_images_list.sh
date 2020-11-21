#!/usr/bin/env bash

get_image(){
   docker inspect --format='{{index .RepoDigests 0}}' $IMAGE
}

cat <<EOF
{
    "civilla_policecontrol_image": "`IMAGE=civilla/policecontrol get_image`"
}
EOF
