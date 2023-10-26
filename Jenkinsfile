pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'jenkins-github-access-token', url:'https://github.com/Spharos-final-project-WOOYANO/Settle'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
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
                sh 'docker run -d --name settle-service -p 8006:8000 settle-service-img'
            }
        }
    }
}
