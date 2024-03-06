#!/bin/bash

deploy()
{
  echo "Deploying jar file to mvn central"
  mvn clean compile deploy -DskipTestsKKH D
}

cd ..
pwd

deploy

echo "<<< complete >>>"
