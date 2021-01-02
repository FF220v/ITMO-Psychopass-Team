pushd ~/ITMO-Psychopass-Team/tests
  ./build.sh
  pushd session_generator
    ./build.sh && ./run.sh
  popd
popd