package com.irdeto.jumpstart.domain.purge;

import static com.irdeto.jumpstart.domain.purge.PurgeWrapper.ACTION_DELETE;
import static com.irdeto.jumpstart.domain.purge.PurgeWrapper.MODE_DEFERRED;
import static com.irdeto.jumpstart.domain.purge.PurgeWrapper.MODE_NOW;
import static com.irdeto.jumpstart.factory.FactoryHelper.classesFor;

import java.util.Map;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.factory.FactoryHelper;

public class PurgeWrapperFactory {
    private PurgeWrapperFactory() {}

    @SuppressWarnings("unchecked")
    private static final Map<Class<Base>, Class<?>> purgeWrapperMap = classesFor(PurgeWrapper.class);

    /**
     * Shortcut to return {@link PurgeWrapper}.
     * @param entity to create wrapper for.
     * @param <T> entity class.
     * @return newly created {@link PurgeWrapper}.
     */
    public static <T extends Base> PurgeWrapper<T> getInstance(T entity, String action, String mode) {
        @SuppressWarnings("unchecked")
        PurgeWrapper<T> purgeWrapper = (PurgeWrapper<T>) FactoryHelper.getInstance(purgeWrapperMap, entity.getClass());
        if (purgeWrapper != null) {
            purgeWrapper.setEntity(entity);
            purgeWrapper.setAction(action);
            if (MODE_DEFERRED.equals(mode) && purgeWrapper.purgeDue()) {
                purgeWrapper.setMode(MODE_NOW);
            } else {
                purgeWrapper.setMode(MODE_DEFERRED);
            }
        }
        return purgeWrapper;
    }

    /**
     * Shortcut to return {@link PurgeWrapper} with mode {@link PurgeWrapper#MODE_NOW} and
     * action {@link PurgeWrapper#ACTION_DELETE}.
     * @param entity to create wrapper for.
     * @param <T> entity class.
     * @return newly created {@link PurgeWrapper}.
     */
    public static <T extends Base> PurgeWrapper<T> buildDeleteNowWrapper(T entity) {
        return getInstance(entity, ACTION_DELETE, MODE_NOW);
    }
}
