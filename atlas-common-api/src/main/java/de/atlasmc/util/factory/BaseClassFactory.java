package de.atlasmc.util.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BaseClassFactory<T> implements Factory {
	
	private final Constructor<? extends T> constructor;
	protected final Class<? extends T> clazz;
	
	public BaseClassFactory(Class<? extends T>  clazz, Class<?>... parameterTypes) {
		if (clazz == null)
			throw new IllegalArgumentException("Class can not be null!");
		this.clazz = clazz;
		try {
			this.constructor = clazz.getConstructor(parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new FactoryException("Error while fetching constructor for class: " + clazz.getName(), e);
		}
	}
	
	protected T create(Object... arguments) {
		try {
			return constructor.newInstance(arguments);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FactoryException("Error while creating component effect", e);
		}
	}
	
	protected static <T> Class<T> getClass(String name) throws ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) Class.forName(name);
		return clazz;
	}

}
