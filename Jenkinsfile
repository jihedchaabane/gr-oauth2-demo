pipeline {
    agent any
    tools {
        maven 'MAVEN'
    }
    parameters {
        string(name: 'MODULES', defaultValue: 'all', description: 'Comma-separated list of modules to build, or "all" for all modules')
    }
    environment {
        DOCKER_REGISTRY = 'jihed123' // e.g., docker.io/yourusername
        DOCKER_CREDENTIALS_ID = 'docker-credentials'
        
        CONFIG_SERVER_ADDR = 'http://container-gr-conf-config-server:8762'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build and Deploy Modules') {
            steps {
                script {
                    def modules = [
                        'gr-eureka-server',
                        'gr-auth-server',
                        'gr-api-gateway',
                        'gr-sbadmin-server',
                        'gr-conf-aggregator-server',
                        'gr-ms1-resource',
                        'gr-ms2-resource',
                        'gr-ms3-resource',
                        'gr-resource-consumer-resttemplate',
                        'gr-resource-consumer-webclient',
                        'gr-resource-consumer-feign'
                    ]
                    def selectedModules = params.MODULES == 'all' ? modules : params.MODULES.split(',').collect { it.trim() }
                    
                    for (module in selectedModules) {
                        if (!modules.contains(module)) {
                            error "Invalid module: ${module}"
                        }
                        
                        stage("Build Module: ${module}") {
                            sh "mvn clean package -pl ${module} -am"
                        }
                        
//                        stage("SonarQube Analysis: ${module}") {
//                            withSonarQubeEnv('SonarQubeServer') {
//                                sh """
//                                	mvn -pl ${module} sonar:sonar \
//			                            -Dsonar.java.binaries=. \
//			                            -Dsonar.projectName=${module} \
//			                            -Dsonar.projectKey=${module}
//                                """
//                            }
//                        }
                        
                        stage("Stop and Remove Old Container: ${module}") {
                            sh """
                                docker stop container-${module} || true
                                docker rm container-${module} || true
                            """
                        }
                        
                        stage("Remove Old Docker Image If Exists: ${module}") {
                            script {
                                //def version = sh(script: "mvn -pl ${module} help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                                def version = sh(script: "grep 'build.version' 	${module}/target/classes/META-INF/build-info.properties | cut -d'=' -f2", returnStdout: true).trim()
                                def imageName = "${DOCKER_REGISTRY}/${module}:${version}"
                                sh """
			                        if [ \$(docker images -q ${imageName}) ]; then
			                            echo "Removing Old Existing Docker Image: ${imageName}"
			                            docker rmi ${imageName} || true
			                        else
			                            echo "Docker image ${imageName} doesn't already exist !"
			                        fi
			                    """
                            }
                        }
                        
                        stage("Build New Docker Image: ${module}") {
                            script {
                                //def version = sh(script: "mvn -pl ${module} help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                                //def port = sh(script: "yq eval '.server.port' ${module}/src/main/resources/application.yml", returnStdout: true).trim()
                                def version = sh(script: "grep 'build.version' 	${module}/target/classes/META-INF/build-info.properties | cut -d'=' -f2", returnStdout: true).trim()
                                def port = sh(script: "grep 'server.port' ${module}/src/main/resources/application.yml | cut -d' ' -f2", returnStdout: true).trim()
                                def jarFile = sh(script: "ls ${module}/target/*.jar", returnStdout: true).trim()
                                sh """
                                    docker build \
                                        --build-arg MODULE_NAME=${module} \
                                        --build-arg JAR_FILE=${jarFile} \
                                        --build-arg JAR_VERSION=${version} \
                                        --build-arg PORT=${port} \
                                        -t ${DOCKER_REGISTRY}/${module}:${version} .
                                """
                            }
                        }
                        
//                        stage("Push New Docker Image: ${module}") {
//                            script {
//                                //def version = sh(script: "mvn -pl ${module} help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
//							      def version = sh(script: "grep 'build.version' 	${module}/target/classes/META-INF/build-info.properties | cut -d'=' -f2", returnStdout: true).trim()
//                                docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS_ID) {
//                                    sh "docker push ${DOCKER_REGISTRY}/${module}:${version}"
//                                }
//                            }
//                        }
                        
                        stage("Run New Docker Container: container-${module}") {
                            script {
                                //def version = sh(script: "mvn -pl ${module} help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                                //def port = sh(script: "yq eval '.server.port' ${module}/src/main/resources/application.yml", returnStdout: true).trim()
                                def version = sh(script: "grep 'build.version' 	${module}/target/classes/META-INF/build-info.properties | cut -d'=' -f2", returnStdout: true).trim()
                                def port = sh(script: "grep 'server.port' ${module}/src/main/resources/application.yml | cut -d' ' -f2", returnStdout: true).trim()
                                sh """
				                    docker run \
				                    	-d --name container-${module} --network ${DOCKER_NETWORK} \
									    -p ${port}:${port} \
			                    	    -e SPRING_PROFILES_ACTIVE=${ACTIVE_PROFILE} \
			                    	    -e CONFIG_SERVER_ADDR=${CONFIG_SERVER_ADDR} \
				                       	-v /var/lib/jenkins/workspace/certificates:/certificates \
									    ${DOCKER_REGISTRY}/${module}:${version}
			 					"""
                            }
                        }
                        
                        
                        stage('Wait 25 seconds') {
			                script {
			                    sh """
			                    	echo "Attendre que le conteneur démarre"
			                        sleep 25 
			                    """
//			                    sh """
//			                    echo "Vérifier que l'application répond"
//			                        curl --fail http://localhost:${APP_PORT}/actuator/health || exit 1
//			                    """
				                }
				        }
                        stage("Verify New Container is RUNNING: container-${module}") {
                            script {
                                def status = sh(script: "docker inspect -f '{{.State.Status}}' container-${module}", returnStdout: true).trim()
                                if (status != 'running') {
                                    error "Container container-${module} is not running. Status: ${status}"
                                } else {
                                    echo "Container container-${module} is running successfully."
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            echo 'Application SpringBoot démarrée avec succés !'
        }
        failure {
            echo 'Echec du démarrage de l\'application.'
        }
    }
}