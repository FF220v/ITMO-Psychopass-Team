echo 'machine github.com login $GIT_LOGIN password $GIT_KEY' > ~/_netrc
git config --global user.email $GIT_EMAIL
git config --global user.name "$GIT_USERNAME