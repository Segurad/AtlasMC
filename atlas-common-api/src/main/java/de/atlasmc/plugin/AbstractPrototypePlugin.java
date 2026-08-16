package de.atlasmc.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationException;

public abstract class AbstractPrototypePlugin implements PrototypePlugin {

	private final String name;
	private final File file;
	private final Version version;
	private final Configuration pluginInfo;
	private final List<Dependency> dependencies;
	private final List<NamespacedKey> requiredFeatures;
	private final List<NamespacedKey> softRequiredFeatures;
	
	public AbstractPrototypePlugin(File file, Configuration pluginInfo) {
		this.file = Objects.requireNonNull(file, "file");
		this.pluginInfo = Objects.requireNonNull(pluginInfo, "info");
		this.name = pluginInfo.getString("name");
		if (name == null)
			throw new ConfigurationException("Name is not defined!");
		String rawVersion = pluginInfo.getString("version");
		if (rawVersion == null)
			throw new ConfigurationException("Version is not defined!");
		this.version = new Version(rawVersion);
		this.dependencies = loadList("dependencies", Dependency::of);
		this.requiredFeatures = loadList("required-features", NamespacedKey::of);
		this.softRequiredFeatures = loadList("soft-required-features", NamespacedKey::of);
	}
	
	private <T> List<T> loadList(String key, Function<String, T> constructor) {
		List<String> rawList = pluginInfo.getStringList(key, List.of());
		List<T> list = new ArrayList<>(rawList.size());
		for (String raw : rawList) {
			list.add(constructor.apply(raw));
		}
		return List.copyOf(list);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public Version getVersion() {
		return version;
	}

	@Override
	public Configuration getPluginInfo() {
		return pluginInfo;
	}

	@Override
	public List<Dependency> getDependencies() {
		return dependencies;
	}

	@Override
	public List<NamespacedKey> getRequiredFeatures() {
		return requiredFeatures;
	}

	@Override
	public List<NamespacedKey> getSoftRequiredFeatures() {
		return softRequiredFeatures;
	}

}
