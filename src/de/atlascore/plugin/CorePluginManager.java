package de.atlascore.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginException;
import de.atlasmc.plugin.PluginLoader;
import de.atlasmc.plugin.PluginManager;

public class CorePluginManager implements PluginManager {

	private final CopyOnWriteArrayList<Plugin> plugins;
	private final CopyOnWriteArrayList<PluginLoader> loaders;
	
	public CorePluginManager() {
		this.plugins = new CopyOnWriteArrayList<Plugin>();
		this.loaders = new CopyOnWriteArrayList<PluginLoader>();
	}
	
	@Override
	public Plugin loadPlugin(File file) {
		if (file.exists())
			throw new IllegalArgumentException("File does not exist: ".concat(file.getPath()));
		for (PluginLoader loader : loaders) {
			if (!loader.canLoad(file)) continue;
			try {
				Plugin plugin = loader.load(file);
				if (plugin == null) continue;
				plugins.add(plugin);
				return plugin;
			} catch (IOException e) {}
		}
		throw new IllegalArgumentException("Unable to load Plugin: ".concat(file.getPath()));
	}

	@Override
	public void unloadPlugin(Plugin plugin) {
		plugin.disable();
		plugin.unload();
	}

	@Override
	public List<Plugin> loadPlugins(File directory) {
		List<Plugin> list = null;
		if (directory.exists())
			throw new IllegalArgumentException("Directory does not exist: ".concat(directory.getPath()));
		for (File file : directory.listFiles()) {
			for (PluginLoader loader : loaders) {
				if (!loader.canLoad(file)) continue;
				try {
					Plugin plugin = loader.load(file);
					if (plugin == null) continue;
					plugins.add(plugin);
					if (list == null)
						list = new ArrayList<Plugin>();
					list.add(plugin);
					break;
				} catch (IOException | PluginException e) {}
			}
		}
		return list == null ? List.of() : list;
	}

	@Override
	public void unloadPlugins(Plugin... plugins) {
		for (Plugin plugin : plugins) {
			unloadPlugin(plugin);
		}
	}

	@Override
	public List<Plugin> getPlugins() {
		return List.copyOf(plugins);
	}

	@Override
	public Plugin getPlugin(String name) {
		for (Plugin plugin : plugins) {
			if (plugin.getName().equals(name)) return plugin;
		}
		return null;
	}

	@Override
	public List<PluginLoader> getLoaders() {
		return List.copyOf(loaders);
	}

	@Override
	public boolean addLoader(PluginLoader loader) {
		if (loaders.contains(loader)) return false;
		loaders.add(loader);
		return true;
	}

	@Override
	public boolean removeLoader(PluginLoader loader) {
		return loaders.remove(loader);
	}

	public void loadCoreModuls() {
		// TODO Auto-generated method stub
		
	}

}
