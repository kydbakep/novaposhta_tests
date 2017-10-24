node {
    checkout scm

    stage('Novaposhta tests') {
        def environment = docker.build('tober_test_docker_build')
        environment.inside() {
//==============================================================================
            String commandParams = ''
// AWIS ------------------------------------------------------------------------
            if (env.AWIS_URL) {
                commandParams += " -Dawis.url=${env.AWIS_URL}"
            }
            if (env.AWIS_LOGIN) {
                commandParams += " -Dawis.login=${env.AWIS_gLOGIN}"
            }
            if (env.AWIS_PASSWORD) {
                commandParams += " -Dawis.password=${env.AWIS_PASSWORD}"
            }
// WEB -------------------------------------------------------------------------
            if (env.WEB_URL) {
                commandParams += " -Dweb.url=${env.WEB_URL}"
            }
            if (env.WEB_LOGIN) {
                commandParams += " -Dweb.login=${env.WEB_LOGIN}"
            }
            if (env.WEB_PASSWORD) {
                commandParams += " -Dweb.password=${env.WEB_PASSWORD}"
            }
// IGNORE FAILURES -------------------------------------------------------------
            if (env.MAVEN_IGNORE_FAILURES){
                commandParams += " -Dmaven.test.failure.ignore=${env.MAVEN_IGNORE_FAILURES}"
            }
//==============================================================================
//==============================================================================
            String testName = ''

            if (env.TEST_CLASS) {
                testName += " -Dtest=${env.TEST_CLASS}"
            }

            if (env.TEST_NAME) {
                testName += "#${env.TEST_NAME}"
            }
//==============================================================================
//            Удалять все говно!
            sh "echo 'Starting tests'"

//            sh "Xvfb :99 -ac -screen 0 1920x1080x24 &"
            sh "Xvfb :0 -ac -screen 0 1920x1080x24 &"
            sh "mvn clean test" + commandParams + testName
        }
    }
    stage('Results log') {
        junit allowEmptyResults: true, keepLongStdio: true, testResults: '**/target/surefire-reports/*.xml'
    }

    stage('Artifacts') {
        archiveArtifacts 'build/reports/**/*'
    }
}