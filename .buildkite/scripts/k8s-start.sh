#!/usr/bin/env bash

K8S_NODE_GROUP_NAME="docker-localstack-k8s-node-group"
K8S_NODE_GROUP_SIZE=5

eksctl get cluster
eksctl get nodegroup --cluster ${K8S_CLUSTER_NAME}
eksctl scale nodegroup --cluster ${K8S_CLUSTER_NAME} --name ${K8S_NODE_GROUP_NAME} --nodes ${K8S_NODE_GROUP_SIZE}