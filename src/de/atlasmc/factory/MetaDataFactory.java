package de.atlasmc.factory;

import java.util.HashMap;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.inventory.meta.CoreBlockDataMeta;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.BlockDataMeta;

/**
 * Factory for creation of {@link ItemMeta} and {@link BlockData}
 */
public abstract class MetaDataFactory {
	
	public static MetaDataFactory DEFAULT = new ClassMetaDataFactory(BlockDataMeta.class, CoreBlockDataMeta.class, BlockData.class, CoreBlockData.class);
	private static final HashMap<Material, ItemMeta> metaPreConfig = new HashMap<>();
	private static final HashMap<Material, BlockData> dataPreConfig = new HashMap<>();
	
	public abstract boolean isValidMeta(ItemMeta meta);
	
	public abstract boolean isValidData(BlockData data);
	
	public ItemMeta createMeta(Material material) {
		return createMeta(material, true);
	}
	
	public BlockData createData(Material material) {
		return createData(material, true);
	}
	
	/**
	 * 
	 * @param material
	 * @param preConfig if true it will check for a MetaPreConfig
	 * @return a new ItemMeta
	 */
	public abstract ItemMeta createMeta(Material material, boolean preConfig);
	
	/**
	 * 
	 * @param material
	 * @param preConfig if true it will check for a DataPreConfig
	 * @return a new BlockData
	 */
	public abstract BlockData createData(Material material, boolean preConfig);
	
	public static ItemMeta getMetaPreConfig(Material material) {
		return metaPreConfig.get(material);
	}
	
	public static BlockData getDataPreConfig(Material material) {
		return dataPreConfig.get(material);
	}
	
	public static void setMetaPreConfig(Material material, ItemMeta meta) {
		if (material == null) throw new IllegalArgumentException("Material can not be null!");
		if (!material.isItem()) throw new IllegalArgumentException("Material is not a Item!");
		if (meta == null) metaPreConfig.remove(material);
		if (!material.isValidMeta(meta))
			throw new IllegalArgumentException("ItemMeta is not valid for Material: " + material.getName());
		metaPreConfig.put(material, meta);
	}
	
	public static void setDataPreConfig(Material material, BlockData data) {
		if (material == null) throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) throw new IllegalArgumentException("Material is not a Block!");
		if (data == null) dataPreConfig.remove(material);
		if (!material.isValidData(data))
			throw new IllegalArgumentException("BlockData is not valid for Material: " + material.getName());
		dataPreConfig.put(material, data);
	}
}
