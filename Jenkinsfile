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

        stage('Build artifacts & tests') {
            agent {
                docker {
                    image 'openjdk:21-jdk'
                    reuseNode true
                    args '-u root'
                }
            }
            environment {
                MAVEN_VERSION = '3.9.9'
            }
            steps {
                unstash 'workspace'

                sh '''
                    apt-get remove -y maven || true

                    apt-get update
                    apt-get install -y wget tar

                    wget https://downloads.apache.org/maven/maven-3/${env.MAVEN_VERSION}/binaries/apache-maven-${env.MAVEN_VERSION}-bin.tar.gz
                    tar -xzf apache-maven-${env.MAVEN_VERSION}-bin.tar.gz
                    mv apache-maven-${env.MAVEN_VERSION} /opt/maven
                    ln -s /opt/maven/apache-maven-${env.MAVEN_VERSION} /opt/maven/latest

                    echo 'export PATH=/opt/maven/latest/bin:$PATH' >> ~/.bashrc
                    source ~/.bashrc

                    cd ./controller
                    mvn clean formatter:format formatter:validate install
                    cd ..
                    cd ./data-simulator
                    mvn clean formatter:format formatter:validate install
                    cd ..
                    cd ./rule-engine
                    mvn clean formatter:format formatter:validate install
                    cd ..
                '''

                stash name:'workspace', includes:'**'
            }
        }

        stage('Build docker images') {
            agent {
                docker {
                    image 'openjdk:21-jdk'
                    reuseNode true
                    args '-u root'
                }
            }
            steps {
                unstash 'workspace'
                sh '''
                    set -e
                    apt-get update
                    apt-get install -y docker-compose

                    docker-compose build --no-cache
                '''
                stash name:'workspace', includes:'**'
            }
        }

        stage('Run tsung') {
            agent {
                docker {
                    image 'openjdk:21-jdk'
                    reuseNode true
                    args '-u root'
                }
            }
            steps {
                unstash 'workspace'
                sh '''
                    set -e
                    apt-get update
                    apt-get install -y docker-compose
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
