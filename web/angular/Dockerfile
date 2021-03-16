# Docker image for jfoa web
# VERSION 0.0.1
# Author: Jack Li
# base image using nginx
FROM nginx
# author
MAINTAINER JavaFamily <javafamily.club@outlook.com>

# temp folder. link locale:/var/lib/docker to container:/tmp
#VOLUME /tmp
#RUN ls
#RUN pwd

COPY ./app/ /data/app/

COPY ./conf/nginx.conf /etc/nginx/nginx.conf
COPY ./conf/default.conf /etc/nginx/conf.d/default.conf

# env
ENV DATA_DIR /data/app
ENV LOG_DIR /log/nginx

RUN mkdir /log/nginx -p

EXPOSE 80

ENTRYPOINT nginx -g "daemon off;"
