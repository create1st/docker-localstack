#!/usr/bin/env bash

set -uexo pipefail

function rotate_secrets() {
  local AWS_SECRET_VALUE_JSON
  AWS_SECRET_VALUE_JSON=$(aws secretsmanager get-secret-value --secret-id "$1")
  local AWS_SECRET_VALUE
  AWS_SECRET_VALUE=$(echo "${AWS_SECRET_VALUE_JSON}" | jq -r ".SecretString | fromjson | .password")
  # modify resources using ${AWS_SECRET_VALUE}
  aws secretsmanager rotate-secret --secret-id "$1"
}

AWS_IAM_ROLE="$1"
AWS_SECRET_ID="MY_SECRET"

SCRIPTS_HOME=$(dirname "$0")
# shellcheck source=formatting.sh
source "${SCRIPTS_HOME}"/formatting.sh
# shellcheck source=aws-assume-role.sh
source "${SCRIPTS_HOME}"/aws-assume-role.sh "${AWS_IAM_ROLE}"

echo "Rotating secret ${BOLD}${AWS_SECRET_ID}${NORMAL}"
rotate_secrets "${AWS_SECRET_ID}"
