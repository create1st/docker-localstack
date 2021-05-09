#!/usr/bin/env bash

set -uexo pipefail

export K8S_CONTEXT="${K8S_CLUSTER_NAME:-k8s}.${PROFILE:-dev}.craftandtechnology.cloud"
export K8S_NAMESPACE="${K8S_NAMESPACE:-default}"

echo "Using K8S context:"
echo "  K8S_CONTEXT=${K8S_CONTEXT}"
echo "  K8S_NAMESPACE=${K8S_NAMESPACE}"

kubectl config set-context "${K8S_CONTEXT}" --namespace="${K8S_NAMESPACE}"
kubectl config use-context "${K8S_CONTEXT}"