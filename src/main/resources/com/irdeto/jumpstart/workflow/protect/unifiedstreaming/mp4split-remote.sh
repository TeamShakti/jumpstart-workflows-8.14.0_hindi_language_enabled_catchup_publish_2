#!/bin/bash -x
#Deploy this file to the remote US server at /opt/data/mp4split-remote.sh
#chmod +x mp4split-remote.sh
#Add "AcceptEnv CALLBACK_URL OUTPUT_FILENAME WORK_ITEM_ID MP4SPLIT_PARAMETERS" to /etc/ssh/sshd_config on the remote US server
#The additional property 'PermitUserEnvironment' is set to 'yes' in sshd_config on remote US server
#Add "SendEnv CALLBACK_URL OUTPUT_FILENAME WORK_ITEM_ID MP4SPLIT_PARAMETERS" to /home/jetty/.ssh/config on jetty server
#Add Hosts * in top of SendEnv in client config file
#The ownership of file config is set to jetty:jetty / mode is 600
#Setup non password ssh access from jetty server to remote US ver
OUTPUT=$(/usr/bin/mp4split --license-key=$(cat /etc/httpd/conf/usp-license.conf  | cut -d' ' -f2) -o ${OUTPUT_FILENAME} ${MP4SPLIT_PARAMETERS} 2>&1)
if [ $? -eq 0 ]
then
OUTPUT=$(cat ${OUTPUT_FILENAME} | sed 's/\\/\\\\/g' | sed 's/\"/\\\"/g' | sed ':a;N;$!ba;s/\n/\\n/g' | sed ':a;N;$!ba;s/\r//g')
STATUS=SUCCESS
else
OUTPUT=$(echo ${OUTPUT} | sed 's/\\/\\\\/g' | sed 's/\"/\\\"/g' | sed ':a;N;$!ba;s/\n/\\n/g' | sed ':a;N;$!ba;s/\r//g')
STATUS=FAILURE
fi
rm -f ${OUTPUT_FILENAME}
curl  -H 'Content-type:text/xml' -d "{\"WorkItemId\":\"${WORK_ITEM_ID}\",\"ExitStatus\":\"${STATUS}\",\"Output\":\"${OUTPUT}\"}" ${CALLBACK_URL}
exit 0
