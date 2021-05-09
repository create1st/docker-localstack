#!/usr/bin/env bash

set -uexo pipefail

AWS_IAM_ROLE_NAME="${AWS_IAM_ROLE_NAME:-docker-localstack-aws-role}"
AWS_IAM_ACCOUNT=$(buildkite-agent meta-data get "AWS_IAM_ACCOUNT")

echo "Assuming role: '${AWS_IAM_ACCOUNT}'"
echo "Build: ${BUILDKITE_BUILD_NUMBER}"

AWS_IAM_ROLE="arn:aws:iam::${AWS_IAM_ACCOUNT}:role/${AWS_IAM_ROLE_NAME}"
AWS_IAM_ROLE_JSON=$(aws sts assume-role --role-arn "${AWS_IAM_ROLE}" --role-session-name "buildkite-pipeline-devops-build-${BUILDKITE_BUILD_NUMBER}" --query Credentials)
export AWS_SESSION_TOKEN=$(echo "${AWS_IAM_ROLE_JSON}" | jq -r .Credentials.SessionToken)
export AWS_ACCESS_KEY_ID=$(echo "${AWS_IAM_ROLE_JSON}" | jq -r .Credentials.AccessKeyId)
export AWS_SECRET_ACCESS_KEY=$(echo "${AWS_IAM_ROLE_JSON}" | jq -r .Credentials.SecretAccessKey)

echo "Using AWS credentials:"
echo "  AWS_SESSION_TOKEN=${AWS_SESSION_TOKEN}"
echo "  AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}"
echo "  AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}"