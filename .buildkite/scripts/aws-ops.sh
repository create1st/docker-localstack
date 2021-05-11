#!/usr/bin/env bash

set -uexo pipefail

AWS_ACTION_ROTATE_SECRETS="rotate-secrets"
AWS_ACTION=$(buildkite-agent meta-data get "AWS_ACTION")

echo "Executing '${AWS_ACTION}'"

SCRIPTS_HOME=$(dirname "$0")
# shellcheck source=aws-assume-role.sh
source "${SCRIPTS_HOME}"/aws-assume-role.sh

case ${AWS_ACTION} in
"${AWS_ACTION_ROTATE_SECRETS}")
  AWS_SECRET_VALUE_JSON=$(aws secretsmanager get-secret-value --secret-id "${AWS_SECRET_ID}")
  AWS_SECRET_VALUE=$(echo "${AWS_SECRET_VALUE_JSON}" | jq -r ".SecretString | fromjson | .password")
  # modify resources using ${AWS_SECRET_VALUE}
  aws secretsmanager rotate-secret --secret-id "${AWS_SECRET_ID}"
  ;;

*)
  echo "Unsupported operation: '${AWS_ACTION}'"
  exit 1
  ;;
esac