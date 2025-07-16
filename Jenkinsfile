pipeline {
  agent any
  environment {
    GIT_CRED = 'github-https'
    DB_URL    = 'jdbc:mysql://db-vehiculos.chi842w4epoh.eu-north-1.rds.amazonaws.com:3306/Sucursal'
  }
  stages {
    stage('Checkout SCM') {
      steps {
        cleanWs()
        checkout([
          $class: 'GitSCM',
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
        withCredentials([usernamePassword(
          credentialsId: 'db-vehiculos',
          usernameVariable: 'admin',
          passwordVariable: 'C4cuc41397'
        )]) {
          sh 'chmod +x mvnw'
          sh './mvnw clean package -DskipTests'
        }
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
        sh 'docker rm contenedor_sucursal || true'
        sh 'docker run -d -p 9090:8080 --name contenedor_sucursal imagen_vehiculos'
      }
    }
  }
  post {
    success { echo 'Pipeline completado con éxito.' }
    failure { echo 'Pipeline falló. Revisa los logs.' }
  }
}
