#!/usr/bin/env bash

mongod -f /mongod.cfg
mongod --bind_ip_all
