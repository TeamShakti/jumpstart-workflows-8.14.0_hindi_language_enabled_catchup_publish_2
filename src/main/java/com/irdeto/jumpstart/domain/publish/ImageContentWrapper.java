package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.ImageContent;

public class ImageContentWrapper extends AbstractContentWrapper<ImageContent> {
	private List<String> imageSubcontentIdList = new ArrayList<>();
	private List<String> unpublishedImageSubcontentIdList = new ArrayList<>();
	
	public ImageContentWrapper() {
		super();
	}
	
	public ImageContentWrapper(String imageContentId) {
		super();
		setContentId(imageContentId);
	}
	
	public List<String> getImageSubcontentIdList() {
		return imageSubcontentIdList;
	}

	public void setImageSubcontentIdList(List<String> imageSubcontentIdList) {
		this.imageSubcontentIdList = imageSubcontentIdList;
	}

	public List<String> getUnpublishedImageSubcontentIdList() {
		return unpublishedImageSubcontentIdList;
	}

	public void setUnpublishedImageSubcontentIdList(
			List<String> unpublishedImageSubcontentIdList) {
		this.unpublishedImageSubcontentIdList = unpublishedImageSubcontentIdList;
	}
}
