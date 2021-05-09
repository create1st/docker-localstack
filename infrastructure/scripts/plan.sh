#!/usr/bin/env bash

set -uex

terraform init -backend=true -backend-config=${TF_PATH}/hcl/${PROFILE}.hcl ${TF_PATH}
terraform plan -var-file=${TF_PATH}/tfvars/${PROFILE}.tfvars -out=tf-plan-${PROFILE} ${TF_PATH}
