package de.atlasmc.block.data;

import de.atlasmc.Material;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.Factory;

/**
 * Factory for creation of {@link BlockData}
 */
@RegistryHolder(key="atlas:factory/block_data_factory")
public interface BlockDataFactory extends Factory {
	
	boolean isValidData(BlockData data);
	
	/**
	 * 
	 * @param material
	 * @return a new BlockData
	 */
	BlockData createData(Material material);

}
