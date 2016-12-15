package com.irdeto.jumpstart.domain.purge;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ChannelDay;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;

public class ChannelDayPurgeWrapper extends AbstractPurgeWrapper<ChannelDay> {
    @Override
    @JsonIgnore
    public Class<ChannelDay> getEntityClass() {
        return ChannelDay.class;
    }

    @Override
    public List<String> getDestinationProcessIdList() {
        List<String> processIdList = new ArrayList<>();
        processIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_PUBLISH);
        processIdList.add(JumpstartPropertyKey.PROCESS_ID_ELASTICSEARCH_PUBLISH);
        return processIdList;
    }
}
