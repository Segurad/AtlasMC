package de.atlasmc.registry;

public interface InstanceRegistry<T> extends Registry<T> {
	
	Class<T> getType();

}
