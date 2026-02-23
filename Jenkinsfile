pipeline {
agent any
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
            bat '''
        docker-compose down --rmi all --volumes --remove-orphans
        docker system prune -f
        docker-compose up --build -d
        '''
        }
    }

    stage('Health Check') {
        steps {
            echo "Checking Health..."
            sleep time: 15, unit: 'SECONDS'

            script {

                def result = bat(
                        script: '''
                @echo off
                curl -s -o response.json -w "%%{http_code}" http://localhost:8082/actuator/health > status.txt 2>nul
                if errorlevel 1 (
                    echo 000 > status.txt
                )
                type status.txt
                ''',
                        returnStdout: true
                ).trim()

                def httpCode = result

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
                    error("Application not reachable")
                }
            }
        }
    }


}
    post {
        success {
            echo 'success'
        }
        failure {
            echo 'failed'
        }
    }
}
