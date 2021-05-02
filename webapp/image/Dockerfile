FROM node:16.0.0-alpine3.13 AS webapp

WORKDIR /app

COPY ./web-ui/*.json ./
COPY ./web-ui/src ./src
COPY ./web-ui/public ./public

RUN npm ci --only=production
RUN npm run build

FROM nginx:1.20.0-alpine

LABEL Name="docker-localstack-webapp"

# Disable startup logs
# ENV NGINX_ENTRYPOINT_QUIET_LOGS=1

# Copy site content
COPY --from=webapp /app/build /usr/share/nginx/html

# Nginx configuration
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./nginx/conf.d/webapp.conf /etc/nginx/conf.d/default.conf

# Nginx unprivileged user permissions
RUN chown -R nginx:nginx /etc/nginx && chmod -R g+w /etc/nginx
RUN chown -R nginx:nginx /var/cache/nginx && chmod -R g+w /var/cache/nginx
RUN chown -R nginx:nginx /usr/share/nginx

# Nginx log redirection to Docker logs
RUN rm /var/log/nginx/access.log && ln -sf /proc/self/fd/1 /var/log/nginx/access.log
RUN rm /var/log/nginx/error.log && ln -sf /proc/self/fd/2 /var/log/nginx/error.log

EXPOSE 8080