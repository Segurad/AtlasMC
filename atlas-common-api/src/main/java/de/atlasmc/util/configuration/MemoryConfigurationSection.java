package de.atlasmc.util.configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MemoryConfigurationSection extends AbstractConfigurationSection {

	private Map<String, Object> values;
	
	protected MemoryConfigurationSection() {
		super();
		this.values = new LinkedHashMap<>();
	}
	
	public MemoryConfigurationSection(ConfigurationSection parent) {
		super(parent);
		this.values = new LinkedHashMap<>();
	}

	@Override
	public Map<String, Object> asMap() {
		return values;
	}

	@Override
	public Set<String> getKeys() {
		return values.keySet();
	}

	@Override
	public int size() {
		return values.size();
	}
	
	@Override
	public void clear() {
		values.clear();		
	}

	@Override
	protected Object internalRemove(String key) {
		return values.remove(key);
	}

	@Override
	protected Object internalGet(String key) {
		return values.get(key);
	}

	@Override
	protected Object internalSet(String key, Object value) {
		return values.put(key, value);
	}

}
