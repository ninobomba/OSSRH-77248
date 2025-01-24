#!/bin/bash

# Check on the pom.xml and here
RELEASE_VERSION="1.0.0.17"

cleanup()
{
  echo "build::cleanup() -> Executing maven home cleanup for this package"

    mvn clean
    mvn dependency:purge-local-repository -DmanualInclude="io.github.ninobomba:t4m-commons:$RELEASE_VERSION"
    rm -rf ~/.m2/repository/io/github/ninobomba

  echo "build::cleanup() -> clean up is complete"
}

package()
{
  echo "build::package() -> packing jar file"

    mvn package -DskipTests
    mvn install -DskipTests

  echo "build::package() -> packing jar file is complete"
}

# deploys to .m2 directory
deploy()
{
  echo "build::deploy() -> Deploying jar file locally"
  mvn install:install-file -Dfile=target/t4m-commons-$RELEASE_VERSION.jar -DgroupId=io.github.ninobomba -DartifactId=t4m-commons -Dversion=$RELEASE_VERSION -Dpackaging=jar -DgeneratePom=true-Dpackaging=jar -DgeneratedPom=true -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false -DupdateReleaseInfo=true
  echo "build::deploy() -> Deploying jar file locally is complete"
}

#################################################
cd ..
pwd

echo "<<<<<<<<<< ############## cleanup ############## >>>>>>>>>>>"
cleanup

echo "<<<<<<<<<< ############## package ############## >>>>>>>>>>>"
package

echo "<<<<<<<<<< ############## deploy ############## >>>>>>>>>>>"
deploy

echo "<<< complete >>>"

# https://central.sonatype.com/artifact/io.github.ninobomba/t4m-commons
