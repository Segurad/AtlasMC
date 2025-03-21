package de.atlascore.test.registry;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.test.plugin.MockPlugin;
import de.atlasmc.registry.Registries;

public class RegistryTestHelper {
	
	private static final Set<String> LOADED_FILES = ConcurrentHashMap.newKeySet();
	
	public static final void loadBulk(String path) throws IOException {
		if (LOADED_FILES.contains(path))
			return;
		Registries.loadBulkRegistryEntries(MockPlugin.PLUGIN, path);
	}

}
