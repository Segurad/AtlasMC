package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.registry.RegistryHolder;

/**
 * Factory for creation of {@link ItemMeta} and {@link BlockData}
 */
@RegistryHolder(key="atlas:factory/meta_data_factory")
public interface MetaDataFactory {
	
	public abstract boolean isValidMeta(ItemMeta meta);
	
	public abstract boolean isValidData(BlockData data);
	
	/**
	 * 
	 * @param material
	 * @return a new ItemMeta
	 */
	public abstract ItemMeta createMeta(Material material);
	
	/**
	 * 
	 * @param material
	 * @return a new BlockData
	 */
	public abstract BlockData createData(Material material);
	
}
