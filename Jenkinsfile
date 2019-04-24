pipeline {
	agent none
	stages {
		stage('Package') {
			agent {
				docker {
					image 'maven:3-alpine'
					args '-v /root/.m2:/root/.m2 -v /root/.m2/settings.xml:/usr/share/maven/conf/settings.xml'
				}
			}
			steps {
						echo 'Package..'
						sh 'mvn clean package install'
						script{
						def mapProperties = t.getProperties()
        					mapProperties.remove('class')//默认有自带class属性，移除减少数据传输量
        					println(mapProperties)
						}
			}
		}
		stage('Build') {
			agent any
			steps {
						echo 'Clean..'
						sh 'mv  docker/Dockerfile Dockerfile'
						sh 'rm -rf docker'
						sh 'mkdir docker'
						sh 'mv  Dockerfile docker/Dockerfile'
						echo 'Build..'
						sh 'mv  target/jenkins-pipeline-0.0.1-SNAPSHOT.jar  docker/jenkins-pipeline.jar'
						sh "docker rmi jenkins-pipeline "
						sh "docker build -t jenkins-pipeline docker/"
				}
			}
	}
	post{
    	always{
	    	emailext (
	    		body: """
	    			${JOB_NAME}- Build #${BUILD_NUMBER} ${currentBuild.result}!</br>
	    			BUILD_URL: <a href=\"${env.BUILD_URL}\">${env.BUILD_URL}</a></br>
	    			JOB_URL:<a href=\"$JOB_URL\">$JOB_URL</br>
	    			LogInfo: <a href=\"${env.BUILD_URL}console\">${env.BUILD_URL}console</br>
	    			
	    		""", 
	    		recipientProviders: [[$class: 'DevelopersRecipientProvider']], 
	    		subject: '${JOB_NAME}- Build #${BUILD_NUMBER} Construction Result',   
	    		to: '1410914605@qq.com'
				)
    	}
    }
}