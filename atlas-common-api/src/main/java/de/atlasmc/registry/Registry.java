package de.atlasmc.registry;

import java.util.Collection;
import java.util.Set;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface Registry<T> extends Namespaced {
	
	T getDefault();
	
	T setDefault(T defaultEntry);
	
	T get(CharSequence key);
	
	T getOrDefault(CharSequence key);
	
	T getOrDefault(CharSequence key, T defaultValue);
	
	/**
	 * Registers a new entry for this registry.
	 * Returns the previous entry with the given key if present.
	 * @param plugin to associate with
	 * @param key of the entry
	 * @param value of the entry
	 * @return previous or null
	 */
	@Nullable
	RegistryEntry<T> register(PluginHandle plugin, CharSequence key, T value);
	
	boolean containsKey(CharSequence key);
	
	@Nullable
	RegistryEntry<T> getEntry(CharSequence key);

	@Nullable
	RegistryEntry<T> remove(CharSequence key);
	
	int size();
	
	@NotNull
	Target getTarget();
	
	@NotNull
	Class<?> getType();
	
	@NotNull
	Collection<RegistryEntry<T>> entries();
	
	@NotNull
	Collection<T> values();
	
	@NotNull
	Set<String> keySet();
	
	@NotNull
	Set<PluginHandle> getHandles();
	
	@NotNull
	Collection<RegistryEntry<T>> getPluginEntries(PluginHandle plugin);
	
	boolean removePluginEntries(PluginHandle plugin);
	
}
