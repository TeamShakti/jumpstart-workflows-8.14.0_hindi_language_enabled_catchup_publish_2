package com.irdeto.jumpstart.workflow.publish.control;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.publish.VideoContentWrapper;
import com.irdeto.jumpstart.workflow.publish.PublishHelper;

public class ProgramControlMapper extends ContentControlMapper<Program>{

	@Override
	@JsonIgnore
	public Class<Program> getEntityClass() {
		return Program.class;
	}

	@Override
	@JsonIgnore
	public List<String> getMediaIdList() {
		// Find videos to link
		List<String> mediaIdList = new ArrayList<>();
		for (VideoContentWrapper videoContentWrapper: getPublishWrapper().getVideoContentWrapperList()) {
			VideoContent videoContent = getPublishWrapper().getContentById(videoContentWrapper);
			if (!videoContentWrapper.getTermMapList().isEmpty()
					&& !videoContentWrapper.getTermWrapperList().isEmpty()) {
				mediaIdList.add(PublishHelper.getControlAssetId(getPublishWrapper().getApprovedEntity(), videoContent));
			}
		}
		mediaIdList.add(PublishHelper.getControlAssetId(getPublishWrapper().getApprovedEntity()));
		return mediaIdList;
	}

	@Override
	@JsonIgnore
	protected boolean isMainContentType(TermMap termMap) {
		return termMap.getContentTypeList().contains(ContentType.MOVIE.toString());
	}
}
