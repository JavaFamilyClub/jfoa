#!/usr/bin/env sh

docker stop jfoa-web
docker rm jfoa-web
docker run --name jfoa-web -d -p 80:80 javafamily/jfoa-web
