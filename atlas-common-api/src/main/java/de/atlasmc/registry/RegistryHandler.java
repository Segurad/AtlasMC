package de.atlasmc.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.NotNull;

public interface RegistryHandler {

	<T extends Registry<?>> T getRegistry(CharSequence key);

	<T> T getDefault(CharSequence key);
	
	<T> T getDefault(Class<?> clazz);

	<T extends Registry<?>> T createRegistry(NamespacedKey key, Class<?> clazz, Target target);
	
	<T extends Registry<?>> T createRegistry(Class<?> clazz);
	
	<T extends Registry<?>> T getRegistry(Class<?> clazz);

	@NotNull
	Registry<Registry<?>> getRegistries();

	boolean registerRegistry(Registry<?> registry);
	
	boolean removePluginEntries(PluginHandle plugin);

	NamespacedKey getRegistryKey(Class<?> clazz);

}
