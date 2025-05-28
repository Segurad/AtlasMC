package de.atlasmc.util.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassFactory<T> implements Factory {
	
	private final Constructor<? extends T> constructor;
	protected final Class<? extends T> clazz;
	
	public ClassFactory(Class<? extends T>  clazz, Class<?>... parameterTypes) {
		if (clazz == null)
			throw new IllegalArgumentException("Class can not be null!");
		this.clazz = clazz;
		this.constructor = getConstructor(clazz, parameterTypes);
	}
	
	protected T create(Object... args) {
		try {
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FactoryException("Error while creating component effect", e);
		}
	}
	
	public static <T> T createInstance(Constructor<T> constructor, Object... args) {
		try {
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FactoryException("Error while creating component effect", e);
		}
	}
	
	public static <T> Class<T> getClass(String name) {
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) Class.forName(name);
			return clazz;
		} catch (ClassNotFoundException e) {
			throw new FactoryException("Class not found: " + name);
		}
	}
	
	public static <T> Constructor<T> getConstructor(String className, Class<?>... parameterTypes) {
		Class<T> clazz = getClass(className);
		return getConstructor(clazz, parameterTypes);
	}
	
	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
		try {
			return clazz.getConstructor(parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new FactoryException("Error while fetching constructor for class: " + clazz, e);
		}
	}

}
