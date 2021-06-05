FROM nginx:1.20.0-alpine

LABEL Name="https-proxy"

# Disable startup logs
# ENV NGINX_ENTRYPOINT_QUIET_LOGS=1

# Nginx configuration
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./nginx/conf.d/https-proxy.conf /etc/nginx/conf.d/default.conf
COPY ./nginx/etc/ssl/certs/nginx-selfsigned.crt /etc/ssl/certs/nginx-selfsigned.crt
COPY ./nginx/etc/ssl/private/nginx-selfsigned.key /etc/ssl/private/nginx-selfsigned.key
COPY ./nginx/etc/ssl/certs/dhparam.pem /etc/ssl/certs/dhparam.pem

# Nginx unprivileged user permissions
RUN chown -R nginx:nginx /etc/nginx \
    && chmod -R g+w /etc/nginx \
    && chown -R nginx:nginx /var/cache/nginx \
    && chmod -R g+w /var/cache/nginx \
    && chown -R nginx:nginx /usr/share/nginx

# Nginx log redirection to Docker logs
RUN rm /var/log/nginx/access.log \
    && ln -sf /proc/self/fd/1 /var/log/nginx/access.log \
    && rm /var/log/nginx/error.log \
    && ln -sf /proc/self/fd/2 /var/log/nginx/error.log

EXPOSE 8080