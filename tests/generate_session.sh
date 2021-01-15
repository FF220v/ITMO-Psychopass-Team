pushd ~/ITMO-Psychopass-Team/tests
  ./build.sh
  ./make_test_settings.sh
  pushd session_generator
    ./run.sh
  popd
popd
