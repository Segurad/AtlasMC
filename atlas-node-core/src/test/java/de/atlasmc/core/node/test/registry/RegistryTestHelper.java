package de.atlasmc.core.node.test.registry;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.core.node.test.plugin.MockPlugin;
import de.atlasmc.core.registry.CoreRegistryHandler;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.registry.Registries;

public class RegistryTestHelper {
	
	private static final Set<String> LOADED_FILES = ConcurrentHashMap.newKeySet();
	
	public synchronized static void initRegistries() {
		if (Registries.getHandler() == null) {
			Registries.init(new CoreRegistryHandler());
		}
	}
	
	public synchronized static final void loadBulk(Class<?> registryHolder, String path) throws IOException {
		if (LOADED_FILES.contains(path))
			return;
		initRegistries();
		Registries.createRegistry(EntityType.class);
		Registries.loadBulkRegistryEntries(MockPlugin.PLUGIN, path);
	}
	
	public synchronized static final void loadBulk(String path) throws IOException {
		if (LOADED_FILES.contains(path))
			return;
		initRegistries();
		Registries.loadBulkRegistryEntries(MockPlugin.PLUGIN, path);
	}

}
