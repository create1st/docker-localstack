#!/usr/bin/env bash

set -uexo pipefail

function delete_items() {
  local KEY_SCHEMA
  KEY_SCHEMA="$(aws dynamodb describe-table \
    --table-name "$1" | \
    jq -r '.Table.KeySchema[].AttributeName' | \
    tr '\n' ' ')"

  aws dynamodb scan \
   --table-name "$1" \
   --attributes-to-get "${KEY_SCHEMA}" | \
   jq -r ".Items[] | tojson" | \
   tr '\n' '\0' | \
   xargs -t -0 -I keyItem \
    aws dynamodb delete-item \
      --table-name "$1" \
      --key=keyItem
}

AWS_IAM_ROLE="$1"
DYNAMODB_TABLE_LIST_FILE="$2"
SCRIPTS_PATH=$(dirname "$0")
# shellcheck source=formatting.sh
source "${SCRIPTS_PATH}"/formatting.sh
# shellcheck source=aws-assume-role.sh
source "${SCRIPTS_HOME}"/aws-assume-role.sh "${AWS_IAM_ROLE}"

echo "Cleaning dynamodb tables from ${BOLD}${DYNAMODB_TABLE_LIST_FILE}${NORMAL}"
while IFS="" read -r TABLE_NAME || [ -n "${TABLE_NAME}" ]
do
  echo "Cleaning dynamodb ${BOLD}${TABLE_NAME}${NORMAL}"
  delete_items "${TABLE_NAME}"
done < "${DYNAMODB_TABLE_LIST_FILE}"