#!/usr/bin/env bash

set -uex

terraform fmt -write=false -diff=true -check=true ${TF_PATH}