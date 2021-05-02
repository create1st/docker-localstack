#!/bin/bash

function wait_for_service() {
  echo "Connecting to $1 service at endpoint $2/health"
  until $(curl --silent --fail $2/health | grep "\"$1\": \"running\"" >/dev/null); do
    sleep 5
    echo "Waiting for $1 service to be ready..."
  done
  echo "$1 service is ready"
}

wait_for_service "$1" "$2"
