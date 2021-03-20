#!/bin/bash

set -uexo pipefail

AWS_SECRET_ID="docker-localstack-secret"
AWS_ROLE_NAME="docker-localstack-role"
AWS_IAM_ACCOUNT_DEV="AWS_IAM_ACCOUNT_DEV"

PROFILE_DEV="dev"

ACTION_AWS_ROTATE_SECRETS="rotate-secrets"

PROFILE=$(buildkite-agent meta-data get "DEVOPS_PROFILE")
ACTION=$(buildkite-agent meta-data get "DEVOPS_ACTION")

echo "Executing '${ACTION}' on '${PROFILE}'"

case ${PROFILE} in
${PROFILE_DEV})
  AWS_IAM_ACCOUNT=${AWS_IAM_ACCOUNT_DEV}
  ;;

*)
  echo "Unknown profile: '${PROFILE}'"
  exit 1
  ;;
esac

echo "Build: ${BUILDKITE_BUILD_NUMBER}"

AWS_IAM_ROLE="arn:aws:iam::${AWS_IAM_ACCOUNT}:role/${AWS_ROLE_NAME}"
AWS_ROLE_JSON=$(aws sts assume-role --role-arn ${AWS_IAM_ROLE} --role-session-name "buildkite-pipeline-devops-build-${BUILDKITE_BUILD_NUMBER}" --query Credentials)
export AWS_SESSION_TOKEN=$(echo ${AWS_ROLE_JSON} | jq -r .Credentials.SessionToken)
export AWS_ACCESS_KEY_ID=$(echo ${AWS_ROLE_JSON} | jq -r .Credentials.AccessKeyId)
export AWS_SECRET_ACCESS_KEY=$(echo ${AWS_ROLE_JSON} | jq -r .Credentials.SecretAccessKey)

echo "Using AWS credentials:"
echo "  AWS_SESSION_TOKEN=${AWS_SESSION_TOKEN}"
echo "  AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}"
echo "  AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}"

case ${ACTION} in
${ACTION_AWS_ROTATE_SECRETS})
  AWS_SECRET_VALUE_JSON=$(aws secretsmanager get-secret-value --secret-id ${AWS_SECRET_ID})
  AWS_SECRET_VALUE=$(echo ${AWS_SECRET_VALUE_JSON} | jq -r ".SecretString | fromjson | .password")
  # modify resources using ${AWS_SECRET_VALUE}
  aws secretsmanager rotate-secret --secret-id ${AWS_SECRET_ID}
  ;;

*)
  echo "Unsupported operation: '${ACTION}'"
  exit 1
  ;;
esac