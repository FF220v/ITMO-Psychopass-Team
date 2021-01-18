~/ITMO-Psychopass-Team/helpers/kubectl_proxy.sh &
PID_1=$!

~/ITMO-Psychopass-Team/helpers/kubectl_mongo_ports_forward.sh &
PID_3=$!

read -p "Press enter to stop forwarding"
kill $PID_1 $PID_3
