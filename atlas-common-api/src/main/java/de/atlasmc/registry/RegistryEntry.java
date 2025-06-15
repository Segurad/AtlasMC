package de.atlasmc.registry;

import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.annotation.NotNull;

public interface RegistryEntry<T> {
	
	@NotNull
	T value();
	
	@NotNull
	PluginHandle plugin();
	
	@NotNull
	String key();

}
