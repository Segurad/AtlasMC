package de.atlasmc.registry;

import de.atlasmc.plugin.Plugin;

public interface RegistryEntry<T> {
	
	T value();
	
	Plugin plugin();
	
	String key();

}
