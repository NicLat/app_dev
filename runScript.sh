#!/bin/bash

echo -n "Repository: "
read repo
echo -n "Owner: "
read owner

echo "========="

java -jar catcher.jar "$owner" "$repo"
