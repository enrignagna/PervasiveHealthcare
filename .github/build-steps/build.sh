#!/bin/bash
set -e
if [ -x 'gradlew' ]; then
    echo 'Detected gradle wrapper, checking for known tasks'
    if ./gradlew tasks | grep '^assemble\s'; then
        echo 'Detected assemble task'
        ./gradlew assemble --parallel
    elif ./gradlew tasks | grep '^build\s'; then
        echo 'Detected build task'
        ./gradlew build --parallel
    else
        echo 'No known tasks, fall back to the default tasks'
        ./gradlew
    fi
else
    echo 'No valid configuration detected, failing'
    echo "To fix, provide an *executable* build script "
    exit 1
fi
