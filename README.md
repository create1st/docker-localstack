# docker-localstack
Docker + localstack + Buildkite

``` bash
docker-compose up
eval `ssh-agent`
bk run -E SSH_AUTH_SOCK="$SSH_AUTH_SOCK"
```

### Buildkite
* https://github.com/chronotc/monorepo-diff-buildkite-plugin
* https://github.com/buildkite-plugins/docker-compose-buildkite-plugin
* https://github.com/buildkite-plugins/artifacts-buildkite-plugin

### Docker

