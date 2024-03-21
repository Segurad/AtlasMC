package de.atlasmc.block.data;

import de.atlasmc.Material;

/**
 * {@link ItemMetaFactory} implementation that clones a existing {@link BlockData}
 */
public class CloneBlockDataFactory implements BlockDataFactory {
	
	private final Class<?> dataInterface;
	private final BlockData data;
	
	public <B extends BlockData> CloneBlockDataFactory(Class<B> dataInterface, B data) {
		if (dataInterface != null && !dataInterface.isInstance(data))
			throw new IllegalArgumentException("DataInterface is not assignable from Data!");
		this.dataInterface = dataInterface;
		this.data = data;
	}
	
	@Override
	public boolean isValidData(BlockData data) {
		if (data == null || dataInterface == null) 
			return false;
		return dataInterface.isInstance(data); 
	}

	@Override
	public BlockData createData(Material material) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) 
			throw new IllegalArgumentException("Material is not a Block!");
		if (data == null) 
			return null;
		if (data.getMaterial() != material)
			throw new IllegalArgumentException("Material does not match BlockData!");
		return data.clone();
	}

}
