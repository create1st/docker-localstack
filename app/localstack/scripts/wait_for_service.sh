#!/usr/bin/env bash

function wait_for_service() {
  local SERVICE=$1
  local HOST=$2
  local MAX_RETRIES=$3
  local HEALTH_ENDPOINT="${HOST}/health"
  local RETRY_COUNT=1
  local RUNNING
  echo "Connecting to ${SERVICE} service at endpoint ${HEALTH_ENDPOINT}"
  while
    curl --silent --fail "${HEALTH_ENDPOINT}" | grep "\"${SERVICE}\": \"running\"" >/dev/null
    RUNNING=$?
    if [[ "${RUNNING}" -ne 0 ]]; then
      sleep 5
      echo "Waiting for $1 service to be ready. Retrying ${RETRY_COUNT}..."
    fi
    ((RETRY_COUNT=RETRY_COUNT+1))
    [[ "${RUNNING}" -ne 0 && "${RETRY_COUNT}" -lt ${MAX_RETRIES} ]]
  do :; done
  if [[ "${RUNNING}" -eq 0 ]]; then
    echo "${SERVICE} service is ready"
  else
    echo "${SERVICE} service is NOT ready"
  fi
}

SERVICE=$1
HOST=$2
wait_for_service "${SERVICE}" "${HOST}" 15
