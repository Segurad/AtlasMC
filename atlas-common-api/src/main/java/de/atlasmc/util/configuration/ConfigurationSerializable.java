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
	
	static final String DEFAULT_TYPE_KEY = "==type";
	
	public static <C extends ConfigurationSerializable, T extends C> T deserializeSafe(ConfigurationSection section, Class<C> required) {
		String raw = section.getString(DEFAULT_TYPE_KEY);
		if (raw == null)
			throw new ConfigurationException("No field found with name \"" + DEFAULT_TYPE_KEY + "\"!");
		Class<T> clazz;
		try {
			@SuppressWarnings("unchecked")
			Class<T> c = (Class<T>) Class.forName(raw);
			clazz = c;
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException(e);
		}
		if (!required.isAssignableFrom(clazz))
			throw new ConfigurationException("Deserialization class must be assignable from " + required + ":" + clazz);
		return deserialize(section, clazz);
	}
	
	@NotNull
	public static <T extends ConfigurationSerializable> T deserialize(ConfigurationSection section, String className, ClassLoader loader) {
		Class<T> clazz;
		try {
			@SuppressWarnings("unchecked")
			Class<T> c = (Class<T>) loader.loadClass(className);
			clazz = c;
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException(e);
		}
		return deserialize(section, clazz);
	}
	
	@NotNull
	public static <T extends ConfigurationSerializable> T deserialize(ConfigurationSection section, Class<T> clazz) {
		var constructor = getConstructor(clazz);
		return construct(constructor, section);
	}
	
	public static <T extends ConfigurationSerializable> Collection<T> deserialize(Collection<ConfigurationSection> sections, Class<T> clazz) {
		return deserialize(sections, clazz, new ArrayList<>(sections.size()));
	}
	
	public static <T extends ConfigurationSerializable, C extends Collection<T>> C deserialize(Collection<ConfigurationSection> sections, Class<T> clazz, C buf) {
		var constructor = getConstructor(clazz);
		for (ConfigurationSection section : sections) {
			buf.add(construct(constructor, section));
		}
		return buf;
	}
	
	private static <T extends ConfigurationSerializable> T construct(Constructor<T> constructor, ConfigurationSection section) {
		T instance;
		try {
			instance = constructor.newInstance(section);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ConfigurationException("Error while creating new instance of class: " + constructor.getDeclaringClass().getName(), e);
		}
		return instance;
	}
	
	private static <T extends ConfigurationSerializable> Constructor<T> getConstructor(Class<T> clazz) {
		if (ConfigurationSerializable.class.isInstance(clazz))
			throw new IllegalArgumentException("Class must be assignable from ConfigurationSerializable: " + clazz.getName());
		try {
			return clazz.getConstructor(ConfigurationSection.class);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Class does not have constructor accepting only ConfigurationSection!", e);
		}
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
