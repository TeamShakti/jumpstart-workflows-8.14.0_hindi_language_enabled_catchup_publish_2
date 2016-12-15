package com.irdeto.domain.control;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.control.ControlAvailabilityOverride;
import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.jumpstart.domain.control.ControlContent;
import com.irdeto.jumpstart.domain.control.ControlMedia;
import com.irdeto.jumpstart.domain.control.ControlPriceOverride;
import com.irdeto.jumpstart.domain.control.ControlSubContent;

public class ControlRegisterContentDummyData {
	private static final String RELEASE_DATE = "1900-01-01T00:00:00+01:00";
	private static final String RELEASE_END = "2099-12-31T23:00:00+01:00";
	private static final String OPTION_ID = "4";
	private static final String ACCOUNT_ID = "multiscreen";
	private static final String MEDIA_ID = "100_movie";
	private static final String CONTENT_ID = "16";
	private static final String NAME = "Mad Max";
	private static final String TYPE = "default";
	private static final String POLICY_ID = "3";
	private static final String COUNTRY = "US";
	private static final Float AMOUNT = 10.99f;
	private static final Boolean BLOCKED = false;
	
	public static ControlBatch getControlBatch() {
		ControlBatch controlBatch = new ControlBatch();
		List<ControlMedia> mediaList = new ArrayList<>();
		mediaList.add(getMedia());
		controlBatch.setMediaList(mediaList);
		return controlBatch;
	}

	private static ControlMedia getMedia() {
		ControlMedia media = new ControlMedia();
		media.setAccountId(ACCOUNT_ID);
		media.setMediaId(MEDIA_ID);
		media.setName(NAME);
		List<ControlContent> contentList = new ArrayList<>();
		contentList.add(getContent());
		media.setContentList(contentList);
		return media;
	}

	private static ControlContent getContent() {
		ControlContent content = new ControlContent();
		content.setAccountId(ACCOUNT_ID);
		content.setContentId(CONTENT_ID);
		content.setMediaId(MEDIA_ID);
		content.setPolicyId(POLICY_ID);
		content.setType(TYPE);
		List<ControlAvailabilityOverride> availabilityOverrideList = new ArrayList<>();
		availabilityOverrideList.add(getAvailabilityOverrideList());
		content.setAvailabilityOverrideList(availabilityOverrideList);
		List<ControlPriceOverride> priceOverrideList = new ArrayList<>();
		priceOverrideList.add(getPriceOverride());
		content.setPriceOverrideList(priceOverrideList);
		List<ControlSubContent> subContentList = new ArrayList<>();
		subContentList.add(getSubContent());
		content.setSubContentList(subContentList);
		return content;
	}

	private static ControlSubContent getSubContent() {
		ControlSubContent subContent = new ControlSubContent();
		subContent.setAccountId(ACCOUNT_ID);
		subContent.setContentId(CONTENT_ID);
		subContent.setMediaId(MEDIA_ID);
		subContent.setType(TYPE);
		return subContent;
	}

	private static ControlPriceOverride getPriceOverride() {
		ControlPriceOverride priceOverride = new ControlPriceOverride();
		priceOverride.setAmount(AMOUNT);
		priceOverride.setCountry(COUNTRY);
		priceOverride.setOptionId(OPTION_ID);
		return priceOverride;
	}

	private static ControlAvailabilityOverride getAvailabilityOverrideList() {
		ControlAvailabilityOverride availabilityOverride = new ControlAvailabilityOverride();
		availabilityOverride.setBlocked(BLOCKED);
		availabilityOverride.setCountry(COUNTRY);
		availabilityOverride.setReleaseDate(RELEASE_DATE);
		availabilityOverride.setReleaseEnd(RELEASE_END);
		return availabilityOverride;
	}
}
