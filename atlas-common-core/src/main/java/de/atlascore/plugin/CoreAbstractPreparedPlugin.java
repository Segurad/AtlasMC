package de.atlascore.plugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PreparedPlugin;
import de.atlasmc.plugin.channel.PluginChannelException;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.Configuration;

public abstract class CoreAbstractPreparedPlugin implements PreparedPlugin {
	
	private final Configuration pluginInfo;
	protected volatile Plugin plugin;
	protected volatile boolean tryedToLoad;
	private final String name;
	protected Set<Future<Plugin>> dependencies;
	protected Set<Future<Plugin>> softDependencies;
	private volatile boolean invalid;
	
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
	public void addDependency(Future<Plugin> future) {
		if (dependencies == null)
			dependencies = new HashSet<>();
		dependencies.add(future);
	}
	
	@Override
	public boolean hasDependencies() {
		return dependencies != null && !dependencies.isEmpty();
	}
	
	@Override
	public void addSoftDependency(Future<Plugin> future) {
		if (softDependencies == null)
			softDependencies = new HashSet<>();
		softDependencies.add(future);
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
	
	@Override
	public Collection<Future<Plugin>> getDependencies() {
		return dependencies;
	}
	
	@Override
	public Collection<Future<Plugin>> getSoftDependencies() {
		return softDependencies;
	}

}
