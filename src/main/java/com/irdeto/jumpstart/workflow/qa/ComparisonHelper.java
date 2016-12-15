package com.irdeto.jumpstart.workflow.qa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.joda.time.DateTime;

import com.irdeto.jumpstart.domain.Subcontent;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ComparisonHelper {

	public static boolean areSameObjects(Object object1, Object object2) {
		return areSameObjects(object1, object2, false, new ArrayList<String>());
	}

	public static boolean areSameObjects(Object object1, Object object2, boolean considerSubcontent, List<String> considerLinks) {
		// Value 1 and 2 are not null and the same type
		if (object1 == null && object2 == null) {
			return true;
		}
		if ((object1 == null && object2 != null)
				|| (object1 != null && object2 == null)) {
			return false;
		}
		if (!object1.getClass().equals(object2.getClass())) {
			return false;
		}

		// Don't test subcontent
		if (!considerSubcontent && object1 instanceof Subcontent && object2 instanceof Subcontent) {
			return true;
		}

		// Primatives, Strings, enums and DateTime
		if (object1 instanceof String || ClassUtils.isPrimitiveOrWrapper(object1.getClass()) || object1.getClass().isEnum() || object1 instanceof DateTime) {
			if (!object1.equals(object2)) {
				return false;
			}
		}

		// Arrays
		else if (object1.getClass().isArray()) {
			List<?> valueList1 = null;
			List<?> valueList2 = null;
			if (object1 instanceof Object[]) {
				valueList1 = Arrays.asList((Object[])object1);
				valueList2 = Arrays.asList((Object[])object2);
			} else if (object1 instanceof boolean[]) {
				valueList1 = Arrays.asList(ArrayUtils.toObject((boolean[])object1));
				valueList2 = Arrays.asList(ArrayUtils.toObject((boolean[])object2));
			} else if (object1 instanceof byte[]) {
				valueList1 = Arrays.asList(ArrayUtils.toObject((byte[])object1));
				valueList2 = Arrays.asList(ArrayUtils.toObject((byte[])object2));
			} else if (object1 instanceof char[]) {
				valueList1 = Arrays.asList(ArrayUtils.toObject((char[])object1));
				valueList2 = Arrays.asList(ArrayUtils.toObject((char[])object2));
			} else if (object1 instanceof double[]) {
				valueList1 = Arrays.asList(ArrayUtils.toObject((double[])object1));
				valueList2 = Arrays.asList(ArrayUtils.toObject((double[])object2));
			} else if (object1 instanceof float[]) {
				valueList1 = Arrays.asList(ArrayUtils.toObject((float[])object1));
				valueList2 = Arrays.asList(ArrayUtils.toObject((float[])object2));
			} else if (object1 instanceof int[]) {
				valueList1 = Arrays.asList(ArrayUtils.toObject((int[])object1));
				valueList2 = Arrays.asList(ArrayUtils.toObject((int[])object2));
			} else if (object1 instanceof long[]) {
				valueList1 = Arrays.asList(ArrayUtils.toObject((long[])object1));
				valueList2 = Arrays.asList(ArrayUtils.toObject((long[])object2));
			} else if (object1 instanceof short[]) {
				valueList1 = Arrays.asList(ArrayUtils.toObject((short[])object1));
				valueList2 = Arrays.asList(ArrayUtils.toObject((short[])object2));
			} else if (object1 instanceof Object[]) {
				valueList1 = Arrays.asList((Object[])object1);
				valueList2 = Arrays.asList((Object[])object2);
			}
			if (!areSameEntries(valueList1, valueList2, considerSubcontent, considerLinks)) {
				return false;
			}
		}

		// Iterables
		else if (object1 instanceof Iterable<?>) {
			if (!areSameEntries((Iterable<?>)object1, (Iterable<?>)object2, considerSubcontent, considerLinks)) {
				return false;
			}
		}

		// Maps
		else if (object1 instanceof Map<?, ?>) {
			Map<?, ?> map1 = (Map<?,?>)object1;
			Map<?, ?> map2 = (Map<?,?>)object2;
			for (Object key: map1.keySet()) {
				if (!map2.containsKey(key)) {
					return false;
				}
				Object value1 = map1.get(key);
				Object value2 = map2.get(key);
				if (!areSameObjects(value1, value2, considerSubcontent, considerLinks)) {
					return false;
				}
			}
			for (Object key: map2.keySet()) {
				if (!map1.containsKey(key)) {
					return false;
				}
				Object value1 = map1.get(key);
				Object value2 = map2.get(key);
				if (!areSameObjects(value1, value2, considerSubcontent, considerLinks)) {
					return false;
				}
			}
		}

		// Complex types
		// Entity 1 and 2 are not null and same class.
		else {
			for (Method method: object1.getClass().getMethods()) {
				if (!method.getName().startsWith("get")
						|| WorkflowHelper.isIn(method.getName(),
							"getClass",
							"getId",
						    "getType",
						    "getCreatedDate",
						    "getCreatedBy",
						    "getModifiedDate",
						    "getModifiedBy",
						    "getVersions",
						    "getAdditionalProperties",
						    "get_links",
						    "getMetadataApproved",
						    "getContentApproved"
							)) {
					continue;
				}
				// Method is a getter.
				try {
					Object value1 = method.invoke(object1);
					Object value2 = method.invoke(object2);
					if (!areSameObjects(value1, value2, considerSubcontent, considerLinks)) {
						return false;
					}
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
				}
			}

			// Check links
			if (considerLinks != null) {
				for (String link: considerLinks) {
					Integer object1LinkCount = WorkflowHelper.getLinkCount(object1, link);
					Integer object2LinkCount = WorkflowHelper.getLinkCount(object2, link);
					if (object1LinkCount == null && object2LinkCount == null) {
						continue;
					} else if (object1LinkCount == null && object2LinkCount != null) {
						return false;
					} else if (!object1LinkCount.equals(object2LinkCount)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private static boolean areSameEntries(Iterable<?> value1, Iterable<?> value2, boolean considerSubcontent, List<String> considerLinks) {
		if (value1 == value2) {
			return true;
		}
		if ((value1 == null && value2 != null)
				|| (value1 != null && value2 == null)) {
			return false;
		}

		for (Object entry1: value1) {
			boolean found = false;
			for (Object entry2: value2) {
				if (areSameObjects(entry1, entry2, considerSubcontent, considerLinks)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		for (Object entry2: value2) {
			boolean found = false;
			for (Object entry1: value1) {
				if (areSameObjects(entry1, entry2, considerSubcontent, considerLinks)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}
}
