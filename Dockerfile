# Docker image for jfoa
# VERSION 0.0.1
# Author: Jack Li
# base image using java8
FROM java:8
# author
MAINTAINER JavaFamily <javafamily.club@outlook.com>

# temp folder. link locale:/var/lib/docker to container:/tmp
VOLUME /tmp

# add jar to container and renaming to app.jar
ADD ./javafamily-oa-*.jar app.jar
# env
ARG JF_JASYPT_ENCRYPTOR
ENV JF_JASYPT_ENCRYPTOR=$JF_JASYPT_ENCRYPTOR

ARG JF_GITHUB_CLIENT_ID
ENV JF_GITHUB_CLIENT_ID=$JF_GITHUB_CLIENT_ID

ARG JF_GITHUB_CLIENT_SECRETS
ENV JF_GITHUB_CLIENT_SECRETS=$JF_GITHUB_CLIENT_SECRETS

ENV JAVA_OPTS="-Xms800m -Xmx800m"

# run command
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar --spring.profiles.active=prod
