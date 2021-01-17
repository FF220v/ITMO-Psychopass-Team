POD=`kubectl get pods -ncivilla-stage --no-headers -o custom-columns=":metadata.name" | grep analysisserver-`
kubectl port-forward $POD -ncivilla-stage --address='0.0.0.0' 27017:27017
