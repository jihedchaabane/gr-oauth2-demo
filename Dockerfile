FROM openjdk:21

ARG MODULE_NAME
# gr-eureka-server

ARG JAR_FILE
# gr-eureka-server/target/gr-eureka-server-0.0.1-SNAPSHOT.jar

ARG JAR_VERSION
# 0.0.1-SNAPSHOT

ARG PORT
# 8761

COPY ${JAR_FILE} ${MODULE_NAME}-${JAR_VERSION}.jar
# COPY gr-eureka-server/target/gr-eureka-server-0.0.1-SNAPSHOT.jar gr-eureka-server-0.0.1-SNAPSHOT.jar

EXPOSE ${PORT}
# EXPOSE ${8761}

ENTRYPOINT ["java", "-jar", "${MODULE_NAME}-${JAR_VERSION}.jar"]
# ENTRYPOINT ["java", "-jar", "gr-eureka-server-0.0.1-SNAPSHOT.jar"]



#FROM openjdk:21
#WORKDIR /var/lib/jenkins/workspace/jars
#ADD  target/gr-https-ssl-ms0-orchestrator-0.0.1-SNAPSHOT.jar gr-https-ssl-ms0-orchestrator.jar
#COPY target/gr-https-ssl-ms0-orchestrator-0.0.1-SNAPSHOT.jar gr-https-ssl-ms0-orchestrator-0.0.1-SNAPSHOT.jar
#EXPOSE 1000
#ENTRYPOINT ["java", "-jar", "gr-https-ssl-ms0-orchestrator-0.0.1-SNAPSHOT.jar"]