#!/bin/bash

mkdir -p /tmp/build
helm template $1 --output-dir /tmp/build
mkdir -p ~/.kube
mv /tmp/build/helmchart/templates/kubeconfig.yaml ~/.kube/config
rm -fR /tmp/build
