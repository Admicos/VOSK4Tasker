#!/bin/sh -eu

sed -i "s/li.ebc.vosk4tasker/$1/g" app/build.gradle.kts
