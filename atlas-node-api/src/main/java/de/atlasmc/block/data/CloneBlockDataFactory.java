package de.atlasmc.block.data;

import de.atlasmc.block.BlockType;

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
	public BlockData createData(BlockType type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		if (data == null) 
			return null;
		if (data.getType() != type)
			throw new IllegalArgumentException("Type does not match BlockData!");
		return data.clone();
	}

}
