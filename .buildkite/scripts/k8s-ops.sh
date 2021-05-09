#!/usr/bin/env bash

set -uexo pipefail

K8S_ACTION_START="start"
K8S_ACTION_STOP="stop"

K8S_ACTION=$(buildkite-agent meta-data get "K8S_ACTION")

echo "Executing '${K8S_ACTION}'"

SCRIPTS_HOME=$(dirname "$0")

"${SCRIPTS_HOME}"/aws-assume-role.sh
# Optional login to ECR

case ${K8S_ACTION} in
${K8S_ACTION_STOP})
  K8S_ACTION=${K8S_ACTION} docker-compose -f "${SCRIPTS_HOME}"/docker-compose-k8s-ops.yml run apply
  ;;

${K8S_ACTION_START})
  K8S_ACTION=${K8S_ACTION} docker-compose -f "${SCRIPTS_HOME}"/docker-compose-k8s-ops.yml run apply
  ;;
*)
  echo "Unsupported operation: '${K8S_ACTION}'"
  exit 1
  ;;
esac
