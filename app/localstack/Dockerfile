FROM amazon/aws-cli:2.1.35

ENV PATH="/scripts:${PATH}"

WORKDIR /

RUN yes y | yum install jq

COPY scripts /scripts

ENTRYPOINT [ "sh", "scripts/entrypoint.sh"]
