#!/usr/bin/env bash

get_image(){
   docker inspect --format='{{index .RepoDigests 0}}' $IMAGE
}

cat <<EOF
{
    "civilla_policecontrolproxy_image": "`IMAGE=civilla/policecontrolproxy get_image`",
    "civilla_policecontrolserver_image": "`IMAGE=civilla/policecontrolserver get_image`",
    "civilla_analysisserver_image": "`IMAGE=civilla/analysisserver get_image`",
    "civilla_mongodb_image": "`IMAGE=civilla/mongodb get_image`",
    "civilla_mongodbproxyserver_image": "`IMAGE=civilla/mongodbproxyserver get_image`",
    "civilla_notificationserver_image": "`IMAGE=civilla/notificationserver get_image`"
    "civilla_devicesserver_image": "`IMAGE=civilla/devicesserver get_image`"
}
EOF
