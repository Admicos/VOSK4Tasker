#!/bin/sh -eu

sed -i "s/li.ebc.vosk4tasker/$1/g" app/build.gradle.kts
sed -i "s/li.ebc.vosk4tasker/$1/g" app/src/main/AndroidManifest.xml
