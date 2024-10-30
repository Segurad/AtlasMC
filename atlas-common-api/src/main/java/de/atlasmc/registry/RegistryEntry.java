package de.atlasmc.registry;

import de.atlasmc.plugin.PluginHandle;

public interface RegistryEntry<T> {
	
	T value();
	
	PluginHandle plugin();
	
	String key();

}
