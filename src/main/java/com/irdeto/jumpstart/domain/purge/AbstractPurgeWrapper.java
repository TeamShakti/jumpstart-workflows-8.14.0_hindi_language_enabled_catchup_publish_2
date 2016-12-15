package com.irdeto.jumpstart.domain.purge;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Days;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public abstract class AbstractPurgeWrapper<T extends Base> implements PurgeWrapper<T> {
	static final Integer DURATION_100_YEARS = 36500;
	private T entity;
	private String action;
	private String mode;

	@Override
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	@JsonIgnore
	public List<Relationship<? extends BaseEntity>> getRelationshipList() {
		return new ArrayList<>();
	}
	@JsonIgnore
	public List<Relationship<? extends BaseEntity>> getPurgeRelationshipList() {
		return new ArrayList<>();
	}

	@Override
	public boolean purgeDue() {
		if (getEntity() == null) {
			// No entity to purge.
			return false;
		}
		if (ACTION_DELETE.equals(getAction())) {
			// If it is a deleted entity, then purge can proceed.
			return true;
		}
		if (getEntity() instanceof Base) {
			return testPurgeDates();
		}
		if (ACTION_DE_ORPHAN.equals(getAction())) {
			return testOrphanDates();
		}
		// Not Base entity, so purging video/image/person etc. as part of a top level purge
		return ACTION_RECURSE.equals(getAction());
	}

	@Override
	@JsonIgnore
	public DateTime getEndDate() {
		if (getEntity() instanceof Base) {
			return ((Base)getEntity()).getEndDateTime();
		} else {
			return null;
		}
	}

	@Override
	@JsonIgnore
	public DateTime getModifiedDate() {
		return getEntity().getModifiedDate();
	}

	private boolean testPurgeDates() {
		return testDates(getEndDate(), getGracePeriod());
	}

	private boolean testOrphanDates() {
		return testDates(getModifiedDate(), getGracePeriod());
	}

	private boolean testDates(DateTime date, Integer gracePeriod) {
		if (date == null) {
			return false;
		}
		if (gracePeriod == null) {
			return false;
		}
		DateTime now = DateTime.now();
		if (date.isBefore(now)) {
			int timeBetweenEntityDateAndNow = Days.daysBetween(date.toLocalDate(), now.toLocalDate()).getDays();
			// Is old enough to purge
			return timeBetweenEntityDateAndNow >= gracePeriod;
		} else {
			// Is still active
			return false;
		}
	}

	@Override
	public String getAction() {
		return action;
	}

	@Override
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String getMode() {
		return mode;
	}

	@Override
	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	@JsonIgnore
	public String getPurgeDelay() {
		int purgeDelay = getGracePeriod();
		if (getEndDate() != null) {
			DateTime now = DateTime.now();
			if (getEndDate().isAfter(now)) {
				int timeBetweenNowAndEndDate = Days.daysBetween(now.toLocalDate(), getEndDate().toLocalDate()).getDays();
				purgeDelay = getGracePeriod() + timeBetweenNowAndEndDate;
			}
		}
		return String.valueOf(purgeDelay) + "d";
	}

	@Override
	@JsonIgnore
	public Integer getGracePeriod() {
		String purgeDuration;
		try {
			purgeDuration = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.PURGE_DURATION_KEY);
		} catch (PropertyException e) {
			purgeDuration = "";
		}
		if (StringUtils.isNumeric(purgeDuration)) {
			return Integer.parseInt(purgeDuration);
		}
		return DURATION_100_YEARS;
	}

}
