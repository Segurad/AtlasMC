package de.atlascore.plugin;

import java.util.HashSet;
import java.util.Set;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PreparedPlugin;
import de.atlasmc.plugin.channel.PluginChannelException;
import de.atlasmc.util.configuration.Configuration;

public abstract class CoreAbstractPreparedPlugin implements PreparedPlugin {
	
	private final Configuration pluginInfo;
	protected Plugin plugin;
	protected boolean tryedToLoad;
	private final String name;
	protected Set<PreparedPlugin> dependencies;
	protected Set<PreparedPlugin> softDependencies;
	private boolean invalid;
	
	public CoreAbstractPreparedPlugin(Configuration pluginInfo) {
		if (pluginInfo == null)
			throw new IllegalArgumentException("PluginInfo can not be null!");
		this.name = pluginInfo.getString("name");
		if (name == null)
			throw new PluginChannelException("No name found in plugin info!");
		this.pluginInfo = pluginInfo;
	}
	
	@Override
	public boolean isLoaded() {
		return plugin != null;
	}

	@Override
	public final Configuration getPluginInfo() {
		return pluginInfo;
	}
	
	@Override
	public final String getName() {
		return name;
	}
	
	@Override
	public Set<PreparedPlugin> getDependencies() {
		if (dependencies == null)
			dependencies = new HashSet<>();
		return dependencies;
	}
	
	@Override
	public boolean hasDependencies() {
		return dependencies != null && !dependencies.isEmpty();
	}
	
	@Override
	public Set<PreparedPlugin> getSoftDependencies() {
		if (softDependencies == null)
			softDependencies = new HashSet<>();
		return softDependencies;
	}
	
	@Override
	public boolean hasSoftDependencies() {
		return softDependencies != null && !softDependencies.isEmpty();
	}
	
	@Override
	public boolean isInvalid() {
		return invalid;
	}
	
	@Override
	public void setInvalid() {
		invalid = true;
	}
	
	@Override
	public Plugin getPlugin() {
		return plugin;
	}

}
