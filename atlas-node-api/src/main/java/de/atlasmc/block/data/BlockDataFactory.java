package de.atlasmc.block.data;

import de.atlasmc.block.BlockType;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.factory.Factory;

/**
 * Factory for creation of {@link BlockData}
 */
@RegistryHolder(key="atlas:factory/block_data_factory")
public interface BlockDataFactory extends Factory {
	
	boolean isValidData(BlockData data);
	
	/**
	 * 
	 * @param type
	 * @return a new BlockData
	 */
	BlockData createData(BlockType type);

}
