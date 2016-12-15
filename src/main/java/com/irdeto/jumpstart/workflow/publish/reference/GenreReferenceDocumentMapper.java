package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.publish.EntityWithGenreListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithParentGenrePublishWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceGenre;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class GenreReferenceDocumentMapper extends AbstractReferenceDocumentMapper<Genre> {
	@Override
	@JsonIgnore
	public Class<Genre> getEntityClass() {
	return Genre.class;
	}

	@Override
	@JsonIgnore
	public ReferenceDocument getReferenceDocument() throws Exception {
	Genre genre = getPublishWrapper().getEntity();
	ReferenceGenre referenceGenre = mapGenre(genre);
	referenceGenre.setParentGenre(mapGenre(((EntityWithParentGenrePublishWrapper) getPublishWrapper()).getParentGenre()));

		return referenceGenre;
	}

	protected static List<ReferenceGenre> mapGenreList(EntityWithGenreListPublishWrapper publishWrapper) {
		return mapGenreList(publishWrapper.getGenreList());
	}

	protected static List<ReferenceGenre> mapGenreList(List<Genre> genreList) {
		List<ReferenceGenre> referenceGenreList = new ArrayList<>();
		if (genreList != null) {
			for (Genre genre: genreList) {
				ReferenceGenre referenceGenre = mapGenre(genre);

				if (genre.getParent() != null) {
				referenceGenre.setParentGenre(mapGenre(genre.getParent()));

				referenceGenre.setGenrePath(
							LocaleHelper.getStringValueForDefaultLanguage(genre.getParent().getTitle()) + '/' + LocaleHelper.getStringValueForDefaultLanguage(genre.getTitle())
					);
				} else {
					referenceGenre.setGenrePath(
							LocaleHelper.getStringValueForDefaultLanguage(genre.getTitle())
					);
				}

				referenceGenreList.add(referenceGenre);
			}
		}
		return referenceGenreList;
	}

	private static ReferenceGenre mapGenre(Genre genre) {
		ReferenceGenre referenceGenre = null;
		if (genre != null) {
			referenceGenre = new ReferenceGenre();
			referenceGenre.setId(genre.getUriId());
			referenceGenre.setIsEnabled(genre.getIsEnabled());
			referenceGenre.setTitle(mapLocalized(genre.getTitle()));
			referenceGenre.setProvider(genre.getProvider());
		}
		return referenceGenre;
	}
}