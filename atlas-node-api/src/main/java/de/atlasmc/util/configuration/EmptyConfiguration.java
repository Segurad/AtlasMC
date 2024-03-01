package de.atlasmc.util.configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

final class EmptyConfiguration implements Configuration {
	
	public static final ConfigurationSection INSTANCE = new EmptyConfiguration();

	private EmptyConfiguration() {}
	
	@Override
	public boolean contains(String path) {
		return false;
	}

	@Override
	public ConfigurationSection getConfigurationSection(String path) {
		return null;
	}

	@Override
	public Object get(String path) {
		return null;
	}

	@Override
	public Object get(String path, Object def) {
		return def;
	}

	@Override
	public String getString(String path) {
		return null;
	}

	@Override
	public String getString(String path, String def) {
		return def;
	}

	@Override
	public int getInt(String path) {
		return 0;
	}

	@Override
	public int getInt(String path, int def) {
		return def;
	}

	@Override
	public long getLong(String path) {
		return 0;
	}

	@Override
	public long getLong(String path, long def) {
		return def;
	}

	@Override
	public double getDouble(String path) {
		return 0;
	}

	@Override
	public double getDouble(String path, double def) {
		return def;
	}

	@Override
	public boolean getBoolean(String path) {
		return false;
	}

	@Override
	public boolean getBoolean(String path, boolean def) {
		return def;
	}

	@Override
	public float getFloat(String path) {
		return 0;
	}

	@Override
	public float getFloat(String path, float def) {
		return def;
	}

	@Override
	public List<String> getStringList(String path) {
		return null;
	}

	@Override
	public List<String> getStringList(String path, List<String> def) {
		return def;
	}

	@Override
	public <T> List<T> getListOfType(String path, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> List<T> getListOfType(String path, Class<T> clazz, List<T> def) {
		return def;
	}

	@Override
	public List<?> getList(String path) {
		return null;
	}

	@Override
	public List<?> getList(String path, List<?> def) {
		return def;
	}

	@Override
	public ConfigurationSection createSection(String path) {
		return null;
	}

	@Override
	public Configuration getRoot() {
		return this;
	}

	@Override
	public Object set(String path, Object value) {
		return null;
	}

	@Override
	public Map<String, Object> getValues() {
		return Map.of();
	}

	@Override
	public Set<String> getKeys() {
		return Set.of();
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public void clear() {}

}
