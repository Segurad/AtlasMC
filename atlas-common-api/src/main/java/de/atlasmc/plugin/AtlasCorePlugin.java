package de.atlasmc.plugin;

import de.atlasmc.plugin.startup.StartupContext;

public interface AtlasCorePlugin {
	
	void initStartupHandler(StartupContext context);
	
}
