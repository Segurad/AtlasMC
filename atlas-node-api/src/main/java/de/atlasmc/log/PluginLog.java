package de.atlasmc.log;

import de.atlasmc.plugin.Plugin;

public interface PluginLog extends Log {

	Plugin getPlugin();
	
	default String getName() {
		return getPlugin().getName();
	}
	
}
