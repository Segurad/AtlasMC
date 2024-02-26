package de.atlasmc.registry;

public interface ClassRegistry<T> extends Registry<Class<T>> {
	
	Class<T> getType();

}
