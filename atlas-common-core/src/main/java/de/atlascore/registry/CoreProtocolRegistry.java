package de.atlascore.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryEntry;
import de.atlasmc.registry.RegistryHolder.Target;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class CoreProtocolRegistry<T extends ProtocolRegistryValue> extends CoreInstanceRegistry<T> implements ProtocolRegistry<T> {
	
	private final Int2ObjectMap<RegistryEntry<T>> ENTRY_BY_ID;
	
	public CoreProtocolRegistry(NamespacedKey key, Class<?> type) {
		super(key, type);
		ENTRY_BY_ID = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
	}
	
	@Override
	public RegistryEntry<T> register(PluginHandle plugin, NamespacedKey key, T value) {
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		if (value.getID() == -1)
			throw new IllegalArgumentException("Values has invalid id: " + value.getID());
		return super.register(plugin, key, value);
	}
	
	@Override
	public RegistryEntry<T> register(PluginHandle plugin, String key, T value) {
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		if (value.getID() == -1)
			throw new IllegalArgumentException("Values has invalid id: " + value.getID());
		return super.register(plugin, key, value);
	}
	
	@Override
	protected void onAdd(RegistryEntry<T> entry) {
		ENTRY_BY_ID.remove(entry.value().getID(), entry);
	}
	
	@Override
	protected void onRemove(RegistryEntry<T> entry) {
		ENTRY_BY_ID.remove(entry.value().getID(), entry);
	}
	
	@Override
	public Target getTarget() {
		return Target.PROTOCOL;
	}

	@Override
	public T getByID(int id) {
		RegistryEntry<T> entry = ENTRY_BY_ID.get(id);
		return entry == null ? null : entry.value();
	}
	
	@Override
	public RegistryEntry<T> getEntryByID(int id) {
		return ENTRY_BY_ID.get(id);
	}

}
