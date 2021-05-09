#!/usr/bin/env bash

set -uexo pipefail

REASON=$(buildkite-agent meta-data get "BK_ACTION_REASON")

echo ${REASON} | buildkite-agent annotate --append