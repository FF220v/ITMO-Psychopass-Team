### Hello, fellow developer!

This short readme should help you to work with acceptance tests.  

Basically tests require one file - settings.json. It is generated out of staging data
and from secret files.  

Before starting with tests: 
1. create an application on `https://my.telegram.org/apps`
2. add your application data for your stage to secrets `sops secrets/test_app.json`. 
3. run `generate_session.sh` in order to connect testing session to your telegram account. 

That's it. Now you can `./run_tests.sh`

Running tests on a local machine is easy as hell.
1. install packages `pip install -r requirements.txt`
2. `./run_tests.sh` once on remote machine to generate `settings.json`
3. copy `settings.json` to your work directory
4. change `localhost` addresses in `settings.json` to your cluster machine's ones
5. finally, run following stuff on a cluster machine:
```
MONGO_POD=`kubectl get pods -ncivilla-stage --no-headers -o custom-columns=":metadata.name" | grep mongodb-`
kubectl port-forward $MONGO_POD -ncivilla-stage --address='0.0.0.0' 27017:27017
```
this should start mongodb pod port forwarding.  

Now you should be able to run and debug tests locally.
