#!/usr/bin/env bash

echo "Running Instrumented Tests"
echo "--------------------------"
for i in 1 2 3; do
  if ./gradlew connectedCheck; then
    echo "Tests passed on attempt $i"
    break
  elif [ $i -lt 3 ]; then
    echo "Tests failed on attempt $i. Retrying..."
  else
    echo "Tests failed on attempt $i. Not retrying."
    exit 1
  fi
done
