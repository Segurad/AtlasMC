package de.atlasmc.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

/**
 * {@link ItemMetaFactory} implementation that clones a existing {@link ItemMeta}
 */
public class CloneItemMetaFactory implements ItemMetaFactory {
	
	private final Class<?> metaInterface;
	private final ItemMeta meta;
	
	public <I extends ItemMeta, B extends BlockData> CloneItemMetaFactory(Class<I> metaInterface, I meta) {
		if (metaInterface != null && !metaInterface.isInstance(meta)) 
			throw new IllegalArgumentException("MetaInterface is not assignable from Meta!");
		this.metaInterface = metaInterface;
		this.meta = meta;
	}

	@Override
	public boolean isValidMeta(ItemMeta meta) {
		if (meta == null || metaInterface == null) 
			return false;
		return metaInterface.isInstance(meta);
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

}
