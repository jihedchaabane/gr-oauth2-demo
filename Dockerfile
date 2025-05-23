FROM openjdk:21

############################ Définir des arguments pour le nom, la version et le port
ARG MODULE_NAME
# gr-eureka-server
ARG JAR_FILE
# gr-eureka-server/target/gr-eureka-server-0.0.1-SNAPSHOT.jar
ARG JAR_VERSION
# 0.0.1-SNAPSHOT
ARG PORT
# 8761

############################ Définir le répertoire de travail
WORKDIR /var/lib/jenkins/workspace/jars
############################ # Copier le fichier JAR en utilisant les arguments
COPY ${JAR_FILE} ${MODULE_NAME}-${JAR_VERSION}.jar
# COPY gr-eureka-server/target/gr-eureka-server-0.0.1-SNAPSHOT.jar gr-eureka-server-0.0.1-SNAPSHOT.jar

############################ Exposer le port dynamique
EXPOSE ${PORT}
# EXPOSE ${8761}

############################ Définir la commande d'entrée avec le nom et la version du JAR
#ENTRYPOINT ["java", "-jar", "${MODULE_NAME}-${JAR_VERSION}.jar"]
ENTRYPOINT ["java", "-jar", "gr-eureka-server-0.0.1-SNAPSHOT.jar"]



#FROM openjdk:21
#WORKDIR /var/lib/jenkins/workspace/jars
#ADD  target/gr-https-ssl-ms0-orchestrator-0.0.1-SNAPSHOT.jar gr-https-ssl-ms0-orchestrator.jar
#COPY target/gr-https-ssl-ms0-orchestrator-0.0.1-SNAPSHOT.jar gr-https-ssl-ms0-orchestrator-0.0.1-SNAPSHOT.jar
#EXPOSE 1000
#ENTRYPOINT ["java", "-jar", "gr-https-ssl-ms0-orchestrator-0.0.1-SNAPSHOT.jar"]