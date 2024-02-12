package de.atlasmc.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.NBTHolder;

public interface Registry<T> extends Namespaced, NBTHolder {
	
	T getDefault();
	
	T get(NamespacedKey key);
	
	boolean register(NamespacedKey key, T value);
	
	boolean register(String key, T value);

	T get(String key);
	
	Class<T> getType();

}
