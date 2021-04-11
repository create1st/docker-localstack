#!/bin/bash

function create_secret() {
    aws secretsmanager create-secret \
      --endpoint-url="$SMS_ENDPOINT" \
      --name "$1" \
      --secret-string "$2"
}

function create_queue() {
    aws sqs create-queue \
      --cli-connect-timeout=2 \
      --cli-read-timeout=2 \
      --endpoint-url="$SQS_ENDPOINT" \
      --queue-name "$1" \
      --attributes="DelaySeconds=0,VisibilityTimeout=0,MessageRetentionPeriod=86400"
}

echo "####################################################"
echo "PWD=$PWD"
echo "PATH=$PATH"
echo "SMS_ENDPOINT=$SMS_ENDPOINT"
echo "SQS_ENDPOINT=$SQS_ENDPOINT"
echo "####################################################"


wait_for_service.sh "secretsmanager" "$SMS_ENDPOINT"
wait_for_service.sh "sqs" "$SQS_ENDPOINT"

create_secret "create1st/docker_localstack_db/docker_localstack" "file://scripts/credentials.json"
create_queue "docker-localstack-queue"
