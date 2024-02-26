#!/bin/bash


function waitForService() {
  echo "host: $1"
  echo "port: $2"
  echo "service name: $3"

  while ! nc -z $1 $2; do
    echo 'wait for service'
    sleep 1
  done

  echo 'service is running'
}
