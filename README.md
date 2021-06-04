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

```shell
docker network create docker-localstack-network
docker-compose -f docker-compose-localstack.yaml up

eval `ssh-agent`
export TF_CLI_ARGS_apply="-no-color"
export TF_CLI_ARGS_plan="-no-color"
bk run -E SSH_AUTH_SOCK="$SSH_AUTH_SOCK"
bk run .buildkite/pipeline-aws-ops.yaml
bk run .buildkite/pipeline-k8s-ops.yaml
bk run .buildkite/pipeline-aws-ops-development.yaml
buildkite-agent start --spawn 5 --tags "queue=global,queue=infra,queue=dev-aws-ops,queue=dev-k8s-ops,queue=app"
```

```shell
cd app
docker-compose -f docker-compose-integration-tests.yml build 
docker-compose -f docker-compose-integration-tests.yml up localstack-setup postgresql
docker-compose -f docker-compose-integration-tests.yml run integration-test
```

```shell
cd app
docker-compose -f docker-compose-acceptance-tests.yml build 
docker-compose -f docker-compose-acceptance-tests.yml up localstack-setup postgresql
docker-compose -f docker-compose-acceptance-tests.yml run acceptance-test
```

```shell
bk run app/.buildkite/pipeline.yaml
bk run webapp/.buildkite/pipeline.yaml
```

# IntelliJ

```shell
cd app
docker-compose -f docker-compose-local.yml build
docker-compose -f docker-compose-local.yml up
```

# Run Web application

```shell
docker run -p 80:8080 docker-localstack-webapp:local-snapshot
```

# K8s

```shell
docker-comdocker -f .buildkite/image/docker-compose.yml build k8s-ci-cd
bk run .buildkite/pipeline-k8s-ci-di-image.yaml
```

Minikube
```shell
minikube start
kubect create -f localstack.pod.k8s.yaml
kubect create -f localstack.service.k8s.yaml
kubectl port-forward $(kubectl get pods | grep localstack | awk {'print $1}') 4566:4566
localstack.init.k8s.sh
```

### Buildkite

* https://github.com/chronotc/monorepo-diff-buildkite-plugin
* https://github.com/buildkite-plugins/docker-compose-buildkite-plugin
* https://github.com/buildkite-plugins/artifacts-buildkite-plugin
* https://github.com/cultureamp/aws-assume-role-buildkite-plugin
* https://github.com/buildkite-plugins/ecr-buildkite-plugin
* https://github.com/coyainsurance/s3-cache-buildkite-plugin/tags
* https://github.com/thedyrt/change-directory-buildkite-plugin
* https://github.com/buildkite-plugins/shellcheck-buildkite-plugin

### Docker

```shell
export DOCKER_BUILDKIT=0
export COMPOSE_DOCKER_CLI_BUILD=0
```

# AWS

Rotate password
https://docs.aws.amazon.com/secretsmanager/latest/userguide/tutorials_db-rotate.html
