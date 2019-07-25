#!/bin/bash
set -x #echo on

##################################################
#### Verify

if [ "$#" -ne 2 ]; then
    echo "Usage ./release.sh {release_version} {next_version}"
    exit 2
fi

# Ensure to be on the develop branch
git checkout develop

##################################################
#### Release

# Prepare release branch
git checkout -b "rel_$1"

# Apply all necesssary version changes/fixtures
mvn versions:set -DnewVersion=$1

# Ensure it builds!
mvn clean install
if [[ "$?" -ne 0 ]] ; then
  mvn versions:revert
  git checkout develop
  git branch -d "rel_$1"

  echo 'release failed';
  exit -1
fi

# Commit changes
git commit -am "Candidate release $1"

##################################################
#### Rebase

# Rebase to ensure continuity
git checkout develop
git rebase "rel_$1"

##################################################
#### Move on

# Revert all necesssary version changes/fixtures
mvn versions:set -DnewVersion=$2

# Commit changes
git commit -am "Start version $2+"

