#!/bin/sh -eu

download() {
    language="$1"
    version="$2"

    if [ ! -d "model" ]; then
        if [ ! -f "model.zip" ]; then
            wget "https://alphacephei.com/vosk/models/vosk-model-small-$language-$version.zip" -O model.zip
        fi

        unzip model.zip
        mv "vosk-model-small-$language-$version" model
        rm model.zip
    fi
}


# see https://alphacephei.com/vosk/models
# remove the folder contents before changing models!

cd app/src/main/assets
download "en-us" "0.15"
# download "tr" "0.3"
