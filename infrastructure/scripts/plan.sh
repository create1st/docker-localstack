#!/bin/bash
set -uex
terraform init -backend=true -backend-config=${TF_PATH}/hcl/${ENV_PROFILE}.hcl ${TF_PATH}
terraform plan -var-file=${TF_PATH}/tfvars/${ENV_PROFILE}.tfvars -out=tf-plan-${ENV_PROFILE} ${TF_PATH}
