pipeline {
  agent any

  environment {
    GIT_CRED = 'github-https'
  }

  stages {
    stage('Checkout SCM') {
      steps {
        checkout([$class: 'GitSCM',
          branches: [[name: '*/main']],
          userRemoteConfigs: [[
            url: 'https://github.com/lfavreau/devopss8.git',
            credentialsId: env.GIT_CRED
          ]]
        ])
      }
    }

    stage('Build Application - Maven') {
      steps {
        sh 'chmod +x mvnw'
        sh './mvnw clean package'
      }
    }

    stage('Docker Image') {
      steps {
        sh 'docker build -t imagen_vehiculos .'
      }
    }

    stage('Deployment') {
      steps {
        sh 'docker stop contenedor_sucursal || true'
        sh 'docker rm contenedor_sucursal   || true'
        sh 'docker run -d -p 9090:8080 --name contenedor_sucursal imagen_vehiculos'
      }
    }
  }

  post {
    success {
      echo 'Pipeline completado con éxito.'
    }
    failure {
      echo 'Pipeline falló. Revisa los logs.'
    }
  }
}
