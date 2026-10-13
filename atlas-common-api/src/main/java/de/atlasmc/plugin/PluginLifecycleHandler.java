package de.atlasmc.plugin;

import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryHolder.Target;

/**
 * Handler for executing operations within the Plugin Lifecycle.
 * e.g. doing cleanup after disabling a Plugin
 */
@RegistryHolder(key = "atlas:plugin/lifecycle", target = Target.INSTANCE)
public interface PluginLifecycleHandler {
	
	static RegistryKey<PluginLifecycleHandler> REGISTRY_KEY = Registries.getRegistryKey(PluginLifecycleHandler.class);
	
	/**
	 * Called when a Plugin is enabled
	 * @implSpec should be called before the Plugin handles enabling
	 * @param plugin
	 */
	default void onEnable(Plugin plugin) {
		// override as needed
	}
	
	/**
	 * Called when a Plugin is loaded
	 * @param plugin
	 */
	default void onLoad(Plugin plugin) {
		// override as needed
	}
	
	/**
	 * Called after a Plugin was disabled
	 * @param plugin
	 */
	default void onDisable(Plugin plugin) {
		// override as needed
	}
	
	/**
	 * Called after a Plugin was unloaded
	 * @param plugin
	 */
	default void onUnload(Plugin plugin) {
		// override as needed
	}

}
