package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.publish.EntityWithChannelListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithSubscriptionPackageListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceChannel;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceVideoContent;

public class ChannelReferenceDocumentMapper extends AbstractReferenceDocumentMapper<Channel> {
	@Override
	@JsonIgnore
	public Class<Channel> getEntityClass() {
		return Channel.class;
	}

	@Override
	@JsonIgnore
	public ReferenceDocument getReferenceDocument() throws Exception {
		Channel channel = getPublishWrapper().getApprovedEntity();
		List<ReferenceVideoContent> videoContentList = mapEncodeProfileRenditions();
		ReferenceChannel referenceChannel = mapChannel(channel, getPublishWrapper().getTermWrapperList(), videoContentList);
		mapDocument(channel, referenceChannel);
		referenceChannel.getSubscriptionPackages().addAll(SubscriptionPackageReferenceDocumentMapper.mapSubscriptionPackageList((EntityWithSubscriptionPackageListPublishWrapper)getPublishWrapper()));
		return referenceChannel;
	}

	protected static List<ReferenceChannel> mapChannelList(List<Channel> channelList) throws Exception {
		List<ReferenceChannel> referenceChannelList = new ArrayList<>();
		if (channelList != null) {
			for (Channel channel: channelList) {
				referenceChannelList.add(mapChannel(channel));
			}
		}
		return referenceChannelList;
	}

	protected static ReferenceChannel mapChannel(EntityWithChannelListPublishWrapper publishWrapper) throws Exception {
		return mapChannel(publishWrapper.getChannel());
	}

	protected static ReferenceChannel mapChannel(Channel channel) throws Exception {
		return mapChannel(channel, null, null);
	}

	protected static ReferenceChannel mapChannel(Channel channel, List<TermWrapper> termWrapperList, List<ReferenceVideoContent> videoContentList) throws Exception {
		ReferenceChannel referenceChannel = null;
		if (channel != null) {
			referenceChannel = new ReferenceChannel();
			mapDocumentTitle(channel, termWrapperList, referenceChannel);
			mapDocumentEntitlement(channel, termWrapperList, videoContentList, referenceChannel);
			referenceChannel.setEnabled(channel.getEnabled());
			referenceChannel.setChannelId(channel.getChannelId());
			referenceChannel.setDisplayOrder(channel.getDisplayOrder());
			referenceChannel.setChannelPackager(channel.getChannelPackager());
			referenceChannel.setAllowedOnBrowsers(channel.getAllowedOnBrowsers());
			referenceChannel.setFreeChannel(channel.getFreeChannel());
			referenceChannel.setNumberOfAudio(channel.getNumberOfAudio());
			referenceChannel.setBroadcastServiceId(channel.getBroadcastServiceId());
			referenceChannel.setHDChannel(channel.getHDChannel());
			referenceChannel.setCatchupChannel(channel.getCatchupChannel());
			referenceChannel.setUdpMulticastIP(channel.getUdpMulticastIP());
			referenceChannel.setLiveWindowDuration(channel.getLiveWindowDuration());
			referenceChannel.getImageContents().addAll(mapImageContents(channel.getImageContent()));
		}
		return referenceChannel;
	}
}
