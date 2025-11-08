package de.atlasmc.bootstrap;

import java.io.File;

import de.atlasmc.Atlas;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key="atlas-core:system_init/load_plugins")
public class CoreLoadPluginsTaskFactory implements AtlasThreadTaskFactory {

	@SuppressWarnings("unchecked")
	@Override
	public AtlasThreadTask<StartupContext> createTask(ConfigurationSection config) {
		return (context, _) -> {
			Log log = context.getLogger();
			log.info("Loading core modules...");
			File tmpCoremodulDir = new File(Atlas.getWorkdir(), "tmp/modules/");
			tmpCoremodulDir.mkdirs();
			File coremodulDir = new File(Atlas.getWorkdir(), "modules/");
			coremodulDir.mkdirs();
			PluginManager pm = Atlas.getPluginManager();
			pm.loadPlugins(coremodulDir);
			pm.loadPlugins(tmpCoremodulDir);
		};
	}

}
