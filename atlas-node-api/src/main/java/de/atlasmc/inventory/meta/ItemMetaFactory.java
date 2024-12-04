package de.atlasmc.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.Factory;

/**
 * Factory for creation of {@link ItemMeta}
 */
@RegistryHolder(key="atlas:factory/item_meta_factory")
public interface ItemMetaFactory extends Factory {
	
	boolean isValidMeta(ItemMeta meta);
	
	/**
	 * 
	 * @param material
	 * @return a new ItemMeta
	 */
	ItemMeta createMeta(Material material);
	
}
