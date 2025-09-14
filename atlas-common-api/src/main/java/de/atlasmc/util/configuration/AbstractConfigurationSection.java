package de.atlasmc.util.configuration;

import java.util.Objects;

public abstract class AbstractConfigurationSection implements ConfigurationSection {

	private final Configuration root;
	private final ConfigurationSection parent;
	
	protected AbstractConfigurationSection() {
		if (!(this instanceof Configuration))
			throw new IllegalStateException("Cannot create ConfigurationSection as root when not a instance of Configuration!");
		this.root = (Configuration) this;
		this.parent = null;
	}
	
	public AbstractConfigurationSection(ConfigurationSection parent) {
		this.parent = Objects.requireNonNull(parent);
		this.root = parent.getRoot();
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
			Object value = internalGet(key);
			return value != null ? value : def;
		}
		return section.get(key, def);
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
			internalSet(key, newSection);
			return newSection;
		}
		return section.createSection(path);
	}
	
	@Override
	public ListConfigurationSection createListSection(String path) {
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
			ListConfigurationSection newSection = createListSection();
			internalSet(key, newSection);
			return newSection;
		}
		return section.createListSection(path);
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
			return internalSet(key, value);
		} else {
			return section.set(key, value);
		}
	}
	
	@Override
	public Object remove(String path) {
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
				return null; // path not present
			section = section.getConfigurationSection(nextPathKey);
			if (section == null)
				return null;
		}
		String key = path.substring(searchIndex);
		if (section == this) {
			return internalRemove(key);
		}
		return section.remove(key);
	}

	@Override
	public Configuration getRoot() {
		return root;
	}

	@Override
	public ConfigurationSection getParent() {
		return parent;
	}
	
	protected abstract Object internalRemove(String key);
	
	protected abstract Object internalGet(String key);
	
	protected abstract Object internalSet(String key, Object value);
	
	protected ConfigurationSection createSection() {
		return new MemoryConfigurationSection(this);
	}
	
	protected ListConfigurationSection createListSection() {
		return new ListMemoryConfigurationSection(this);
	}

}
