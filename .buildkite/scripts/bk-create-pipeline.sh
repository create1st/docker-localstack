#!/usr/bin/env bash

set -uexo pipefail

SCRIPTS_HOME=$(dirname "$0")

# shellcheck source=bk-export-meta-data.sh
source "${SCRIPTS_HOME}"/bk-export-meta-data.sh PROFILE AWS_APP TEMPLATE_NAME
# shellcheck source=bk-set-env.sh
source "${SCRIPTS_HOME}"/bk-set-env.sh "${PROFILE}"

ESC_DOLLAR='$' envsubst < "${SCRIPTS_HOME}/buildkite-templates/${TEMPLATE_NAME}.yaml"
