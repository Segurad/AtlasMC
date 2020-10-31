package de.atlasmc.factory.metadata;

import de.atlascore.inventory.meta.CoreItemMeta;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.ItemMeta;

public interface MetaDataFactory {
	
	public static MetaDataFactory DEFAULT = new ClassMetaDataFactory(ItemMeta.class, CoreItemMeta.class, BlockData.class, null);
	
	public boolean isValidMeta(ItemMeta meta);
	
	public boolean isValidData(BlockData data);
	
	public ItemMeta createMeta(Material material);
	
	public BlockData createData(Material material);
	
}
