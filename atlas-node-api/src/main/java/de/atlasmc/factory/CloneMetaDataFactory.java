package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.ItemMeta;

/**
 * {@link MetaDataFactory} implementation that clones a existing {@link ItemMeta} or {@link BlockData}
 */
public class CloneMetaDataFactory implements MetaDataFactory {
	
	private final Class<?> metaInterface, dataInterface;
	private final ItemMeta meta;
	private final BlockData data;
	
	public <I extends ItemMeta, B extends BlockData> CloneMetaDataFactory(Class<I> metaInterface, I meta, Class<B> dataInterface, B data) {
		if (metaInterface != null && !metaInterface.isInstance(meta)) 
			throw new IllegalArgumentException("MetaInterface is not assignable from Meta!");
		if (dataInterface != null && !dataInterface.isInstance(data))
			throw new IllegalArgumentException("DataInterface is not assignable from Data!");
		this.metaInterface = metaInterface;
		this.dataInterface = dataInterface;
		this.meta = meta;
		this.data = data;
	}

	@Override
	public boolean isValidMeta(ItemMeta meta) {
		if (meta == null || metaInterface == null) 
			return false;
		return metaInterface.isInstance(meta);
	}
	
	@Override
	public boolean isValidData(BlockData data) {
		if (data == null || dataInterface == null) 
			return false;
		return dataInterface.isInstance(data); 
	}

	@Override
	public ItemMeta createMeta(Material material) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		if (!material.isItem()) 
			throw new IllegalArgumentException("Material is not a Item!");
		if (meta == null) 
			return null;
		return meta.clone();
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
