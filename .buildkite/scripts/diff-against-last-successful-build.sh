#!/usr/bin/env bash

set -uexo pipefail

LAST_SUCCESSFUL_BUILD_COMMIT="$(aws s3 cp "${S3_LAST_SUCCESSFUL_BUILD_COMMIT_PATH}" - | head -n 1)"
git diff --name-only "$LAST_SUCCESSFUL_BUILD_COMMIT"