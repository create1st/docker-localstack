<div align="center">    
 <img src="https://img.shields.io/github/license/create1st/docker-localstack.svg" align="left" />
 <img src="https://img.shields.io/badge/Docker-blue.svg" align="left" />
 <img src="https://img.shields.io/badge/localstack-orange.svg" align="left" />
 <img src="https://img.shields.io/badge/Terraform-blueviolet.svg" align="left" />
 <img src="https://img.shields.io/badge/Buildkite-green.svg" align="left" />
 <img src="https://img.shields.io/badge/PRs-welcome-green.svg" align="left" />
</div>

# docker-localstack
Docker + localstack + Buildkite

``` bash
docker network create docker-localstack-network
docker-compose -f docker-compose-localstack.yaml up

eval `ssh-agent`
export TF_CLI_ARGS_apply="-no-color"
export TF_CLI_ARGS_plan="-no-color"
bk run -E SSH_AUTH_SOCK="$SSH_AUTH_SOCK"
bk run .buildkite/pipeline-aws-ops.yaml
bk run .buildkite/pipeline-k8s-ops.yaml
```

### Buildkite
* https://github.com/chronotc/monorepo-diff-buildkite-plugin
* https://github.com/buildkite-plugins/docker-compose-buildkite-plugin
* https://github.com/buildkite-plugins/artifacts-buildkite-plugin
* https://github.com/cultureamp/aws-assume-role-buildkite-plugin
  https://github.com/buildkite-plugins/ecr-buildkite-plugin

### Docker

