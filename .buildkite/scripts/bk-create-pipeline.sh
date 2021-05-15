#!/usr/bin/env bash

set -uexo pipefail

PROFILE=$(buildkite-agent meta-data get PROFILE)
AWS_APP=$(buildkite-agent meta-data get AWS_APP)

# shellcheck disable=SC1090,SC1091
source "${SCRIPTS_PATH}"/bk-set-env.sh "${PROFILE}"

export AWS_APP

TEMPLATE_NAME="${1}"
ESC_DOLLAR='$' envsubst < "${SCRIPTS_PATH}/buildkite-templates/${TEMPLATE_NAME}"
