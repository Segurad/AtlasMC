package de.atlasmc.registry;

import de.atlasmc.util.annotation.Nullable;

public interface ProtocolRegistry<T extends ProtocolRegistryValue> extends Registry<T> {
	
	@Nullable
	T getByID(int id);
	
	@Nullable
	RegistryEntry<T> getEntryByID(int id);
	
}
