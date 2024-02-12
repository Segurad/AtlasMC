package de.atlascore.registry;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRegistry<T> implements Registry<T> {
	
	private final Map<String, T> entries;
	private final NamespacedKey key;
	private final Class<T> type;
	
	public CoreRegistry(NamespacedKey key, Class<T> type) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		this.key = key;
		this.type = type;
		this.entries = new ConcurrentHashMap<>();
	}

	@Override
	@NotNull
	public NamespacedKey getNamespacedKey() {
		return key;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T getDefault() {
		return entries.get(Registries.DEFAULT_REGISTRY_KEY);
	}

	@Override
	public T get(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		return entries.get(key.toString());
	}

	@Override
	public boolean register(NamespacedKey key, T value) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		if (!type.isInstance(value))
			throw new IllegalArgumentException("Value (" + value.getClass().getName() + ") is not a instance of the registries type: " + type.getName());
		entries.put(key.toString(), value);
		return true;
	}

	@Override
	public boolean register(String key, T value) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (!NamespacedKey.NAMESPACED_KEY_PATTERN.matcher(key).matches())
			throw new IllegalArgumentException("Key must be a valid namespaced key: " + key);
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		if (!type.isInstance(value))
			throw new IllegalArgumentException("Value (" + value.getClass().getName() + ") is not a instance of the registries type: " + type.getName());
		entries.put(key, value);
		return true;
	}

	@Override
	public T get(String key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		return entries.get(key);
	}

	@Override
	public Class<T> getType() {
		return type;
	}

}
