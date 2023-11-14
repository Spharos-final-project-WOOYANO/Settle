pipeline {
    agent any
    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Settle'
            }
        }
	stage('Secret-File Download'){
	    steps {
	        withCredentials([
		    file(credentialsId: 'Kafka-Secret-File', variable: 'kafkaSecret')
		    ])
		{
		    sh "cp \$kafkaSecret ./src/main/resources/application-secret.yml"
		}
	    }
	}
        stage('Build'){
            steps{
                script {
                    sh '''
                        pwd
                        chmod +x ./gradlew
                        ./gradlew build
                    '''

                }

            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    docker stop settle-service || true
                    docker rm settle-service || true
                    docker rmi settle-service-img || true
                    docker build -t settle-service-img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --network spharos-network -e EUREKA_URL="${EUREKA_URL}" -e KAFKA_URL1="${KAFKA_URL1}" --name settle-service settle-service-img'

            }
        }
    }
}

