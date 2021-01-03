MONGO_POD=`kubectl get pods -ncivilla-stage --no-headers -o custom-columns=":metadata.name" | grep mongodb-`
kubectl port-forward $MONGO_POD -ncivilla-stage --address='0.0.0.0' 27017:27017
