package de.atlascore.test.plugin;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginConfiguration;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.plugin.PluginLoader;
import de.atlasmc.plugin.PrototypePlugin;
import de.atlasmc.plugin.Version;

public class MockPlugin implements Plugin {

	public static final MockPlugin PLUGIN;
	
	static {
		PLUGIN = new MockPlugin();
	}

	@Override
	public Plugin getPlugin() {
		return this;
	}

	@Override
	public void load() {}

	@Override
	public void enable() {}

	@Override
	public void disable() {}

	@Override
	public void unload() {}

	@Override
	public void reload() {}

	@Override
	public void loadConfiguration(PluginConfiguration config) {}

	@Override
	public void loadConfiguration(PluginConfiguration config, Object context) {}

	@Override
	public void unloadConfiguration(NamespacedKey config) {}

	@Override
	public void unloadConfiguration(NamespacedKey config, Object context) {}

	@Override
	public void reloadConfiguration(NamespacedKey config) {}

	@Override
	public void reloadConfigurations() {}

	@Override
	public Collection<PluginConfiguration> getConfigurations() {
		return List.of();
	}

	@Override
	public Version getVersion() {
		return Version.ZERO;
	}

	@Override
	public List<String> getAuthor() {
		return List.of();
	}

	@Override
	public String getName() {
		return "MockPlugin";
	}

	@Override
	public String getDescription() {
		return "Mock Plugin for testing purpose";
	}

	@Override
	public boolean isLoaded() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public PluginLoader getPluginLoader() {
		return null;
	}

	@Override
	public File getFile() {
		return null;
	}

	@Override
	public Log getLogger() {
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		return getClass().getResourceAsStream(name);
	}

	@Override
	public URL getResource(String name) {
		return getClass().getResource(name);
	}

	@Override
	public Collection<PluginHandle> getHandles() {
		return List.of();
	}

	@Override
	public PrototypePlugin getPrototype() {
		return null;
	}
	
}
