package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceChannel extends ReferenceDocumentWithEntitlementAndTitle {
	@JsonIgnore
	@Override
	public String getType() {
		return "channel";
	}

	private List<ReferenceImageContent> imageContents = new ArrayList<>();
	private List<ReferenceSubscriptionPackage> subscriptionPackages = new ArrayList<>();
	private Boolean enabled;
	// code by nitin
	private String broadcastServiceId;
	private String channelPackager;
	private String udpMulticastIP;
	private Boolean allowedOnBrowsers;
	private Boolean freeChannel;
	private Boolean hDChannel;
	private Integer displayOrder;
	private Boolean catchupChannel;
	private Integer numberOfAudio;
	private String channelId;
	private Integer liveWindowDuration;

	@JsonProperty("enabled")
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JsonProperty("imageContents")
	public List<ReferenceImageContent> getImageContents() {
		return imageContents;
	}
	public void setImageContents(List<ReferenceImageContent> imageContents) {
		this.imageContents = imageContents;
	}

	@JsonProperty("BroadcastServiceId")
    public String getBroadcastServiceId() {
        return broadcastServiceId;
    }
	public void setBroadcastServiceId(String broadcastServiceId) {
        this.broadcastServiceId = broadcastServiceId;
    }
	
	 @JsonProperty("channelPackager")
	    public String getChannelPackager() {
	        return channelPackager;
	    }
	 public void setChannelPackager(String channelPackager) {
	        this.channelPackager = channelPackager;
	    }
	 
	 @JsonProperty("udpMulticastIP")
	    public String getUdpMulticastIP() {
	        return udpMulticastIP;
	    }
	 public void setUdpMulticastIP(String udpMulticastIP) {
	        this.udpMulticastIP = udpMulticastIP;
	    }
	 
	 
	 @JsonProperty("freeChannel")
	    public Boolean getFreeChannel() {
	        return freeChannel;
	    }
	 public void setFreeChannel(Boolean freeChannel) {
	        this.freeChannel = freeChannel;
	    }
	 
	 @JsonProperty("HDChannel")
	    public Boolean getHDChannel() {
	        return hDChannel;
	    }
	 public void setHDChannel(Boolean hDChannel) {
	        this.hDChannel = hDChannel;
	    }
	 
	 @JsonProperty("catchupChannel")
	    public Boolean getCatchupChannel() {
	        return catchupChannel;
	    }
	 public void setCatchupChannel(Boolean catchupChannel) {
	        this.catchupChannel = catchupChannel;
	    }
	 
	 @JsonProperty("displayOrder")
	    public Integer getDisplayOrder() {
	        return displayOrder;
	    }
	 public void setDisplayOrder(Integer displayOrder) {
	        this.displayOrder = displayOrder;
	    }
	
	 @JsonProperty("numberOfAudio")
	    public Integer getNumberOfAudio() {
	        return numberOfAudio;
	    }
	 public void setNumberOfAudio(Integer numberOfAudio) {
	        this.numberOfAudio = numberOfAudio;
	    }
	
	 @JsonProperty("channelId")
	    public String getChannelId() {
	        return channelId;
	    }
	 public void setChannelId(String channelId) {
	        this.channelId = channelId;
	    }
	
	 
	 @JsonProperty("allowedOnBrowsers")
	    public Boolean getAllowedOnBrowsers() {
	        return allowedOnBrowsers;
	    }
	 public void setAllowedOnBrowsers(Boolean allowedOnBrowsers) {
	        this.allowedOnBrowsers = allowedOnBrowsers;
	    }
	
	@JsonProperty("subscriptionPackages")
	public List<ReferenceSubscriptionPackage> getSubscriptionPackages() {
		return subscriptionPackages;
	}
	public void setSubscriptionPackages(List<ReferenceSubscriptionPackage> subscriptionPackages) {
		this.subscriptionPackages = subscriptionPackages;
	}

	@JsonProperty("liveWindowDuration")
	public Integer getLiveWindowDuration() {
		return liveWindowDuration;
	}
	public void setLiveWindowDuration(Integer liveWindowDuration) {
		this.liveWindowDuration = liveWindowDuration;
	}
}
