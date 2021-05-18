#!/usr/bin/env bash

set -uexo pipefail

function die() {
    echo >&2 "$@"
    exit 1
}

function validate() {
  local ARGUMENT=$1
  [[ "${ARGUMENT}" =~ ^[A-Za-z0-9_-]+$ ]] || die "Invalid argument: ${ARGUMENT}"
}

function set_environment() {
  local ENV_NAME=$1
  local ENV_VALUE=$2
  source /dev/stdin <<EOF
    export ${ENV_NAME}=${ENV_VALUE}
EOF
}

DEFAULT_VALUE="META_DATA_NOT_SET"
ENV_FILE="/tmp/buildkite-env-${BUILDKITE_BUILD_NUMBER}"

for META_DATA_NAME in "$@"
do
    META_DATA_VALUE=$(buildkite-agent meta-data get ${META_DATA_NAME} --default ${DEFAULT_VALUE})
    validate "${META_DATA_NAME}"
    validate "${META_DATA_VALUE}"
    set_environment "${META_DATA_NAME}" "${META_DATA_VALUE}"
done