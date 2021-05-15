#!/usr/bin/env bash

set -uexo pipefail

AWS_IAM_ROLE="${1}"

echo "Assuming role: '${AWS_IAM_ROLE}'"
echo "Build: ${BUILDKITE_BUILD_NUMBER}"

AWS_IAM_ROLE_JSON=$(aws sts assume-role --role-arn "${AWS_IAM_ROLE}" --role-session-name "buildkite-pipeline-devops-build-${BUILDKITE_BUILD_NUMBER}" --query Credentials)
AWS_SESSION_TOKEN=$(echo "${AWS_IAM_ROLE_JSON}" | jq -r .Credentials.SessionToken)
AWS_ACCESS_KEY_ID=$(echo "${AWS_IAM_ROLE_JSON}" | jq -r .Credentials.AccessKeyId)
AWS_SECRET_ACCESS_KEY=$(echo "${AWS_IAM_ROLE_JSON}" | jq -r .Credentials.SecretAccessKey)

export AWS_SESSION_TOKEN
export AWS_ACCESS_KEY_ID
export AWS_SECRET_ACCESS_KEY

echo "Using AWS credentials:"
echo "  AWS_SESSION_TOKEN=${AWS_SESSION_TOKEN}"
echo "  AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}"
echo "  AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}"