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
            bat 'docker-compose up --build -d'
        }
    }



}
    post {
        always {
            publishHTML ([
            allowMissing: false,
             alwaysLinkToLastBuild: true,
             keepAll: true,
             reportDir: 'target/site/apidocs',
             reportFiles: 'index.html',
             reportName: 'Documentation'
             ])

        }

        success {
            echo 'success'
        }
        failure {
            echo 'failed'
        }
    }
}
