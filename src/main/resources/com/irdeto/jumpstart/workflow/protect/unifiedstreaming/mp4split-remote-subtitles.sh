#!/bin/bash -x
#Deploy this file to the remote US server at /opt/data/mp4split-remote.sh
#chmod +x mp4split-remote.sh
#Add "AcceptEnv CALLBACK_URL OUTPUT_FILENAME WORK_ITEM_ID MP4SPLIT_PARAMETERS" to /etc/ssh/sshd_config on the remote US server
#The additional property 'PermitUserEnvironment' is set to 'yes' in sshd_config on remote US server
#Add "SendEnv CALLBACK_URL OUTPUT_FILENAME WORK_ITEM_ID MP4SPLIT_PARAMETERS" to /home/jetty/.ssh/config on jetty server
#Add Hosts * in top of SendEnv in client config file
#The ownership of file config is set to jetty:jetty / mode is 600
#Setup non password ssh access from jetty server to remote US ver
function callBack {
EXIT_STATUS=$1
EXIT_ON_SUCCESS=$2
WORK_ITEM_ID=$3
CALLBACK_URL=$4
FILENAME=$5
OUTPUT=$6
if [ $? -eq 0 ]
then
STATUS=SUCCESS
else
STATUS=FAILURE
rm -f "${FILENAME}" 
fi
if [ "${STATUS}" == "FAILURE" ] || [ "${EXIT_ON_SUCCESS}" == "true" ]
then
OUTPUT=$(echo ${OUTPUT} | sed 's/\\/\\\\/g' | sed 's/\"/\\\"/g' | sed ':a;N;$!ba;s/\n/\\n/g' | sed ':a;N;$!ba;s/\r//g')
curl  -H 'Content-type:text/xml' -d "{\"WorkItemId\":\"${WORK_ITEM_ID}\",\"ExitStatus\":\"${STATUS}\",\"Output\":\"${OUTPUT}\"}" ${CALLBACK_URL}
exit 0
fi
}
echo ${MP4SPLIT_PARAMETERS} | grep ".srt" > /dev/null 2>&1
if [ $? -eq 0 ]
then
DFXP_OUTPUT_FILENAME=$(mktemp subtitle.XXXXXXXXXX.dfxp)
callBack $? false ${WORK_ITEM_ID} ${CALLBACK_URL} "${DFXP_OUTPUT_FILENAME}" "${DFXP_OUTPUT_FILENAME}"
OUTPUT=$(/usr/bin/mp4split --license-key=$(cat /etc/httpd/conf/usp-license.conf  | cut -d' ' -f2) -o ${DFXP_OUTPUT_FILENAME} ${MP4SPLIT_PARAMETERS} 2>&1)
callBack $? false ${WORK_ITEM_ID} ${CALLBACK_URL} ${DFXP_OUTPUT_FILENAME} "${OUTPUT}"
OUTPUT=$(/usr/bin/mp4split --license-key=$(cat /etc/httpd/conf/usp-license.conf  | cut -d' ' -f2) -o ${OUTPUT_FILENAME} ${DFXP_OUTPUT_FILENAME} 2>&1)
rm -f "${DFXP_OUTPUT_FILENAME}" 
callBack $? true ${WORK_ITEM_ID} ${CALLBACK_URL} ${OUTPUT_FILENAME} "${OUTPUT}"
else
OUTPUT=$(/usr/bin/mp4split --license-key=$(cat /etc/httpd/conf/usp-license.conf  | cut -d' ' -f2) -o ${OUTPUT_FILENAME} ${MP4SPLIT_PARAMETERS} 2>&1)
callBack $? true ${WORK_ITEM_ID} ${CALLBACK_URL} ${OUTPUT_FILENAME} "${OUTPUT}"
fi

