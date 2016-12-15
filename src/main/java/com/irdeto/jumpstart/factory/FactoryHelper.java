package com.irdeto.jumpstart.factory;

import static com.irdeto.jumpstart.domain.property.JumpstartPropertyKey.FACTORY_BASE_PACKAGE_KEY;
import static com.irdeto.manager.task.BeanUtil.propertiesManager;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.SimpleAliasRegistry;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.manager.properties.PropertyException;

public class FactoryHelper {
	private static final Logger LOG = LoggerFactory.getLogger(FactoryHelper.class);
	private static final DefinitionRegistry REGISTRY = initRegistry();

	private static DefinitionRegistry initRegistry() {
		DefinitionRegistry registry = new DefinitionRegistry();
		ClassPathBeanDefinitionScanner componentProvider = new ClassPathBeanDefinitionScanner(registry, false);
		componentProvider.setResourceLoader(new PathMatchingResourcePatternResolver(
			new DefaultResourceLoader(FactoryHelper.class.getClassLoader())
		));
		componentProvider.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
		try {
			String property;
			if (propertiesManager != null) {
				property = propertiesManager.getProperty(FACTORY_BASE_PACKAGE_KEY);
			} else {
				property = "com.irdeto.jumpstart";
			}
			if (nonNull(property)) {
				componentProvider.scan(property.replace('.', '/'));
			}
		} catch (PropertyException e) {
			componentProvider.scan("com/irdeto/jumpstart");
		}
		return registry;
	}

	/**
	 * @deprecated use {{@link #classesFor(Class)}} to get the {@link Map}.
	 */
	@Deprecated
	public static <U extends AbstractInstance<Base>> Map<Class<Base>, Class<U>> scanToMap(Class<U> instanceInterface) {
		Map<Class<Base>, Class<U>> map = new HashMap<>();

		for (Class<U> clazz: Collections.singletonList(instanceInterface)) {
			U instance = getInstance(clazz);
			if (instance != null) {
				map.put(instance.getEntityClass(), clazz);
			}
		}
		return map;
	}

	@Deprecated
	private static <T extends BaseEntity, U extends AbstractInstance<T>> U getInstance(Class<U> clazz) {
		if (clazz == null) {
			return null;
		}
		try {
			return clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	/**
	 * @deprecated use {{@link #classesFor(Class)}} to get the {@link Map} and
	 * {@link Map#get(Object)} with an entity as a parameter.
	 */
	@Deprecated
	public static <T extends BaseEntity, U extends AbstractInstance<T>> U getInstance(
			Map<Class<BaseEntity>, Class<U>> map, T entity) {
		if (entity == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Class<U> instanceClass = map.get(entity.getClass());
		if (instanceClass != null) {
			return getInstance(instanceClass);
		}
		return null;
	}

	public static Stream<? extends Class<?>> streamClasses(Class<?> anInterface) {
		return REGISTRY.streamClasses()
						.filter(anInterface::isAssignableFrom);
	}

	@SuppressWarnings("unchecked")
	public static Map<Class<Base>, Class<?>> classesFor(Class<?> clz) {
		return FactoryHelper.streamClasses(clz)
				.map(cl -> {
					AbstractInstance<Base> o;
					try {
						o = (AbstractInstance<Base>) cl.getConstructor().newInstance();
					} catch (InstantiationException |
							IllegalAccessException |
							InvocationTargetException |
							NoSuchMethodException |
							ClassCastException e) {
						return null;
					}
					return new Tuple(o.getEntityClass(), cl);
				})
				.filter(Objects::nonNull)
				.collect(toMap(t -> t.left, t -> t.right));
	}

	public static Object getInstance(Map<Class<Base>, Class<?>> classMap, Class<?> key) {
		if (key == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Class<?> instanceClass = classMap.get(key);

		if (instanceClass == null) {
			return null;
		}
		try {
			return instanceClass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	private static class DefinitionRegistry extends SimpleAliasRegistry implements BeanDefinitionRegistry {
		/** Map of bean definition objects, keyed by bean name */
		private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(64);

		@Override
		public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
				throws BeanDefinitionStoreException {

			Assert.hasText(beanName, "'beanName' must not be empty");
			Assert.notNull(beanDefinition, "BeanDefinition must not be null");
			beanDefinitionMap.put(beanName, beanDefinition);
		}

		@Override
		public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
			if (beanDefinitionMap.remove(beanName) == null) {
				throw new NoSuchBeanDefinitionException(beanName);
			}
		}

		@Override
		public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
			BeanDefinition bd = beanDefinitionMap.get(beanName);
			if (bd == null) {
				throw new NoSuchBeanDefinitionException(beanName);
			}
			return bd;
		}

		@Override
		public boolean containsBeanDefinition(String beanName) {
			return beanDefinitionMap.containsKey(beanName);
		}

		@Override
		public String[] getBeanDefinitionNames() {
			return StringUtils.toStringArray(beanDefinitionMap.keySet());
		}

		@Override
		public int getBeanDefinitionCount() {
			return beanDefinitionMap.size();
		}

		@Override
		public boolean isBeanNameInUse(String beanName) {
			return isAlias(beanName) || containsBeanDefinition(beanName);
		}

		Stream<? extends Class<?>> streamClasses() {
			return beanDefinitionMap
					.values()
					.stream()
					.map(bd -> {
						try {
							return Class.forName(bd.getBeanClassName());
						} catch (ClassNotFoundException | LinkageError e) {
							LOG.trace("Could not find class " + bd.getBeanClassName(), e);
							return null;
						}
					})
					.filter(Objects::nonNull);
		}
	}

	private static class Tuple<T extends Class<?>, U extends Class<?>> {
		public final T left;
		public final U right;

		Tuple(T left, U right) {
			this.left = left;
			this.right = right;
		}
	}
}
