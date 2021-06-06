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
docker run -p 80:8080 docker-localstack-webapp:latest
```

# K8s

```shell
docker-comdocker -f .buildkite/image/docker-compose.yml build k8s-ci-cd
bk run .buildkite/pipeline-k8s-ci-di-image.yaml
```

# Build

JIB app

```shell
cd app
gradle clean build test jib -x integrationTest -x acceptanceTest
docker image rm craftandtechnology/docker-localstack:latest
#docker pull craftandtechnology/docker-localstack:latest
docker-compose up
docker-compose rm -f
docker image rm craftandtechnology/docker-localstack:latest
```

webapp

```shell
cd webapp
docker-compose build
```

https-proxy sidecar

```shell
cd https-proxy
mkdir -p "$(pwd)/nginx/etc/ssl/private"
mkdir -p "$(pwd)/nginx/etc/ssl/certs"
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout "$(pwd)/nginx/etc/ssl/private/nginx-selfsigned.key" -out "$(pwd)/nginx/etc/ssl/certs/nginx-selfsigned.crt"
openssl dhparam -out /etc/ssl/certs/dhparam.pem 2048
docker-compose build
docker push craftandtechnology/https-proxy:latest
```

# Minikube

localstack

```shell
minikube start
kubect create -f localstack.pod.k8s.yaml
kubect create -f localstack.service.k8s.yaml
kubectl port-forward $(kubectl get pods | grep "^localstack" | awk {'print $1}') 4566:4566
localstack.init.k8s.sh
```

postgres

```shell
kubect create -f postgres.configmap.k8s.yaml
kubect create -f postgres.pv.k8s.yaml
kubect create -f postgres.pod.k8s.yaml
kubect create -f postgres.service.k8s.yaml
```

app

```shell
kubect create -f app.pod.k8s.yaml
kubect create -f app.service.k8s.yaml
# Port forwarding to check service
kubectl port-forward $(kubectl get pods | grep "docker-localstack" | grep -v "webapp" | awk {'print $1}') 8080:8080
# Check logs
kubectl logs -f -l app=docker-localstack -c docker-localstack
```

webapp

```shell
docker push craftandtechnology/docker-localstack-webapp:latest
kubect create -f webapp.pod.k8s.yaml
kubectl create -f webapp.service.k8s.yaml
# Port forwarding to check service
kubectl port-forward $(kubectl get pods | grep "docker-localstack-webapp" | awk {'print $1}') 8080:8080
# Check logs
kubectl logs -f -l app=docker-localstack-webapp -c docker-localstack-webapp
# Create tunnel for load balancer
minikube tunnel
```

# Running

Self-signed certificate
=> https://stackoverflow.com/questions/35274659/when-you-use-badidea-or-thisisunsafe-to-bypass-a-chrome-certificate-hsts-err

```shell
curl --insecure https://localhost:8443/rest/orders
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
