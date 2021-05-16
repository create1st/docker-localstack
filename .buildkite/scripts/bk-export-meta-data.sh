#!/usr/bin/env bash

set -uexo pipefail

DEFAULT_VALUE="META_DATA_NOT_SET"
ENV_FILE="/tmp/buildkite-env-${BUILDKITE_BUILD_NUMBER}"

for META_DATA_NAME in "$@"
do
    META_DATA_VALUE=$(buildkite-agent meta-data get ${META_DATA_NAME} --default ${DEFAULT_VALUE})
    echo "${META_DATA_NAME}=${META_DATA_VALUE}" >> "${ENV_FILE}"
    echo "export ${META_DATA_NAME}" >> "${ENV_FILE}"
done

source "${ENV_FILE}"

rm "${ENV_FILE}"