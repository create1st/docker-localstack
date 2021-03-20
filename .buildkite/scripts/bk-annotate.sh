#!/bin/bash

set -uexo pipefail

REASON=$(buildkite-agent meta-data get "DEVOPS_ACTION_REASON")

echo ${REASON} | buildkite-agent annotate --append