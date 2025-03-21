package de.atlasmc.registry;

public interface ProtocolRegistry<T extends ProtocolRegistryValue> extends Registry<T> {
	
	T getByID(int id);
	
	RegistryEntry<T> getEntryByID(int id);
	
}
