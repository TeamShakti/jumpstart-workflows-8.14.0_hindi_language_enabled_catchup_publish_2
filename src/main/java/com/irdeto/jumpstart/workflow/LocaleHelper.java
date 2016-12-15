package com.irdeto.jumpstart.workflow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.irdeto.jumpstart.domain.Locale;

public class LocaleHelper {
	private static final String GETTER_PREFIX = "get";
	private static final String SETTER_PREFIX = "set";
	// If no locale is specified, assume that this locale is used.
	public static final String INGEST_DEFAULT_LOCALE = "en_US";
	public static final String PUBLISH_DEFAULT_LOCALE = "en_US";
	public static final String DEFAULT_LANGUAGE_NAME = "eng";
	public static final String ALTERNATE_DEFAULT_LANG_NAME = "en";
	public static final String DEFAULT_CURRENCY = "USD";
	public static final String DEFAULT_COUNTRY_NAME = "US";
	public static final String ALL_COUNTRY = "01";

	private static Map<String, List<String>> LANGUAGE_LOCALE_MAP = new HashMap<>();
	private static Set<String> PUBLISH_LANGUAGE_SET = new HashSet<>();

	static {
		List<String> enList = new ArrayList<String>();
		enList.add("en_US");
		List<String> hiList = new ArrayList<String>();
		hiList.add("hi_IN");
		List<String> taList = new ArrayList<String>();
		taList.add("ta_IN");
		LANGUAGE_LOCALE_MAP.put("eng", enList);
		LANGUAGE_LOCALE_MAP.put("en", enList);
		LANGUAGE_LOCALE_MAP.put("de", enList);
		LANGUAGE_LOCALE_MAP.put("ta", taList); // Tamil
		LANGUAGE_LOCALE_MAP.put("hi", hiList); // hi

		PUBLISH_LANGUAGE_SET.add("eng");
		PUBLISH_LANGUAGE_SET.add("hi");
	}

	public static List<String> getLocaleList() {
		List<String> localeList = new ArrayList<>();
		for (Entry<String, List<String>> entry: LANGUAGE_LOCALE_MAP.entrySet()) {
			for (String localeName: entry.getValue()) {
				if (!localeList.contains(localeName)) {
					localeList.add(localeName);
				}
			}
		}
		return localeList;
	}

	public static String getStringValueForLanguage(Locale locale, String languageName) {
		return getValueForLanguage(locale, languageName, String.class);
	}

	public static String getStringValueForLanguage(Locale locale, String languageName, String defaultValue) {
		String value = getValueForLanguage(locale, languageName, String.class);
		if (StringUtils.isEmpty(value)) {
			value = defaultValue;
		}
		return value;
	}

	public static String getStringValueForLocale(Locale locale, String localeName) {
		return getValueForLocale(locale, localeName, String.class);
	}

	public static String getStringValueForLocale(Locale locale, String localeName, String defaultValue) {
		String value = getValueForLocale(locale, localeName, String.class);
		if (StringUtils.isEmpty(value)) {
			value = defaultValue;
		}
		return value;
	}

	public static void setStringValueForLanguage(Locale locale, String languageName, String value) {
		setValueForLanguage(locale, languageName, value);
	}

	public static String getStringValueForDefaultLanguage(Locale locale) {
		return getValueForLanguage(locale, DEFAULT_LANGUAGE_NAME, String.class);
	}

	public static void setStringValueForLocale(Locale locale, String localeName, String value) {
		setValueForLocale(locale, localeName, value);
	}

	public static void setStringValueForDefaultLanguage(Locale locale, String value) {
		setValueForLanguage(locale, DEFAULT_LANGUAGE_NAME, value);
	}

	public static void setStringValuesToEmpty(Locale locale) {
		for (String language: LANGUAGE_LOCALE_MAP.keySet()) {
			setStringValueForLanguage(locale, language, "");
		}
	}

	private static <T> T getValueForLanguage(Locale locale, String languageName, Class<T> clazz) {
		if (locale == null || languageName == null || clazz == null) {
			return null;
		}
		T value = null;
		if (LANGUAGE_LOCALE_MAP.containsKey(languageName)) {
			for (String localeName: LANGUAGE_LOCALE_MAP.get(languageName)) {
				value = getValueForLocale(locale, localeName, clazz);
				break;
			}
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getValueForLocale(Locale locale, String localeName, Class<T> clazz) {
		if (locale == null || localeName == null || clazz == null) {
			return null;
		}
		T value = null;
		try {
			if (getLocaleList().contains(localeName)) {
				String methodName = GETTER_PREFIX + StringUtils.capitalize(localeName.replaceAll("[-_]", ""));
				Method method = locale.getClass().getMethod(methodName);
				value = (T)method.invoke(locale);
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
		}
		return value;
	}

	private static void setValueForLanguage(Locale locale, String languageName, Object value) {
		if (locale == null || languageName == null || value == null) {
			return;
		}
		if (LANGUAGE_LOCALE_MAP.containsKey(languageName)) {
			for (String localeName: LANGUAGE_LOCALE_MAP.get(languageName)) {
				setValueForLocale(locale, localeName, value);
			}
		}
	}

	private static void setValueForLocale(Locale locale, String localeName, Object value) {
		if (locale == null || localeName == null || value == null) {
			return;
		}

		String methodName = SETTER_PREFIX + StringUtils.capitalize(localeName.replaceAll("[-_]", ""));
		try {
			Method setter = Locale.class.getMethod(methodName, String.class);
			setter.invoke(locale, value);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		}

	}

	public static Set<String> getLanguages() {
		return PUBLISH_LANGUAGE_SET;
	}

	public static List<String> getLocaleForLanguage(String languageName) {
		List<String> localeList = new ArrayList<>();
		if (LANGUAGE_LOCALE_MAP.get(languageName) != null) {
			for (String localeName: LANGUAGE_LOCALE_MAP.get(languageName)) {
				localeList.add(localeName);
			}
		}
		return localeList;
	}

	public static boolean isAllCountry(String countryKey) {
		return ALL_COUNTRY.equals(countryKey);
	}
}
