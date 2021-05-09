#!/usr/bin/env bash

set -uex

terraform init -backend=false ${TF_PATH}
terraform validate ${TF_PATH}