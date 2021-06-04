#!/usr/bin/env bash

export ACCOUNT_ID="000000000000"
export AWS_DEFAULT_REGION="us-east-1"
export DYNAMODB_ENDPOINT="http://localhost:4566"
export KMS_ENDPOINT="http://localhost:4566"
export SMS_ENDPOINT="http://localhost:4566"
export SNS_ENDPOINT="http://localhost:4566"
export SQS_ENDPOINT="http://localhost:4566"
export SCRIPTS_PATH="app/localstack/scripts/"
export CREDENTIALS_PATH="${SCRIPTS_PATH}"

"${SCRIPTS_PATH}"/entrypoint.sh
