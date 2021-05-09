#!/usr/bin/env bash

set -ueo pipefail

LATEST_TAG=$(git describe --tags --match v* --abbrev=0)
git diff --name-only "$LATEST_TAG"