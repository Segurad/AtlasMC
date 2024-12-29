package de.atlasmc.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.configuration.ConfigurationSection;

public interface RegistryHandler {

	<T extends Registry<?>> T getRegistry(NamespacedKey key);

	<T> T getDefault(NamespacedKey key);
	
	<T> T getDefault(String key);
	
	<T> T getDefault(Class<?> clazz);

	<T extends Registry<?>> T createRegistry(NamespacedKey key, Class<?> clazz, Target target);
	
	<T extends Registry<?>> T createRegistry(Class<?> clazz);
	
	<T extends Registry<?>> T getRegistry(String key);
	
	<T extends Registry<?>> T getRegistry(Class<?> clazz);

	Registry<Registry<?>> getRegistries();

	boolean registerRegistry(Registry<?> registry);
	
	boolean removePluginEntries(PluginHandle plugin);
	
	/**
	 * Loads all registries as defined in the manifest
	 * @param plugin
	 */
	void loadRegistries(Plugin plugin);
	
	void loadRegistries(Plugin plugin, ConfigurationSection config);
	
	/**
	 * Loads all registry entries as defined in the manifest
	 * @param plugin
	 */
	void loadRegistryEntries(Plugin plugin);
	
	void loadRegistryEntries(PluginHandle plugin, ConfigurationSection config);

}
