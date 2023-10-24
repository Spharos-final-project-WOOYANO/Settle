pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Settle'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    cd Settle
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    cd server
                    docker stop Settle-Service || true
                    docker rm Settle-Service || true
                    docker rmi Settle-Service-Img || true
                    docker build -t Settle-Service-Img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --name Settle-Service -p 8080:8000 Settle-Service-Img'
            }
        }
    }
}
