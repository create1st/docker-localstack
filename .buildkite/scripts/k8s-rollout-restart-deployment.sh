#!/usr/bin/env bash

K8S_APP_NAME="docker-localstack-app"

k8s-set-context.sh

kubectl -n "${K8S_NAMESPACE}" rollout restart deployment ${K8S_APP_NAME}