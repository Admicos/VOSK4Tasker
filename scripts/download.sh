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
