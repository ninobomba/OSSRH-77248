#!/bin/bash

   # Define some variables
   DOCS_DIR="target/site/apidocs"  # Update this path if different
   BRANCH="ninobomba.github.io"
   REPO=$(git config remote.origin.url)

   cd ..

   # Generate Javadocs (for Maven, you may need to replace this with your task for Gradle)
   mvn javadoc:javadoc

   # Clone the repository
   git clone $REPO out
   cd out

   # Create or checkout the gh-pages branch
   git checkout $BRANCH || git checkout --orphan $BRANCH

   # Remove existing contents
   rm -rf *

   # Copy generated Javadocs
   cp -r ../$DOCS_DIR/* .

   # Commit and push
   git add --all
   git commit -m "Update Javadocs"
   git push origin $BRANCH

   # Clean up
   cd ..
   rm -rf out
