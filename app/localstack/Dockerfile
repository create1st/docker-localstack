FROM amazon/aws-cli:2.1.35
COPY scripts /scripts
ENV PATH="/scripts:${PATH}"
WORKDIR /
ENTRYPOINT [ "sh", "scripts/entrypoint.sh"]
