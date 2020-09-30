gradlew.bat clean bootJar -Pbuildenv=prod
scp -r runner/build/libs/javafamily-oa-0.0.1-SNAPSHOT.jar root@javafamily.club:/root/workspace/repository/jfoa
