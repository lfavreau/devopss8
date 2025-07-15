pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM',
          branches: [[name: '*/main']],
          userRemoteConfigs: [[
            url: 'https://github.com/lfavreau/devopss8.git',
            credentialsId: 'github-https'
          ]]
        ])
      }
    }
    stage('Build') {
      sh 'chmod +x mvnw'
      sh './mvnw clean package'
    }
    stage('Docker Image') {
      steps { sh 'docker build -t vehiculos-rest .' }
    }
    stage('Deploy') {
      steps {
        sh 'docker stop vehiculos-rest || true'
        sh 'docker rm   vehiculos-rest || true'
        sh 'docker run -d -p 9090:8080 --name vehiculos-rest vehiculos-rest'
      }
    }
  }
}
