#!/bin/bash

# https://central.sonatype.com/artifact/io.github.ninobomba/t4m-commons/versions

# this script
# Remember to update the version on each line

# GPG key
# https://central.sonatype.org/pages/working-with-pgp-signatures.html
# gpg --gen-key
# gpg --list-secret-keys --keyid-format=long
# gpg --keyserver keyserver.ubuntu.com --send-keys <key-id>
# gpg --keyserver https://keys.openpgp.org --send-keys <key-id>

# Maven settings.xml
# https://central.sonatype.org/pages/apache-maven.html
# <server>
#   <id>ossrh</id>
#   <username>your-jira-id</username>
#   <password>your-jira-pwd</password>
#  </server>
#  https://issues.sonatype.org/login.jsp?os_destination=%2Fsecure%2FSignup%21default.jspa

cleanup()
{
  echo "Executing maven home cleanup for this package"
  mvn dependency:purge-local-repository -DmanualInclude="io.github.ninobomba:t4m-commons:1.0.0.2"
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
  mvn install:install-file -Dfile=target/t4m-commons-1.0.0.2.jar -DgroupId=io.github.ninobomba -DartifactId=t4m-commons -Dversion=1.0.0.2 -Dpackaging=jar -DgeneratedPom=true -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false -DupdateReleaseInfo=true
}

#################################################
cd ..
pwd

cleanup
package
deploy

echo "<<< complete >>>"
