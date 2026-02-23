pipeline {
    agent any
    environment {
        ROLLBACK_TAG = "v1.0.0"   // Set your stable rollback tag here
        ROLLBACK_BRANCH = "rollback/hotfix-1.0.0"
    }
// password vgdo tbxb yiyf smar
    stages {
        stage('Checkout') {
            steps {
                checkout scm


            }
        }
        stage('Init') {
            steps {
                bat './mvnw clean'
            }
        }

//    stage('Parallel') {
//        parallel {
//            stage('Test') {
//                steps {
//                    bat './mvnw test'
//                    junit '**/target/surefire-reports/*.xml'
//                }
//            }
//
//            stage('Documentation') {
//                steps {
//                    script {
//                        bat './mvnw javadoc:javadoc'
//
//                        // Clean up previous 'doc' folder if it exists
//                        bat 'if exist doc rmdir /S /Q doc'
//                        bat 'mkdir doc'
//
//                        // Copy Javadoc content to the 'doc' folder
//                        bat 'xcopy /E /I /Y target\\site doc'
//
//                        // Delete existing doc.zip if it exists
//                        bat 'if exist doc.zip del /Q doc.zip'
//
//                        // Create the ZIP file with the new content
//                        bat 'powershell -Command "Compress-Archive -Path doc\\* -DestinationPath doc.zip -Force"'
//
//                        // Archive the doc.zip file for Jenkins artifacts
//                        archiveArtifacts artifacts: 'doc.zip', fingerprint: true
//                    }
//                }
//            }
//        }
//    }

        stage('Build') {
            steps {
                bat './mvnw install'
                archiveArtifacts artifacts: 'target/*.jar'
            }
        }


        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'Deploying application...'

                // Stop and remove containers safely
                bat 'docker-compose down --remove-orphans'
                bat 'docker rm -f spring-boot-app || exit 0'
                bat 'docker rm -f mysql-db || exit 0'

                // Rebuild and start
                bat 'docker-compose up --build -d'
            }
        }

        stage('Health Check') {
            steps {
                echo "Checking Health..."
                sleep time: 15, unit: 'SECONDS'

                script {

                    def httpCode = bat(script: '''
                                        @echo off
                                        setlocal
                                
                                        curl -s -o response.json -w "%%{http_code}" http://localhost:8082/actuator/health > status.txt 2>nul
                                
                                        if errorlevel 1 (
                                            echo 000 > status.txt
                                        )
                                
                                        set /p code=<status.txt
                                        echo %code%
                                
                                        exit /b 0
                                        ''',
                            returnStdout: true).trim()

                    echo "HTTP Code: ${httpCode}"

                    if (httpCode == "200") {

                        def body = readFile('response.json')
                        echo "Body: ${body}"

                        if (body.contains('"status":"UP"')) {
                            echo "Application is healthy ✅"
                        } else {
                            error("Health endpoint returned non-UP status")
                        }

                    } else {
                        currentBuild.result = "failure"
                    }
                }
            }
        }

        stage('Rollback') {
            when {
                expression { currentBuild.result == 'failure' }
            }
            steps {
                /*  def stableTag = sh(
                         script: "git tag --sort=-creatordate | head -n 1",
                         returnStdout: true
                     ).trim()
                 echo "pro stable ${stableTag}" */
                echo "Starting rollback to tag: ${ROLLBACK_TAG}"
                script {
                    bat """
                        git fetch origin --tags --force                                        git checkout tags/${ROLLBACK_TAG} -b ${ROLLBACK_BRANCH}
                   """
                    echo "Rolled back to tag ${ROLLBACK_TAG} on new branch ${ROLLBACK_BRANCH}"


                    //sh './deploy.sh'
                    bat './mvnw clean'
                    bat './mvnw install'

                    // Stop and remove containers safely
                    bat 'docker-compose down --remove-orphans'
                    bat 'docker rm -f spring-boot-app || exit 0'
                    bat 'docker rm -f mysql-db || exit 0'

                    // Rebuild and start
                    bat 'docker-compose up --build -d'

                    echo "Rollback deployment complete"




                }
            }
        }




    }
    post {
        success {
            echo 'success'
        }
        failure {
            echo 'failure'
        }
    }
}
