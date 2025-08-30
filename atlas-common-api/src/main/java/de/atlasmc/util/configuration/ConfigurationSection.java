package de.atlasmc.util.configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * Section of a {@link Configuration} e.g. sub map or list
 */
public interface ConfigurationSection {

	boolean contains(String path);

	@Nullable
	ConfigurationSection getConfigurationSection(String path);
	
	@Nullable
	Object get(String path);
	
	@Nullable
	Object get(String path, Object def);
	
	@Nullable
	String getString(String path);
	
	@Nullable
	String getString(String path, String def);

	int getInt(String path);
	
	int getInt(String path, int def);
	
	long getLong(String path);
	
	long getLong(String path, long def);
	
	double getDouble(String path);
	
	double getDouble(String path, double def);
	
	boolean getBoolean(String path);
	
	boolean getBoolean(String path, boolean def);
	
	float getFloat(String path);
	
	float getFloat(String path, float def);
	
	@Nullable
	List<String> getStringList(String path);
	
	@Nullable
	List<String> getStringList(String path, List<String> def);
	
	@Nullable
	List<ConfigurationSection> getConfigurationList(String path);
	
	@Nullable
	List<ConfigurationSection> getConfigurationList(String path, List<ConfigurationSection> def);
	
	@Nullable
	<T> List<T> getListOfType(String path, Class<T> clazz);
	
	@Nullable
	<T> List<T> getListOfType(String path, Class<T> clazz, List<T> def);
	
	@Nullable
	List<?> getList(String path);
	
	@Nullable
	List<?> getList(String path, List<?> def);
	
	@NotNull
	ConfigurationSection createSection(String path);
	
	@NotNull
	Configuration getRoot();
	
	/**
	 * Sets the value at the given path and returns the previous value.
	 * If the value is null and no {@link ConfigurationSection} is present at the paths end these sections will not be created and so the value will not be set.
	 * @param path
	 * @param value
	 * @return old value or null
	 */
	@Nullable
	Object set(String path, Object value);
	
	/**
	 * Removes the last path element and returns the values currently set
	 * @param path
	 * @return value or null
	 */
	@Nullable
	Object remove(String path);
	
	@NotNull
	Map<String, Object> getValues();
	
	@NotNull
	Set<String> getKeys();
	
	/**
	 * Returns the number of elements in this section
	 * @return size
	 */
	int getSize();
	
	/**
	 * Removes all elements from this section
	 */
	void clear();

}
