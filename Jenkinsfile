import groovy.json.JsonSlurper;
 
node{
    stage 'Build, Test and Package'
    env.PATH = "${tool 'apache-maven-3.5.0'}/bin:${env.PATH}"
    git url: "https://github.com/chandan493/jenkins-demo.git"
    def commitid = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
    def workspacePath = pwd()
    sh "echo ${commitid} > ${workspacePath}/expectedCommitid.txt"
    sh "mvn clean package -Dcommitid=${commitid}"
}
 
node{
    stage 'Stop, Deploy and Start'
    // shutdown
    sh 'curl -X POST http://localhost:9001/shutdown || true'
    // copy file to target location
    sh 'cp target/*.jar /tmp/'
    // start the application
    sh 'nohup java -jar /tmp/*.jar &'
    // wait for application to respond
    sh 'while ! httping -qc1 http://localhost:9001 ; do sleep 1 ; done'
}
 
node{
    stage 'Smoketest'
    def workspacePath = pwd()
    sh "curl --retry-delay 10 --retry 5 http://localhost:9001/info -o ${workspacePath}/info.json"
    if (deploymentOk()){
        return 0
    } else {
        return 1
    }
}
 
def deploymentOk(){
    def workspacePath = pwd()
    expectedCommitid = new File("${workspacePath}/expectedCommitid.txt").text.trim()
    actualCommitid = readCommitidFromJson()
    println "expected commitid from txt: ${expectedCommitid}"
    println "actual commitid from json: ${actualCommitid}"
    return expectedCommitid == actualCommitid
}
 
def readCommitidFromJson() {
    def workspacePath = pwd()
    def slurper = new JsonSlurper()
    def json = slurper.parseText(new File("${workspacePath}/info.json").text)
    def commitid = json.app.commitid
    return commitid
}