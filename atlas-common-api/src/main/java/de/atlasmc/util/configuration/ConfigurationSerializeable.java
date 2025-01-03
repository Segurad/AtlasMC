package de.atlasmc.util.configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import de.atlasmc.util.annotation.NotNull;

/**
 * Represents a Object that can be deserialized from a {@link ConfigurationSection}
 * and serialized as {@link ConfigurationSection}
 * Every class implementing {@link ConfigurationSerializeable} is expected to have a constructor accepting
 * a {@link ConfigurationSection} as only parameter
 */
public interface ConfigurationSerializeable {
	
	public static <T extends ConfigurationSerializeable> T deserialize(ConfigurationSection section, Class<T> clazz) {
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
			throw new InvalidConfigurationException("Error while creating new instance of class: " + clazz.getName(), e);
		}
		return instance;
	}
	
	public static <T extends ConfigurationSerializeable> Collection<T> deserialize(Collection<ConfigurationSection> sections, Class<T> clazz) {
		return deserialize(new ArrayList<>(sections.size()), clazz);
	}
	
	public static <T extends ConfigurationSerializeable, C extends Collection<T>> C deserialize(Collection<ConfigurationSection> sections, Class<T> clazz, C buf) {
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
				throw new InvalidConfigurationException("Error while creating new instance of class: " + clazz.getName(), e);
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
	 * @param configuration
	 * @return configuration
	 */
	@NotNull
	<T extends ConfigurationSection> T toConfiguration(T configuration);
	
}
