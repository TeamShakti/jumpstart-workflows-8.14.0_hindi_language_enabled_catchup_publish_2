package com.irdeto.jumpstart.workflow.purge;

import static com.irdeto.domain.mediamanager.filter.Filter.FieldType.DATE;
import static java.time.LocalTime.MIDNIGHT;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.domain.mediamanager.filter.LtFilter;
import com.irdeto.domain.mediamanager.message.bulk.EntityData;
import com.irdeto.domain.mmentityapi.HttpQueryFilterParameter;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.ChannelDay;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * Facilitates the purge operation for outdated {@link com.irdeto.jumpstart.domain.ScheduleSlot}s.
 * Used within bpmn.
 */
@SuppressWarnings("unused")
public class EpgPurgeHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EpgPurgeHelper.class);
    private static final long DEFAULT_PURGE_DAYS = 7;
    private static final String DATE_FIELD = "date";
    private static final String HARD_DELETE_VAR = "hardDelete";

    public static QueryFilterParameter buildFilterParameter(Object intervalValue) {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), MIDNIGHT, ZoneId.systemDefault());

        long days;
        if (intervalValue != null) {
            days = NumberUtils.toLong(intervalValue.toString(), DEFAULT_PURGE_DAYS);
        } else {
            days = DEFAULT_PURGE_DAYS;
        }

        return new HttpQueryFilterParameter(
                new LtFilter()
                        .withField(DATE_FIELD)
                        .withType(DATE)
                        .withValue(today.minus(days, DAYS).toInstant().toEpochMilli())
        );
    }

    public static Map<String, EntityData> buildEntitiesDataForBulkDelete(List<? extends Base> results) {
        return results.stream().collect(toMap(
            Base::getUriId,
            result -> new EntityData(WorkflowHelper.getEntityType(ChannelDay.class), Integer.valueOf(result.getId()))
        ));
    }

    /**
     * Ensures that {@link ProcessContext} has {@code doHardDelete} variable set(default value is true).
     * Does not do anything, if boolean value is already set.
     * @param context Process context to be used
     */
    public static void configureDeletionMode(ProcessContext context) {
        Object doHardDelete = context.getVariable(HARD_DELETE_VAR);
        if (doHardDelete == null) {
            context.setVariable(HARD_DELETE_VAR, true);
        } else if (!(doHardDelete instanceof Boolean)) {
            context.setVariable(HARD_DELETE_VAR, BooleanUtils.toBoolean(doHardDelete.toString()));
        }
    }
}
