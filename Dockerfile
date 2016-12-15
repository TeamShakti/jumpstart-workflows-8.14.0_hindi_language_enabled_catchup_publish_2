FROM tianon/true

ENV E2E_ARTIFACTS="/opt/irdeto/multiscreen/e2e"
ENV WORKFLOW_ARTIFACTS="${E2E_ARTIFACTS}/workflow"
ENV MEDIAMANAGER_ARTIFACTS="${E2E_ARTIFACTS}/mediamanager"

VOLUME ${WORKFLOW_ARTIFACTS}/
VOLUME ${MEDIAMANAGER_ARTIFACTS}/

ARG VERSION
ENV VERSION ${VERSION:-0.0.0-alpha.0}

COPY target/jumpstart-workflows-${VERSION}.jar ${WORKFLOW_ARTIFACTS}/
COPY jumpstart.config.properties ${WORKFLOW_ARTIFACTS}/

COPY JumpstartRecipe/Recipe/Jumpstart/Jumpstart.php ${MEDIAMANAGER_ARTIFACTS}/
# Disable for now -- need to validate correctness
#COPY JumpstartRecipe/Recipe/Jumpstart/JumpstartSeed.php ${MEDIAMANAGER_ARTIFACTS}/
COPY JumpstartRecipe/exportConfiguration.json ${MEDIAMANAGER_ARTIFACTS}/
COPY JumpstartRecipe/datagrid ${MEDIAMANAGER_ARTIFACTS}/datagrid