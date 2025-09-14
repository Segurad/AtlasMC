package de.atlasmc.network.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginConfiguration;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
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
	private NamespacedKey template;
	private Map<NamespacedKey, String> data;
	private Map<NamespacedKey, NamespacedKey> plugins;
	private Map<String, List<String>> motd;
	
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
			motd = new HashMap<>(motds.size());
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
		String rawTemplate = config.getString("template");
		if (rawTemplate != null)
			template = NamespacedKey.of(rawTemplate);
		ConfigurationSection dataCfg = config.getConfigurationSection("data");
		if (dataCfg != null) {
			this.data = new HashMap<>();
			for (String key : dataCfg.getKeys()) {
				String path = dataCfg.getString(key);
				if (path == null)
					continue;
				this.data.put(NamespacedKey.of(key), path);
			}
		}
		ConfigurationSection pluginsCfg = config.getConfigurationSection("plugins");
		if (pluginsCfg != null) {
			this.plugins = new HashMap<>();
			for (String key : pluginsCfg.getKeys()) {
				String cfg = pluginsCfg.getString(key);
				if (cfg == null)
					continue;
				this.plugins.put(NamespacedKey.of(key), NamespacedKey.of(cfg));
			}
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
	
	public boolean hasTemplate() {
		return template != null;
	}
	
	/**
	 * Returns the repository key for a template used by this server or null
	 * @return key or null
	 */
	@Nullable
	public NamespacedKey getTemplate() {
		return template;
	}
	
	public boolean hasData() {
		return data != null && !data.isEmpty();
	}
	
	/**
	 * Returns repository keys of required data and the paths where the data should be located
	 * @return data
	 */
	@NotNull
	public Map<NamespacedKey, String> getData() {
		if (data == null)
			data = new HashMap<>();
		return data;
	}
	
	public boolean hasPlugins() {
		return plugins != null && !plugins.isEmpty();
	}
	
	/**
	 * Returns the repository keys of the required {@link Plugin}s and its {@link PluginConfiguration}
	 * @return plugins
	 */
	@NotNull
	public Map<NamespacedKey, NamespacedKey> getPlugins() {
		if (plugins == null)
			plugins = new HashMap<>();
		return plugins;
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
	
	/**
	 * Whether or not this server should stop if empty
	 * @return true if stop when empty
	 */
	public boolean isStopWhenEmpty() {
		return stopWhenEmpty;
	}
	
	/**
	 * Whether or not this server will safe chunk data
	 * @return true if safe chunk data
	 */
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
	
	/**
	 * Returns a map of available motd configurations for this server
	 * @return
	 */
	public Map<String, List<String>> getMotd() {
		return motd;
	}
	
}
