#!/bin/bash

deploy()
{
  echo "Deploying jar file to mvn central"
  mvn clean compile deploy -DskipTests
}

cd ..
pwd

deploy

echo "<<< complete >>>"