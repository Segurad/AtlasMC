package de.atlasmc.util;

import de.atlasmc.registry.RegistryHolder;

/**
 * Factory for creating new builder instances
 */
@RegistryHolder(key="atlas:factory/builder")
public interface BuilderFactory {
	
	Builder<?> createBuilder(Object... context);

}
