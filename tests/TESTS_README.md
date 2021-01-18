### Hello, fellow developer!

This short readme should help you to work with acceptance tests.  

Basically tests require one file - settings.json. It is generated out of staging data
and from secret files.  

Before starting with tests: 
1. create an application on `https://my.telegram.org/apps`
2. add your application data for your stage to secrets `sops secrets/test_app.json`. 
3. run `./generate_session.sh` in order to connect testing session to your telegram account. 

That's it. Now you can `./run_tests.sh`

Running tests on a local machine is easy as hell.
1. install packages `pip install -r requirements.txt`
2. `./run_tests.sh` once on remote machine to generate `settings.json`
3. copy `settings.json` to your work directory
4. change `localhost` addresses in `settings.json` to your cluster machine's ones, change kubectl_proxy port to 8001
5. finally, run port forwardings with scripts in helpers directory `run_tests_fwd.sh`

this should start pods port forwarding and kubectl proxy.  

Now you should be able to run and debug tests locally.
