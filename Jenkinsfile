#!groovy

/**
 *
 * This uses Jenkins Shared Global Libraries.
 *  Copy of this repo locally here --
 *      https://gitlab.hdc.engineering.intra/multiscreen-ci/workflowLibs
 *
 */

def DOCKER_REGISTRY_URL = 'https://artifactory.hdc.engineering.intra:5002/v2'
def DOCKER_CREDENTIALS_ID = 'DockerRWCredentials'
def REGISTRY_GROUP_ID = 'multiscreen'
def MAVEN_DEPLOY_CREDENTIALS_ID = 'ArtifactoryDeployCredentialsId'
def MAVEN_REPO_ID = 'maven-multiscreen-dev'
def MAVEN_REPO = "https://artifactory.hdc.engineering.intra/artifactory/${MAVEN_REPO_ID}"
def GITLAB_CREDENTIALS_ID = 'GitLabCredentials'
def GITLAB_SERVER_ID = 'GitLab EMEA'
def CI_JOB_NAME = 'MultiscreenCI'
def version = null

def COMPONENT_NAME = 'jumpstart-workflow'
def CI_VERSION_PARAM = 'JUMPSTART_WORKFLOW_VERSION'

// If multibranch job -- aka branch builds -- we need to set the gitlab ci connection property
if(manager.build.getParent().getParent() instanceof org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject) {
    properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '14', numToKeepStr: '')),
                [$class: 'GitLabConnectionProperty', gitLabConnection: GITLAB_SERVER_ID],
                [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
                pipelineTriggers([])])
}

node('dockerhost') {
    stage('Code Checkout') {
        gitlabCommitStatus('Code Checkout') {
            checkout scm
        }
    }

    stage('Version Handling') {
        gitlabCommitStatus('Version Handling') {
            version = versionUtils.generate(params.MAJOR,
                    params.MINOR,
                    params.PATCH,
                    env.BUILD_NUMBER,
                    env.BRANCH_NAME,
                    params.RELEASE_TYPE)

            currentBuild.description = version
        }
    }

    stage('Build & Deploy') {
        gitlabCommitStatus('Build & Deploy') {
            mavenUtils.setVersionAndRun(version,
                    'clean deploy',
                    MAVEN_REPO,
                    MAVEN_DEPLOY_CREDENTIALS_ID)
        }
    }

    stage('Docker Build') {
        gitlabCommitStatus('Docker Build') {
            dockerUtils.build("${REGISTRY_GROUP_ID}/${COMPONENT_NAME}", version, "--build-arg VERSION=${version} .", DOCKER_REGISTRY_URL, DOCKER_CREDENTIALS_ID)
        }
    }

    stage('Docker Deploy') {
        gitlabCommitStatus('Docker Deploy') {
            dockerUtils.deploy("${REGISTRY_GROUP_ID}/${COMPONENT_NAME}", version, DOCKER_REGISTRY_URL, DOCKER_CREDENTIALS_ID)
        }
    }


    /*
    stage('Component Tests') {
        gitlabCommitStatus('Component Tests') {
            // TODO: None as of now..
        }
    }
    */

    stage('E2E Regression') {
        gitlabCommitStatus('E2E Regression') {
            if (currentBuild.result == null || currentBuild.result == 'SUCCESS') {
                build job: CI_JOB_NAME, parameters: [[$class: 'StringParameterValue', name: CI_VERSION_PARAM, value: version]]
            }
        }
    }

    stage('Git Tagging') {
        gitlabCommitStatus('Git Tagging') {
            if (params.RELEASE_TYPE == 'release' &&
                    (currentBuild.result == null || currentBuild.result == 'SUCCESS')) {
                gitUtils.tag(version,
                        "Release Version Jumpstart Workflow version '${version}'",
                        GITLAB_CREDENTIALS_ID,
                        'origin')
            }
        }
    }
}