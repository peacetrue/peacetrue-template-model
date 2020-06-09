#!/bin/bash

env=${1-default}

./kill.sh

./gradlew clean
./gradlew bootJar

module="${project-name}-app"
cd "$module" || exit
echo "run jar $module at $(pwd)"
java -jar "build/libs/$module-1.0.0-SNAPSHOT.jar" --spring.profiles.active="$env,log" &

