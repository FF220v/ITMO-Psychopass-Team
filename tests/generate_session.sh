pushd ~/ITMO-Psychopass-Team/tests
  ./build.sh
  pushd session_generator
    ./run.sh
  popd
popd
