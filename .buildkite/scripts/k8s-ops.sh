#!/bin/bash

set -uexo pipefail

K8S_CLUSTER_NAME="docker-localstack-k8s-cluster"
K8S_NODE_GROUP_NAME="docker-localstack-k8s-node-group"
K8S_NODE_GROUP_SIZE="docker-localstack-k8s-node-group"

K8S_ACTION_START="start"
K8S_ACTION_STOP="stop"

K8S_ACTION=$(buildkite-agent meta-data get "K8S_ACTION")

echo "Executing '${K8S_ACTION}'"

.buildkite/scripts/aws-assume-role.sh

case ${K8S_ACTION} in
${K8S_ACTION_STOP})
  eksctl get cluster
  eksctl get nodegroup --cluster ${K8S_CLUSTER_NAME}
  eksctl scale nodegroup --cluster ${K8S_CLUSTER_NAME} --name ${K8S_NODE_GROUP_NAME} --nodes 0
  ;;

${K8S_ACTION_START})
  eksctl get cluster
  eksctl get nodegroup --cluster ${K8S_CLUSTER_NAME}
  eksctl scale nodegroup --cluster ${K8S_CLUSTER_NAME} --name ${K8S_NODE_GROUP_NAME} --nodes ${K8S_NODE_GROUP_SIZE}
  ;;

*)
  echo "Unsupported operation: '${K8S_ACTION}'"
  exit 1
  ;;
esac