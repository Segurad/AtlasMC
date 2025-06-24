package de.atlasmc.registry;

import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.key.CharKey;

public interface RegistryEntry<T> {
	
	@NotNull
	T value();
	
	@NotNull
	PluginHandle plugin();
	
	@NotNull
	CharKey key();

}
