package de.atlasmc.util.factory;

import de.atlasmc.util.AtlasUtil;

public class BaseSingletonFactory<T> implements Factory {
	
	private final T instance;
	protected final Class<?> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseSingletonFactory(Class<?> clazz) {
		if (clazz == null)
			throw new IllegalArgumentException("Class can not be null!");
		this.clazz = clazz;
		this.instance = (T) AtlasUtil.getSingleton(clazz);
	}
	
	public T create() {
		return instance;
	}

}
