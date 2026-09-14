package de.atlasmc.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface RegistryHandler {

	@Nullable
	<T extends Registry<?>> T getRegistry(CharSequence key);

	@Nullable
	<T> T getDefault(CharSequence key);
	
	@Nullable
	<T> T getDefault(Class<?> clazz);

	@NotNull
	<T extends Registry<?>> T createRegistry(NamespacedKey key, Class<?> clazz, Target target);
	
	@NotNull
	<T extends Registry<?>> T createRegistry(Class<?> clazz);
	
	@Nullable
	<T extends Registry<?>> T getRegistry(Class<?> clazz);

	@NotNull
	Registry<Registry<?>> getRegistries();

	boolean registerRegistry(Registry<?> registry);
	
	boolean removePluginEntries(PluginHandle plugin);

	@NotNull
	NamespacedKey getRegistryKey(Class<?> clazz);

}
