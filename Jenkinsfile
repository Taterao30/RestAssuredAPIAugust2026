pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    parameters {
        choice(name: 'ENV', choices: ['qa', 'dev'], description: 'Environment')
        choice(name: 'TEST_GROUP', choices: ['all', 'smoke', 'regression'], description: 'TestNG group')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Verify Tools') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Run API Tests') {
            steps {
                script {
                    if (params.TEST_GROUP == 'all') {
                        sh "mvn clean test -Denv=${params.ENV}"
                    } else {
                        sh "mvn clean test -Denv=${params.ENV} -Dgroups=${params.TEST_GROUP}"
                    }
                }
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/junitreports/*.xml',
                  allowEmptyResults: true

            archiveArtifacts artifacts: 'target/surefire-reports/**',
                             allowEmptyArchive: true
        }
    }
}
