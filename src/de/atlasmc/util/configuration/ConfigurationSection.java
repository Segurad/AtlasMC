package de.atlasmc.util.configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Section of a {@link Configuration} e.g. sub map or list
 */
public interface ConfigurationSection {

	public boolean contains(String path);

	public ConfigurationSection getConfigurationSection(String path);

	public Object get(String path);
	
	public Object get(String path, Object def);

	public int getInt(String path);
	
	public int getInt(String path, int def);
	
	public double getDouble(String path);
	
	public double getDouble(String path, double def);
	
	public boolean getBoolean(String path);
	
	public boolean getBoolean(String path, boolean def);
	
	public float getFloat(String path);
	
	public float getFloat(String path, float def);
	
	public List<String> getStringList(String path);
	
	public List<String> getStringList(String path, List<String> def);
	
	public <T> List<T> getListOfType(String path, Class<T> clazz);
	
	public <T> List<T> getListOfType(String path, Class<T> clazz, List<T> def);
	
	public List<?> getList(String path);
	
	public List<?> getList(String path, List<?> def);
	
	public ConfigurationSection createSection(String path);
	
	public Configuration getRoot();
	
	public void set(String path, Object value);
	
	public Map<String, Object> getValues();
	
	public Set<String> getKeys();

}
