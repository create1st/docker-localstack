#!/bin/bash

export TERM=xterm-256color
BOLD=$(tput bold)
export BOLD
NORMAL=$(tput sgr0)
export NORMAL
export RED='\033[0;31m'
export GREEN='\033[0;32m'
export YELLOW='\033[0;33m'
export NC='\033[0m' # No Color