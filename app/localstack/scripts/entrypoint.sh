#!/usr/bin/env bash

function create_key() {
  aws kms create-key \
    --endpoint-url="$KMS_ENDPOINT"
}

function get_kms_key_id() {
  echo "$1" | jq -r '.KeyMetadata.KeyId'
}

function create_kms_key_alias() {
  aws kms create-alias \
    --endpoint-url="$KMS_ENDPOINT" \
    --target-key-id "$1" \
    --alias-name "$2"
}

function create_key_with_alias() {
  KMS_KEY=$(create_key)
  echo "$KMS_KEY"
  create_kms_key_alias "$(get_kms_key_id "$KMS_KEY")" "$1"
}

function list_kms_aliases() {
  aws kms list-aliases \
    --endpoint-url="$KMS_ENDPOINT"
}

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

function create_topic() {
  aws sns create-topic \
    --endpoint-url="$SNS_ENDPOINT" \
    --name "$1"
}

function create_topic_subscription() {
  aws sns subscribe \
    --endpoint-url="$SNS_ENDPOINT" \
    --protocol sqs \
    --topic-arn arn:aws:sns:"${AWS_DEFAULT_REGION}":"${ACCOUNT_ID}":"$1" \
    --notification-endpoint arn:aws:sns:"${AWS_DEFAULT_REGION}":"${ACCOUNT_ID}":"$2" \
    --attributes RawMessageDelivery=true
}

function create_dynamodb_table() {
  aws dynamodb create-table \
    --endpoint-url="$DYNAMODB_ENDPOINT" \
    --table-name="$1" \
    --attribute-definitions \
    "$2" \
    "$3" \
    --key-schema \
    "$4" \
    "$5" \
    --global-secondary-indexes "$6" \
    --provisioned-throughput="ReadCapacityUnits=1000,WriteCapacityUnits=1000"
}

SCRIPTS_PATH=$(dirname "$0")
CREDENTIALS_PATH=${CREDENTIALS_PATH:-scripts}

echo "####################################################"
echo "PWD=$PWD"
echo "PATH=$PATH"
echo "ACCOUNT_ID=$ACCOUNT_ID"
echo "AWS_DEFAULT_REGION=$AWS_DEFAULT_REGION"
echo "DYNAMODB_ENDPOINT=$DYNAMODB_ENDPOINT"
echo "KMS_ENDPOINT=$KMS_ENDPOINT"
echo "SMS_ENDPOINT=$SMS_ENDPOINT"
echo "SNS_ENDPOINT=$SNS_ENDPOINT"
echo "SQS_ENDPOINT=$SQS_ENDPOINT"
echo "####################################################"

"${SCRIPTS_PATH}"/wait_for_service.sh "dynamodb" "$DYNAMODB_ENDPOINT"
"${SCRIPTS_PATH}"/wait_for_service.sh "kms" "$KMS_ENDPOINT"
"${SCRIPTS_PATH}"/wait_for_service.sh "secretsmanager" "$SMS_ENDPOINT"
"${SCRIPTS_PATH}"/wait_for_service.sh "sns" "$SNS_ENDPOINT"
"${SCRIPTS_PATH}"/wait_for_service.sh "sqs" "$SQS_ENDPOINT"

create_secret "craftandtechnology/docker_localstack_db/docker_localstack" "file://${CREDENTIALS_PATH}/credentials.json"
create_key_with_alias "alias/docker-localstack-kms-key"
list_kms_aliases
create_topic "docker-localstack-topic"
create_queue "docker-localstack-queue"
create_topic_subscription "docker-localstack-topic" "docker-localstack-queue"
create_dynamodb_table "docker-localstack-order-table" \
  "AttributeName=userId,AttributeType=S" \
  "AttributeName=transactionId,AttributeType=S" \
  "AttributeName=userId,KeyType=HASH" \
  "AttributeName=transactionId,KeyType=RANGE" \
  "IndexName=transactionIndex,KeySchema=[{AttributeName=transactionId,KeyType=HASH}],Projection={ProjectionType=ALL},ProvisionedThroughput={ReadCapacityUnits=10,WriteCapacityUnits=10}"
