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

# https://central.sonatype.com/artifact/io.github.ninobomba/t4m-commons
