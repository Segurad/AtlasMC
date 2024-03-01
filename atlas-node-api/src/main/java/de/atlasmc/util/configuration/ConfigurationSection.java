package de.atlasmc.util.configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Section of a {@link Configuration} e.g. sub map or list
 */
public interface ConfigurationSection {

	boolean contains(String path);

	ConfigurationSection getConfigurationSection(String path);
	
	Object get(String path);
	
	Object get(String path, Object def);
	
	String getString(String path);
	
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
	
	List<String> getStringList(String path);
	
	List<String> getStringList(String path, List<String> def);
	
	<T> List<T> getListOfType(String path, Class<T> clazz);
	
	<T> List<T> getListOfType(String path, Class<T> clazz, List<T> def);
	
	List<?> getList(String path);
	
	List<?> getList(String path, List<?> def);
	
	ConfigurationSection createSection(String path);
	
	Configuration getRoot();
	
	Object set(String path, Object value);
	
	Map<String, Object> getValues();
	
	Set<String> getKeys();
	
	/**
	 * Returns the number of elements in this section
	 * @return size
	 */
	int getSize();
	
	void clear();

}
