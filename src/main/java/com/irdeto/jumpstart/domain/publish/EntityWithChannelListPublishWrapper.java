package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import com.irdeto.jumpstart.domain.Channel;

public interface EntityWithChannelListPublishWrapper extends EntityWithTermWrapperListPublishWrapper{
	public List<Channel> getChannelList();
	public Channel getChannel();
}
