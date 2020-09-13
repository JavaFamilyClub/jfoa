# <a href="https://javafamilyclub.github.io/jfoa/"><img width="60px" align="center" src="https://s1.ax1x.com/2020/08/28/donrLD.png" alt="icon"/></a> [JavaFamily OA System](https://javafamilyclub.github.io/jfoa) 

[![Build Status: https://travis-ci.org/JavaFamilyClub/jfoa.svg?branch=master](https://travis-ci.org/JavaFamilyClub/jfoa.svg?branch=master)](https://travis-ci.org/github/JavaFamilyClub/jfoa)
[![Windows-latest Build](https://github.com/JavaFamilyClub/jfoa/workflows/Windows-latest%20Build/badge.svg)](https://github.com/JavaFamilyClub/jfoa/actions?query=workflow%3A%22Windows-latest+Build%22)
[![Join the chat at: https://gitter.im/javafamilychat/jfoa](https://badges.gitter.im/javafamilychat/jfoa.svg)](https://gitter.im/javafamilychat/jfoa?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

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

#### 2.3 Build Release Jar

```shell script
gradlew[.bat] cleanAll bootJar -Pbuildenv=prod
```

#### 2.4 Run Release Jar

```shell script
java -jar javafamily-oa-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```
