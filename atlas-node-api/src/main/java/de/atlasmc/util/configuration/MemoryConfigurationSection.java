package de.atlasmc.util.configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.atlasmc.util.NumberConversion;

public class MemoryConfigurationSection implements ConfigurationSection {

	private Map<String, Object> values;

	private final Configuration root;
	
	protected MemoryConfigurationSection() {
		if (!(this instanceof Configuration))
			throw new IllegalStateException("Cannot create ConfigurationSection as root when not a instance of Configuration!");
		this.root = (Configuration) this;
		this.values = new LinkedHashMap<>();
	}
	
	public MemoryConfigurationSection(ConfigurationSection parent) {
		this.root = parent.getRoot();
		this.values = new LinkedHashMap<>();
	}
	
	@Override
	public Object get(String path) {
		return get(path, null);
	}
	
	@Override
	public Object get(String path, Object def) {
		if (path == null)
			throw new IllegalArgumentException("Path can not be null!");
		if (path.length() == 0)
			return this;

		ConfigurationSection section = this;
		int lastIndex = -1;
		int searchIndex = -1;
		// find section of key
		while ((lastIndex = path.indexOf('.', searchIndex = lastIndex+1)) != -1) {
			String nextPathKey = path.substring(searchIndex, lastIndex);
			if (!section.contains(nextPathKey))
				return def; // path not present
			section = section.getConfigurationSection(nextPathKey);
			if (section == null)
				return def;
		}
		String key = path.substring(searchIndex);
		if (section == this) {
			Object value = values.get(key);
			return value != null ? value : def;
		}
		return section.get(key, def);
	}

	@Override
	public boolean contains(String path) {
		return get(path) != null;
	}

	@Override
	public ConfigurationSection getConfigurationSection(String path) {
		Object value = get(path);
		if (value instanceof ConfigurationSection)
			return (ConfigurationSection) value;
		return null;
	}

	@Override
	public String getString(String path) {
		return getString(path, null);
	}
	
	@Override
	public String getString(String path, String def) {
		Object val = get(path, def);
		return val == null ? def : val.toString();
	}
	
	@Override
	public int getInt(String path) {
		return getInt(path, 0);
	}
	
	@Override
	public int getInt(String path, int def) {
		Object val = get(path, null);
		return NumberConversion.toInt(val, def);
	}
	
	@Override
	public long getLong(String path) {
		return getLong(path, 0);
	}
	
	@Override
	public long getLong(String path, long def) {
		Object val = get(path, null);
		return NumberConversion.toLong(val, def);
	}

	@Override
	public double getDouble(String path) {
		return getDouble(path, 0.0);
	}

	@Override
	public double getDouble(String path, double def) {
		Object val = get(path, null);
		return NumberConversion.toDouble(val, def);
	}

	@Override
	public boolean getBoolean(String path) {
		return getBoolean(path, false);
	}

	@Override
	public boolean getBoolean(String path, boolean def) {
		Object val = get(path, def);
		return val instanceof Boolean ? (Boolean) val : def;
	}

	@Override
	public float getFloat(String path) {
		return getFloat(path, 0.0f);
	}

	@Override
	public float getFloat(String path, float def) {
		Object val = get(path, null);
		return NumberConversion.toFloat(val, def);
	}

	@Override
	public List<String> getStringList(String path) {
		return getStringList(path, null);
	}

	@Override
	public List<String> getStringList(String path, List<String> def) {
		return getListOfType(path, String.class, def);
	}

	@Override
	public List<?> getList(String path) {
		return getList(path, null);
	}

	@Override
	public List<?> getList(String path, List<?> def) {
		Object val = get(path, null);
		return (val instanceof List) ? (List<?>) val : def;
	}

	@Override
	public <T> List<T> getListOfType(String path, Class<T> clazz) {
		return getListOfType(path, clazz, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getListOfType(String path, Class<T> clazz, List<T> def) {
		List<?> list = getList(path, def);
		if (list == null)
			return def;
		for (Object o : list)
			if (!clazz.isInstance(o))
				return def;
		return (List<T>) list;
	}

	@Override
	public ConfigurationSection createSection(String path) {
		if (path == null)
			throw new IllegalArgumentException("Path can not be null!");
		if (path.length() == 0)
			throw new IllegalArgumentException("Path can not be empty!");

		ConfigurationSection section = this;
		int lastIndex = -1;
		int searchIndex = -1;
		// find section of key
		while ((lastIndex = path.indexOf('.', searchIndex = lastIndex+1)) != -1) {
			String nextPathKey = path.substring(searchIndex, lastIndex);
			ConfigurationSection nextSection = getConfigurationSection(nextPathKey);
			if (nextSection == null)
				nextSection = createSection(nextPathKey);
			section = nextSection;
		}
		String key = path.substring(searchIndex);
		if (section == this) {
			ConfigurationSection newSection = createSection();
			values.put(key, newSection);
			return newSection;
		}
		return section.createSection(path);
	}

	@Override
	public Object set(String path, Object value) {
		if (path == null)
			throw new IllegalArgumentException("Path can not be null!");
		if (path.length() == 0)
			throw new IllegalArgumentException("Path can not be empty!");

		ConfigurationSection section = this;
		int lastIndex = -1;
		int searchIndex = -1;
		// find section of key
		while ((lastIndex = path.indexOf('.', searchIndex = lastIndex+1)) != -1) {
			String nextPathKey = path.substring(searchIndex, lastIndex);
			ConfigurationSection nextSection = getConfigurationSection(nextPathKey);
			if (nextSection == null) {
				if (value == null)
					return null; // avoid creating null path
				nextSection = createSection(nextPathKey);
			}
			section = nextSection;
		}
		String key = path.substring(searchIndex);
		if (section == this) {
			return values.put(key, value);
		} else {
			return section.set(key, value);
		}
	}
	
	protected ConfigurationSection createSection() {
		return new MemoryConfigurationSection(this);
	}

	@Override
	public Configuration getRoot() {
		return root;
	}

	@Override
	public Map<String, Object> getValues() {
		return values;
	}

	@Override
	public Set<String> getKeys() {
		return values.keySet();
	}

	@Override
	public int getSize() {
		return values.size();
	}
	
	@Override
	public void clear() {
		values.clear();		
	}

}
