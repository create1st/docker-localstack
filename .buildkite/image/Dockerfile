FROM alpine:3.13.5

ARG AWS_IAM_AUTHENTICATOR=0.5.3

VOLUME /var/run/docker.sock

RUN apk add --update ca-certificates \
    && apk update \
    && apk add \
        bash \
        curl \
        docker \
        git \
        go \
        jq \
        openssh-keygen \
        openssl \
        py3-pip \
        python3

# AWS CLI
RUN pip3 install --upgrade awscliv2 \
    && ln -s $(which awscliv2) /usr/local/bin/aws

# Heptio AWS IAM Authenticator for K8s
RUN curl -LO "https://github.com/kubernetes-sigs/aws-iam-authenticator/releases/download/v${AWS_IAM_AUTHENTICATOR}/aws-iam-authenticator_${AWS_IAM_AUTHENTICATOR}_linux_amd64" \
    && curl -LO "https://github.com/kubernetes-sigs/aws-iam-authenticator/releases/download/v${AWS_IAM_AUTHENTICATOR}/authenticator_${AWS_IAM_AUTHENTICATOR}_checksums.txt" \
    && echo "$(cat authenticator_${AWS_IAM_AUTHENTICATOR}_checksums.txt | grep linux_amd64)" \
    && sha256sum "aws-iam-authenticator_${AWS_IAM_AUTHENTICATOR}_linux_amd64" \
    && echo "$(cat authenticator_${AWS_IAM_AUTHENTICATOR}_checksums.txt | grep linux_amd64)" | sha256sum -c \
    && chmod u+x "aws-iam-authenticator_${AWS_IAM_AUTHENTICATOR}_linux_amd64" \
    && mv "aws-iam-authenticator_${AWS_IAM_AUTHENTICATOR}_linux_amd64" /usr/local/bin/aws-iam-authenticator

# Kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" \
    && curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl.sha256" \
    && echo "$(cat kubectl.sha256)  kubectl" \
    && sha256sum kubectl \
    && echo "$(cat kubectl.sha256)  kubectl" | sha256sum -c \
    && chmod u+x kubectl \
    && mv kubectl /usr/local/bin/kubectl

# Helm
RUN curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3 \
    && chmod u+x get_helm.sh \
    && ./get_helm.sh

# Create K8s config
COPY helmchart /root/helmchart
COPY scripts/kubeconfig.sh /usr/local/kubeconfig.sh
RUN /usr/local/kubeconfig.sh /root/helmchart \
    && cat /root/.kube/config \
    && rm -fR \
        /root/helmchart \
        /usr/local/kubeconfig.sh
