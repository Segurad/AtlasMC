package de.atlascore.main;

import java.io.File;

import de.atlasmc.Atlas;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.tick.AtlasThreadTask;
import de.atlasmc.tick.AtlasThreadTaskFactory;

@RegistryValue(registry = "atlas:factory/atlas_thread_task", key="atlas-core:system_init/load_plugins")
public class CoreLoadPluginsTaskFactory implements AtlasThreadTaskFactory {

	@Override
	public AtlasThreadTask createTask(String name, Object... context) {
		return new AtlasThreadTask() {	
			@Override
			public boolean tick(int tick) throws Exception {
				Log log = Atlas.getLogger();
				log.info("Loading core modules...");
				File tmpCoremodulDir = new File(Atlas.getWorkdir(), "tmp/modules/");
				tmpCoremodulDir.mkdirs();
				File coremodulDir = new File(Atlas.getWorkdir(), "modules/");
				coremodulDir.mkdirs();
				PluginManager pm = Atlas.getPluginManager();
				pm.loadPlugins(coremodulDir);
				pm.loadPlugins(tmpCoremodulDir);
				return true;
			}
			
			@Override
			public String name() {
				return name;
			}
		};
	}

}
