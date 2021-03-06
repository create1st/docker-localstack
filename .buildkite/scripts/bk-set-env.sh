#!/usr/bin/env bash

set -uexo pipefail

PROFILE="$1"

declare -A AWS_ACCOUNT_IDS
AWS_ACCOUNT_IDS=([${PROFILE_DEV}]=${AWS_IAM_ACCOUNT_DEV} [${PROFILE_PRD}]=${AWS_IAM_ACCOUNT_PRD})
declare -A PROFILE_NAMES
PROFILE_NAMES=([${PROFILE_DEV}]="Development" [${PROFILE_PRD}]="Production")
declare -A BK_AGENTS
BK_AGENTS=([${PROFILE_DEV}]=${BK_AGENT_DEV} [${PROFILE_PRD}]=${BK_AGENT_PRD})

export AWS_ACCOUNT_ID="${AWS_ACCOUNT_IDS[${PROFILE}]}"
export PROFILE_NAME="${PROFILE_NAMES[${PROFILE}]}"
export BK_AGENT="${BK_AGENTS[${PROFILE}]}"

echo "AWS_ACCOUNT_ID: ${AWS_ACCOUNT_ID}"
echo "PROFILE_NAME: ${PROFILE_NAME}"
echo "BK_AGENT: ${BK_AGENT}"