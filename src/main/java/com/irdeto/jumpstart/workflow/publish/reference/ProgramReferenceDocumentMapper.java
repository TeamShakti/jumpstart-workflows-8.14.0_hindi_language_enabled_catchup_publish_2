package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.SubtitleContent;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.VideoSubcontent;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.publish.EntityWithBrandListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithEventListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithGenreListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithProgramListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithRatingsPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithScheduleSlotListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithSeriesListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithSubscriptionPackageListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithTvodCollectionListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.publish.VideoContentWrapper;
import com.irdeto.jumpstart.domain.publish.VideoSubcontentWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceProgram;
import com.irdeto.jumpstart.domain.reference.ReferenceRendition;
import com.irdeto.jumpstart.domain.reference.ReferenceSubtitle;
import com.irdeto.jumpstart.domain.reference.ReferenceVideoContent;
import com.irdeto.jumpstart.workflow.publish.PublishHelper;

public class ProgramReferenceDocumentMapper extends AbstractReferenceDocumentMapper<Program> {
	@Override
	@JsonIgnore
	public Class<Program> getEntityClass() {
		return Program.class;
	}

	@Override
	@JsonIgnore
	public ReferenceDocument getReferenceDocument() throws Exception {
		Program program = getPublishWrapper().getEntity();
		List<ReferenceVideoContent> videoContentList = mapVideoContent();
		ReferenceProgram referenceProgram = mapProgram(program, getPublishWrapper().getTermWrapperList(), videoContentList);
		mapDocument(program, referenceProgram);

		referenceProgram.setBrand(BrandReferenceDocumentMapper.mapBrand((EntityWithBrandListPublishWrapper)getPublishWrapper()));
		referenceProgram.setSeries(SeriesReferenceDocumentMapper.mapSeries((EntityWithSeriesListPublishWrapper)getPublishWrapper()));

		referenceProgram.getEvents().addAll(EventReferenceDocumentMapper.mapEventList((EntityWithEventListPublishWrapper)getPublishWrapper()));
		//referenceProgram.getGenres().addAll(GenreReferenceDocumentMapper.mapGenreList((EntityWithGenreListPublishWrapper)getPublishWrapper()));
		//referenceProgram.getRatings().addAll(RatingReferenceMapper.mapRatingList((EntityWithRatingsPublishWrapper)getPublishWrapper()));
		referenceProgram.getScheduleSlots().addAll(ScheduleSlotReferenceDocumentMapper.mapScheduleSlotList((EntityWithScheduleSlotListPublishWrapper)getPublishWrapper()));
		referenceProgram.getSubscriptionPackages().addAll(SubscriptionPackageReferenceDocumentMapper.mapSubscriptionPackageList((EntityWithSubscriptionPackageListPublishWrapper)getPublishWrapper()));
		referenceProgram.getTvodCollections().addAll(TvodCollectionReferenceDocumentMapper.mapTvodCollectionList((EntityWithTvodCollectionListPublishWrapper)getPublishWrapper()));

		return referenceProgram;
	}

	protected static List<ReferenceProgram> mapProgramList(EntityWithProgramListPublishWrapper publishWrapper) throws Exception {
		return mapProgramList(publishWrapper.getProgramList());
	}

	protected static List<ReferenceProgram> mapProgramList(List<Program> programList) throws Exception {
		List<ReferenceProgram> referenceProgramList = new ArrayList<>();
		if (programList != null) {
			for (Program program: programList) {
				referenceProgramList.add(mapProgram(program));
			}
		}
		return referenceProgramList;
	}

	protected static ReferenceProgram mapProgram(Program program) throws Exception {
		return mapProgram(program, null, null);
	}

	protected static ReferenceProgram mapProgram(Program program, List<TermWrapper> termWrapperList, List<ReferenceVideoContent> videoContentList) throws Exception {
		ReferenceProgram referenceProgram = null;
		if (program != null) {
			referenceProgram = new ReferenceProgram();
			mapDocumentTitle(program, termWrapperList, referenceProgram);
			mapDocumentEntitlement(program, termWrapperList, videoContentList, referenceProgram);
			referenceProgram.setEpisodeId(program.getEpisodeId());
			referenceProgram.setEpisodeName(program.getEpisodeName());
			referenceProgram.setIsClosedCaptioning(program.getIsClosedCaptioning());
			referenceProgram.setDisplayRunTime(program.getDisplayRunTime());
			referenceProgram.setYearOfRelease(program.getYearOfRelease());
			referenceProgram.setIsSeasonPremiere(program.getIsSeasonPremier());
			// code by nitin
			referenceProgram.setNDSOfferID(program.getNDSOfferID());
			referenceProgram.setGenre(mapLocalized(program.getGenre()));
			referenceProgram.setRating(program.getRating());
			referenceProgram.setNdsPrice(program.getNdsPrice());
			referenceProgram.setMaxconcurrentstream(program.getMaxconcurrentstream());
			referenceProgram.setIsDownloadable(program.getIsDownloadable());
			referenceProgram.setDownloadExpiry(program.getDownloadExpiry());
			referenceProgram.setMSORating(program.getMSORating());
			referenceProgram.setIsSeasonFinale(program.getIsSeasonFinale());
			referenceProgram.setLinearBroadcastDate(program.getLinearBroadcastDate());
			referenceProgram.setContributors(ContributorReferenceMapper.mapContributorList(program.getContributors()));
			referenceProgram.setImageContents(mapImageContents(program.getImageContent()));
		}
		return referenceProgram;

	}

	private List<ReferenceVideoContent> mapVideoContent() {
		List<ReferenceVideoContent> referenceVideoContentList = new ArrayList<>();
		Program program = getPublishWrapper().getEntity();

		for (VideoContentWrapper videoContentWrapper : getPublishWrapper().getVideoContentWrapperList()) {
			VideoContent videoContent = getPublishWrapper().getContentById(videoContentWrapper);
			ReferenceVideoContent referenceVideoContent = new ReferenceVideoContent();

			// From VideoContent
			referenceVideoContent.setEntitlementId(PublishHelper.getControlAssetId(program, videoContent));
			referenceVideoContent.setContentType(stringify(videoContent.getContentType()));
			referenceVideoContent.setDuration(videoContent.getDuration());
			referenceVideoContent.setLanguages(stringifyList(videoContent.getLanguage()));
			referenceVideoContent.setSubtitleLanguages(stringifyList(videoContent.getSubtitleLanguage()));
			referenceVideoContent.setDubbedLanguages(stringifyList(videoContent.getDubbedLanguage()));
			referenceVideoContent.setScreenFormat(stringify(videoContent.getScreenFormat()));

			// From subtitles
			List<SubtitleContent> subtitleContentList =  videoContent.getSubtitleContent();
			for (SubtitleContent subtitleContent : subtitleContentList){
				List<String> subtitleUrls = new ArrayList<String>();
				ReferenceSubtitle referenceSubtitle = new ReferenceSubtitle();
				referenceSubtitle.setLanguage(subtitleContent.getLanguage());
				for (SubtitleSubcontent subtitleSubContent : subtitleContent.getSubcontent()){
					subtitleUrls.add(subtitleSubContent.getConsumerUrl());
				}
				referenceSubtitle.setUrls(subtitleUrls);
				referenceVideoContent.getSubtitleContent().add(referenceSubtitle);
			}

			// From VideoSubcontent
			for (VideoSubcontentWrapper videoSubcontentWrapper : videoContentWrapper.getVideoSubcontentWrapperList()) {
				VideoSubcontent videoSubcontent = getPublishWrapper().getProtectedVideoSubcontentById(videoSubcontentWrapper.getProtectedVideoSubcontentId());
				for (Device device: videoSubcontentWrapper.getDeviceList()) {
					ReferenceRendition rendition = new ReferenceRendition();
					rendition.setResolution(stringify(videoSubcontent.getResolution()));
					rendition.setFrameRate(stringify(videoSubcontent.getFrameRate()));
					rendition.setCodec(stringify(videoSubcontent.getCodec()));
					rendition.setBitrate(videoSubcontent.getBitRate());
					rendition.setIsHDContent(videoSubcontent.getIsHDContent());
					rendition.setConsumerUrl(appendConsumerUrl(device, videoSubcontent.getConsumerUrl()));
					rendition.setLicenseAcquisitionUrl(buildLicenseAcquisitionUrlMap(device, program.getType(), program.getId()));
					referenceVideoContent.getRenditionMap().put(device, rendition);
				}
			}

			referenceVideoContentList.add(referenceVideoContent);
		}

		return referenceVideoContentList;
	}
}
