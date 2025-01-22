pipeline {

    agent any

    environment {
        /*SONARCLOUD_URL = 'https://sonarcloud.io' // URL de SonarCloud
        SONARCLOUD_PROJECT_KEY = 'micro-service-architecture_discovery-service' // Clé unique de votre projet
        SONARCLOUD_ORGANIZATION = 'micro-service-architecture' // Nom de votre organisation SonarCloud
        SONARCLOUD_TOKEN = '2191f786b89a318558c1f14e4a7be8a9a5e74aff' // Token généré dans votre profil SonarCloud
        */

        registryCredential = 'Dockerhub-Access'
        dockerImageName = "sectani/hello-world-app"
        dockerImage = ""

    }

    stages {

        stage('Clone Repository') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Sectani/CI-CD-Project',
                    credentialsId: 'Github-Access'
            }
        }

        stage('Compile') {
            steps {
                dir('') {
                    sh 'mvn clean compile'
                }
            }
        }

       stage('Test Unitaire') {
            steps {
                dir('') {
                    sh 'mvn test'
                }
            }
       }

         /*stage('Code Quality') {
            steps {
                dir('Discovery-service') {
                    withEnv(["SONAR_TOKEN=${SONARCLOUD_TOKEN}"]) {
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=${SONARCLOUD_PROJECT_KEY} \
                        -Dsonar.organization=${SONARCLOUD_ORGANIZATION} \
                        -Dsonar.host.url=${SONARCLOUD_URL} \
                        -Dsonar.login=${SONARCLOUD_TOKEN}
                    """
                }
                }
            }
        }*/


       stage('Package Jar') {
            steps {
                dir('') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

       stage('Build image') {
           steps{
                dir('') {
                    script {
                         dockerImage = docker.build("${dockerImageName}:1.0")
                    }
                }
           }
       }

         stage('Pushing Image') {
          steps{
            dir('') {
            script {
              docker.withRegistry( 'https://registry.hub.docker.com', registryCredential ) {
                //dockerImage.push("latest")
                dockerImage.push("1.0")  // Also push the specific version tag
              }
            }
            }
          }
        }


        stage('Deploying App to Kubernetes') {
          steps {
            dir('') {
            script {
              kubernetesDeploy(configs: "deployment.yaml", kubeconfigId: "kubernetes")
            }
          }
          }
        }

    }

    post {
        always {
            echo "Build complete. Sending notification..."
        }
        success {
            mail to: 'a.sectani2019@gmail.com',
                 subject: "Discovery-service Build #${env.BUILD_NUMBER} Success",
                 body: "The build succeeded!"
        }
        failure {
            mail to: 'a.sectani2019@gmail.com',
                 subject: "Build #${env.BUILD_NUMBER} Failed",
                 body: "The build failed. Please check Jenkins logs."
        }
    }

}

}