package de.atlasmc.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.InvalidConfigurationException;

public abstract class AbstractPrototypePlugin implements PrototypePlugin {

	private final String name;
	private final File file;
	private final Version version;
	private final Configuration pluginInfo;
	private final List<Dependency> dependencies;
	private final List<NamespacedKey> requiredFeatures;
	private final List<NamespacedKey> softRequiredFeatures;
	
	public AbstractPrototypePlugin(File file, Configuration pluginInfo) {
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		if (pluginInfo == null)
			throw new IllegalArgumentException("Plugin info can not be null!");
		this.file = file;
		this.pluginInfo = pluginInfo;
		this.name = pluginInfo.getString("name");
		if (name == null)
			throw new InvalidConfigurationException("Name is not defined!");
		String rawVersion = pluginInfo.getString("version");
		if (rawVersion == null)
			throw new InvalidConfigurationException("Version is not defined!");
		this.version = new Version(rawVersion);
		List<String> rawDependencies = pluginInfo.getStringList("dependencies", List.of());
		List<Dependency> dependencies = new ArrayList<>(rawDependencies.size());
		for (String raw : rawDependencies) {
			dependencies.add(Dependency.of(raw));
		}
		this.dependencies = List.copyOf(dependencies);
		List<String> rawRequiredFeatures = pluginInfo.getStringList("required-features", List.of());
		List<NamespacedKey> requiredFeatures = new ArrayList<>(rawRequiredFeatures.size());
		for (String raw : rawRequiredFeatures) {
			requiredFeatures.add(NamespacedKey.of(raw));
		}
		this.requiredFeatures = List.copyOf(requiredFeatures);
		List<String> rawSoftRequiredFeatures = pluginInfo.getStringList("soft-required-features", List.of());
		List<NamespacedKey> softRequiredFeatures = new ArrayList<>(rawRequiredFeatures.size());
		for (String raw : rawSoftRequiredFeatures) {
			softRequiredFeatures.add(NamespacedKey.of(raw));
		}
		this.softRequiredFeatures = List.copyOf(softRequiredFeatures);
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
