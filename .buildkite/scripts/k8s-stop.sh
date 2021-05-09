#!/usr/bin/env bash

SCRIPTS_HOME=$(dirname "$0")
K8S_NODE_GROUP_NAME="docker-localstack-k8s-node-group"

eksctl get cluster
eksctl get nodegroup --cluster ${K8S_CLUSTER_NAME}
eksctl scale nodegroup --cluster ${K8S_CLUSTER_NAME} --name ${K8S_NODE_GROUP_NAME} --nodes 0