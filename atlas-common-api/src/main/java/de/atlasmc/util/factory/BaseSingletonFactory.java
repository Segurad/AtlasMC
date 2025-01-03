package de.atlasmc.util.factory;

import java.lang.reflect.Field;

public class BaseSingletonFactory<T> implements Factory {
	
	private final T instance;
	protected final Class<?> clazz;
	
	public BaseSingletonFactory(Class<?> clazz) {
		if (clazz == null)
			throw new IllegalArgumentException("Class can not be null!");
		this.clazz = clazz;
		Field instanceField;
		try {
			instanceField = clazz.getDeclaredField("INSTANCE");
		} catch (NoSuchFieldException | SecurityException e) {
			throw new FactoryException("Error while fetching INSTANCE field!", e);
		}
		try {
			@SuppressWarnings("unchecked")
			T instance = (T) instanceField.get(null);
			this.instance = instance;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new FactoryException("Error while fetching INSTANCE value!", e);
		}
	}
	
	public T create() {
		return instance;
	}

}
