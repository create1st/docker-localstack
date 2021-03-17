#!/bin/bash
set -uex
terraform init -backend=true -backend-config=${TF_PATH}/hcl/${ENV_PROFILE}.hcl ${TF_PATH}
terraform apply tf-plan-${ENV_PROFILE}
