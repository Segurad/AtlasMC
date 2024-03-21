package de.atlasmc.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.registry.RegistryHolder;

/**
 * Factory for creation of {@link ItemMeta}
 */
@RegistryHolder(key="atlas:factory/item_meta_factory")
public interface ItemMetaFactory {
	
	public abstract boolean isValidMeta(ItemMeta meta);
	
	/**
	 * 
	 * @param material
	 * @return a new ItemMeta
	 */
	public abstract ItemMeta createMeta(Material material);
	
}
