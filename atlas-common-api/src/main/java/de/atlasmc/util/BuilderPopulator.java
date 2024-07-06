package de.atlasmc.util;

import de.atlasmc.registry.RegistryHolder;

/**
 * Populator are used to populate builders with values
 * @param <T>
 */
@RegistryHolder(key="atlas:builder/populator")
public interface BuilderPopulator<T> {
	
	void populate(Builder<T> builder);

}
