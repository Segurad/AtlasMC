package de.atlasmc.util.configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import de.atlasmc.util.annotation.NotNull;

/**
 * Represents a Object that can be deserialized from a {@link ConfigurationSection}
 * and serialized as {@link ConfigurationSection}
 * Every class implementing {@link ConfigurationSerializable} is expected to have a constructor accepting
 * a {@link ConfigurationSection} as only parameter
 */
public interface ConfigurationSerializable {
	
	@NotNull
	public static <T extends ConfigurationSerializable> T deserialize(ConfigurationSection section, String className) {
		Class<T> clazz;
		try {
			@SuppressWarnings("unchecked")
			Class<T> c = (Class<T>) Class.forName(className);
			clazz = c;
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException(e);
		}
		return deserialize(section, clazz);
	}
	
	@NotNull
	public static <T extends ConfigurationSerializable> T deserialize(ConfigurationSection section, Class<T> clazz) {
		if (ConfigurationSerializable.class.isInstance(clazz))
			throw new IllegalArgumentException("Class must be assignable from ConfigurationSerializable: " + clazz.getName());
		Constructor<T> constructor;
		try {
			constructor = clazz.getConstructor(ConfigurationSection.class);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Class does not have constructor accepting only ConfigurationSection!", e);
		}
		T instance;
		try {
			instance = constructor.newInstance(section);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ConfigurationException("Error while creating new instance of class: " + clazz.getName(), e);
		}
		return instance;
	}
	
	public static <T extends ConfigurationSerializable> Collection<T> deserialize(Collection<ConfigurationSection> sections, Class<T> clazz) {
		return deserialize(sections, clazz, new ArrayList<>(sections.size()));
	}
	
	public static <T extends ConfigurationSerializable, C extends Collection<T>> C deserialize(Collection<ConfigurationSection> sections, Class<T> clazz, C buf) {
		if (ConfigurationSerializable.class.isInstance(clazz))
			throw new IllegalArgumentException("Class must be assignable from ConfigurationSerializable: " + clazz.getName());
		Constructor<T> constructor;
		try {
			constructor = clazz.getConstructor(ConfigurationSection.class);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Class does not have constructor accepting only ConfigurationSection!", e);
		}
		for (ConfigurationSection section : sections) {
			T instance;
			try {
				instance = constructor.newInstance(section);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new ConfigurationException("Error while creating new instance of class: " + clazz.getName(), e);
			}
			buf.add(instance);
		}
		return buf;
	}
	
	/**
	 * Returns a configuration representation of this object 
	 * @return configuration
	 */
	@NotNull
	default ConfigurationSection toConfiguration() {
		return toConfiguration(new MemoryConfiguration());
	}
	
	/**
	 * Adds this a representation of this object to the given configuration and returns it
	 * @param <T>
	 * @param config
	 * @return the given configuration
	 */
	@NotNull
	<T extends ConfigurationSection> T toConfiguration(@NotNull T config);
	
}
