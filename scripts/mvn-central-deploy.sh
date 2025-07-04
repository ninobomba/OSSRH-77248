#!/bin/bash

deploy()
{
  echo "Deploying jar file to mvn central"
  mvn deploy -DskipTests
}

cd ..
pwd

deploy

echo "<<< complete >>>"
