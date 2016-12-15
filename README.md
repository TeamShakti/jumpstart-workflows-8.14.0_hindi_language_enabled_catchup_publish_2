Multiscreen Solution jBPM Workflow
==================================
This repository contains the jBPM workflow for Multiscreen solution.

Pre-requisite to build
======================
- maven: to download external jar file
- nexus: build and library
- git: source version control system

Build procedure
===============
1. Open git bash and go to your local workspace directory
2. Clone this repository into your local folder
3. Go to jumpstart-workflows folder ex) cd jumpstart-workflows
4. Run 'mvn clean install'
5. If no error, you can get this output files in target folder
6. For eclipse, run 'mvn eclipse:eclipse'. Then, import it as an existing project in eclipse.

Outputs to deploy to jBPM server(jetty)
=======================================
jumpstart-workflows/target/irdeto-jbpm-app-1.**.war
jumpstart-workflows/target/jumpstart-workflows-*.*.jar
jumpstart-workflows/src/main/resource/com/
jumpstart-workflows/src/main/resource/data/

# Docker Setup

## Build
```
sudo docker build --rm=true --build-arg VERSION=0.0.0-alpha.0 -t jumpstart-workflow .
```

## Run Container
```
docker run -dt jumpstart-workflow
```