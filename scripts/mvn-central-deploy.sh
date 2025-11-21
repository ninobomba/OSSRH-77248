#!/bin/bash

deploy()
{
  echo "Deploying jar file to mvn central"
  mvn deploy -DskipTests
}


deploy

echo "<<< complete >>>"
