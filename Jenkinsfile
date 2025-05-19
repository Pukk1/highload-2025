@Library('COSM-Jenkins-libs') _

pipeline {

    agent none

    options {
        // This is required if you want to clean before build
        skipDefaultCheckout(true)
    }

    stages {
        
        stage('Preparation') {
            agent { node { label 'master' } }
            steps {
                step([$class: 'WsCleanup'])
    
                checkout scm

                sh '''#!/bin/bash
                    git log -n 1 | grep "commit " | sed 's/commit //g' > currenntVersion
                '''
                    
                stash name:'workspace', includes:'**'
            }
        }

        stage('Build docker images') {
            agent {
                docker {
                    image 'python:3.10-slim'
                    reuseNode true
                    args '-u root'
                }
            }
            steps {
                unstash 'workspace'
                sh '''
                    set -e
                    apt-get update > /dev/null
                    apt-get install -y docker-compose > /dev/null

                    cd ./controller
                    mvn clean formatter:format formatter:validate install
                    cd ..
                    cd ./data-simulator
                    mvn clean formatter:format formatter:validate install
                    cd ..
                    cd ./rula-engine
                    mvn clean formatter:format formatter:validate install
                    cd ..

                    docker-compose build --no-cache
                '''
                stash name:'workspace', includes:'**'
            }
        }

        stage('Run docker tests') {
            agent {
                docker {
                    image 'python:3.10-slim'
                    reuseNode true
                    args '-u root'
                }
            }
            steps {
                unstash 'workspace'
                sh '''
                    set -e
                    apt-get update > /dev/null
                    apt-get install -y docker-compose > /dev/null
                    docker-compose build --no-cache
                    docker run -d --rm itmo-highload-2025_309681_rule-engine mvn clean test
                    docker run -d --rm itmo-highload-2025_309681_data-simulator mvn clean test
                    docker run -d --rm itmo-highload-2025_309681_iot-controller mvn clean test
                    docker-compose up tsung
                '''
            }
            post {
                always {
                    archiveArtifacts artifacts: '/tsung/log/1/**/*', fingerprint: true
                    archiveArtifacts artifacts: '/tsung/log/2/**/*', fingerprint: true
                    archiveArtifacts artifacts: '/tsung/log/3/**/*', fingerprint: true
                    sh 'docker-compose down -v || true'
                }
            }
        }
    }

    post {
        always {
            node ('master') {
                script {
                    env.GIT_URL = env.GIT_URL_1
		            notifyRocketChat(
                        channelName: 'dummy',
                        minioCredentialsId: 'jenkins-minio-credentials',
                        minioHostUrl: 'https://minio.cloud.cosm-lab.science'
                    )
                    withCredentials([string(credentialsId: 'CloudRushTlg-token', variable: 'TLG_TOKEN')]) {
                        notifyTelegram(
                            minioHostUrl: 'https://minio.cloud.cosm-lab.science',
                            botIdAndToken: env.TLG_TOKEN,
                            chatId: '-1002474884172',
                            threadId: '2'
                        )
                    }
                }
            }
        }
    }
 }
