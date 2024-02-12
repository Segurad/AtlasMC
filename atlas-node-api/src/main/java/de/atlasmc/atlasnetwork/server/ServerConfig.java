package de.atlasmc.atlasnetwork.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginConfiguration;
import de.atlasmc.util.Pair;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;

public class ServerConfig implements Cloneable {

	private int slots;
	private boolean dynamicSlots;
	private String factory;
	private Configuration config;
	private boolean stopWhenEmpty;
	private boolean safeChunks;
	private boolean isStatic;
	private boolean maintenance;
	private int stopUserCount;
	private int stopTime;
	private Map<String, List<String>> motd;
	private Collection<String> requiredData;
	private Collection<Pair<String, String>> requiredPlugins;
	
	public ServerConfig() {}
	
	public ServerConfig(ConfigurationSection config) {
		factory = config.getString("server-factory");
		ConfigurationSection factoryConfig = config.getConfigurationSection("config");
		config = new MemoryConfiguration(factoryConfig);
		isStatic = config.getBoolean("static");
		stopTime = config.getInt("auto-stop-delay");
		stopUserCount = config.getInt("auto-stop-users");
		dynamicSlots = config.getBoolean("dynamic-slots");
		slots = config.getInt("slots");
		ConfigurationSection motds = config.getConfigurationSection("motd");
		if (motds != null) {
			motd = new HashMap<>(motds.getSize());
			for (String key : motds.getKeys()) {
				List<String> textList = motds.getStringList(key);
				if (textList == null) {
					String text = motds.getString(key);
					if (text != null)
						textList = List.of(text);
				}
				if (textList == null)
					continue;
				motd.put(key, textList);
			}
		}
		List<String> data = config.getStringList("data", List.of());
		this.requiredData = List.copyOf(data);
		@SuppressWarnings("unchecked")
		List<ConfigurationSection> plugins = (List<ConfigurationSection>) config.getList("plugins");
		if (plugins != null && !plugins.isEmpty()) {
			List<Pair<String, String>> requiredPlugins = new ArrayList<>(plugins.size());
			for (ConfigurationSection pluginCfg : plugins) {
				String plugin = pluginCfg.getString("plugin");
				String cfg = pluginCfg.getString("config");
				requiredPlugins.add(Pair.of(plugin, cfg));
			}
			this.requiredPlugins = List.copyOf(requiredPlugins);
		} else {
			this.requiredPlugins = List.of();
		}
	}
	
	public ServerConfig clone() {
		ServerConfig clone = null;
		try {
			clone = (ServerConfig) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (clone == null)
			return null;
		clone.config = new MemoryConfiguration(config);
		if (motd != null) {
			clone.motd = new HashMap<>(motd.size());
			for (String key : motd.keySet()) {
				clone.motd.put(key, new ArrayList<>(motd.get(key)));
			}
		}
		return clone;
	}

	public int getSlots() {
		return slots;
	}
	
	public Collection<String> getRequiredData() {
		return requiredData;
	}
	
	/**
	 * Returns the repository keys of the required {@link Plugin}s and its {@link PluginConfiguration}
	 * @return plugins
	 */
	@NotNull
	public Collection<Pair<String, String>> getRequiredPlugins() {
		return requiredPlugins;
	}
	
	public boolean isStatic() {
		return isStatic;
	}
	
	public int getStopUserCount() {
		return stopUserCount;
	}
	
	public String getFactory() {
		return factory;
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public int getStopTime() {
		return stopTime;
	}
	
	public boolean isStopWhenEmpty() {
		return stopWhenEmpty;
	}
	
	public boolean getSafeChunks() {
		return safeChunks;
	}
	
	public boolean hasDynamicSlots() {
		return dynamicSlots;
	}
	
	public boolean isMaintenance() {
		return maintenance;
	}
	
	public List<String> getMotd(String key) {
		return motd.get(key);
	}
	
	public Map<String, List<String>> getMotd() {
		return motd;
	}
	
}
