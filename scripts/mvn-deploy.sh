#!/bin/bash

cleanup()
{
  echo "Executing maven home cleanup for this package"
  mvn dependency:purge-local-repository -DmanualInclude="io.github.ninobomba:t4m-commons:1.0"
  rm -rf ~/.m2/repository/io/github/ninobomba
}

package()
{
  echo "Packing jar file"
  mvn clean package install -DskipTests
}

deploy()
{
  echo "Deploying jar file"
  mvn install:install-file -Dfile=target/t4m-commons-1.0.jar -DgroupId=io.github.ninobomba -DartifactId=t4m-commons -Dversion=1.0 -Dpackaging=jar -DgeneratedPom=true -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false -DupdateReleaseInfo=true
}

#################################################
cd ..
pwd

cleanup
package
deploy

echo "<<< complete >>>"
