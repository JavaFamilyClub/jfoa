# <a href="https://javafamilyclub.github.io/jfoa/"><img width="60px" align="center" src="https://s1.ax1x.com/2020/08/28/donrLD.png" alt="icon"/></a> [JavaFamily OA System](https://javafamilyclub.github.io/jfoa) 

[![build](https://github.com/JavaFamilyClub/jfoa/actions/workflows/build.yml/badge.svg)](https://github.com/JavaFamilyClub/jfoa/actions/workflows/build.yml "Build Status")
[![publish](https://github.com/JavaFamilyClub/jfoa/actions/workflows/publish.yml/badge.svg)](https://github.com/JavaFamilyClub/jfoa/actions/workflows/publish.yml "Publish Status")
[![docker](https://github.com/JavaFamilyClub/jfoa/actions/workflows/docker.yml/badge.svg)](https://github.com/JavaFamilyClub/jfoa/actions/workflows/docker.yml "Docker Build Status")
[![codecov](https://codecov.io/gh/JavaFamilyClub/jfoa/branch/master/graph/badge.svg)](https://codecov.io/gh/JavaFamilyClub/jfoa)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d107816830da43caa3f4f848dadb72e8)](https://www.codacy.com/gh/JavaFamilyClub/jfoa/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JavaFamilyClub/jfoa&amp;utm_campaign=Badge_Grade)
[![Percentage of opend issues](http://isitmaintained.com/badge/open/javafamilyclub/jfoa.svg)](https://github.com/JavaFamilyClub/jfoa/issues "Percentage of issues still open")
![GitHub release](https://img.shields.io/github/release-pre/JavaFamilyClub/jfoa)
![GitHub repo size](https://img.shields.io/github/repo-size/JavaFamilyClub/jfoa)
![GitHub License](https://img.shields.io/github/license/JavaFamilyClub/jfoa)
[![Join the chat](https://badges.gitter.im/javafamilychat/jfoa.svg)](https://gitter.im/javafamilychat/jfoa?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Chat on QQ](https://img.shields.io/badge/chat-on%20QQ-ff69b4.svg)](https://jq.qq.com/?_wv=1027&k=d3NIuw7P)

### 0. Preface
### 0.0 Environment

> * JDK: open JDK 1.8
> * Platform: Mac OS X, Linux, Windows

### 0.1 Security

>  `jfoa`'s amqp, email and other password information are all encrypted by `jasypt-spring-boot-starter`, so if you want to start the project after fork, please replace it(quote with `ENC(xxx)` in `./runner/src/main/resources/application.yml`) with your own service or contact me for the secret key.
>  When starting the project, In one of two ways:
>  * pass in the JVM startup parameter `-Djasypt.encryptor.password=xxx`. 
>  * configure it to the environment variable `export JF_JASYPT_ENCRYPTOR=xxx`

### 0.2 Online Demo

> Please visit [www.javafamily.club](https://javafamily.club) preview.

### 1. Build Project
#### 1.1 Build Develop Environment
``` shell script
gradlew[.bat] clean build
```

#### 1.2 Build Production Environment
``` shell script
gradlew[.bat] clean build -Pbuildenv=prod
```

### 2. Project Runner
#### 2.1 Startup
``` shell script
gradlew[.bat] server
Or
gradlew[.bat] server -Pbuildenv=prod # Product Mode
```

#### 2.2 Compile
```shell script
gradlew[.bat] classes # Compile Java 
```

```shell script
gradlew[.bat] web:watch # watch web
Or
gradlew[.bat] web:watch -Pbuildenv=prod # watch product mode
```

#### 2.3 Release
##### 2.3.1 Build Release Jar

```shell script
gradlew[.bat] cleanAll bootJar -Pbuildenv=prod
```

##### 2.3.2 Run Release Jar

```shell script
java -jar javafamily-oa-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

#### 2.4 Docker
##### 2.4.1 Build Docker Image

```shell script
./buildDockerImage
```

##### 2.4.2 Run Docker Container

```shell script
docker run -d -p80:80 -p443:443 --name jfoa javafamily/jfoa:latest
```

##### 2.4.3 See the logs

```shell script
docker logs containerId
```

#### Backers

Thank you to all our backers! 🙏 [[Become a backer](https://opencollective.com/jfoa#backer)]

<a href="https://opencollective.com/jfoa#backer" target="_blank"><img src="https://opencollective.com/jfoa/backer.svg?width=890"></a>

#### Sponsors

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/jfoa#sponsor)]

<a href="https://opencollective.com/jfoa/sponsor/0/website" target="_blank"><img src="https://opencollective.com/jfoa/sponsor/0/avatar.svg"></a>

#### Contributors

This project exists thanks to all the people who contribute. [[Contribute](CONTRIBUTING.md)].
<a href="https://github.com/JavaFamilyClub/jfoa/graphs/contributors"><img src="https://opencollective.com/jfoa/contributors.svg?width=890" /></a>
