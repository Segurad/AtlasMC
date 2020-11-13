package de.atlasmc.factory.metadata;

import java.util.HashMap;

import de.atlascore.inventory.meta.CoreItemMeta;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.NBT;

public abstract class MetaDataFactory {
	
	public static MetaDataFactory DEFAULT = new ClassMetaDataFactory(ItemMeta.class, CoreItemMeta.class, BlockData.class, null);
	private static final HashMap<Material, ItemMeta> metaPreConfig = new HashMap<>();
	private static final HashMap<Material, BlockData> dataPreConfig = new HashMap<>();
	
	public abstract boolean isValidMeta(ItemMeta meta);
	
	public abstract boolean isValidData(BlockData data);
	
	public ItemMeta createMeta(Material material, NBT nbt) {
		return createMeta(material, true, nbt);
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
	public abstract ItemMeta createMeta(Material material, boolean preConfig, NBT nbt);
	
	/**
	 * 
	 * @param material
	 * @param preConfig if true it will check for a DataPreConfig
	 * @return a new BlockData
	 */
	public abstract BlockData createData(Material material, boolean preConfig);
	
	public ItemMeta getMetaPreConfig(Material material) {
		return metaPreConfig.get(material);
	}
	
	public BlockData getDataPreConfig(Material material) {
		return dataPreConfig.get(material);
	}
	
	public ItemMeta setMetaPreConfig(Material material, ItemMeta meta) {
		Validate.notNull(material, "Material can not be null!");
		if (!material.isItem()) throw new IllegalArgumentException("Material is not a Item!");
		if (meta == null) return metaPreConfig.remove(material);
		if (!material.isValidMeta(meta))
			throw new IllegalArgumentException("ItemMeta is not valid for Material: " + material.name());
		return metaPreConfig.put(material, meta);
	}
	
	public BlockData setDataPreConfig(Material material, BlockData data) {
		Validate.notNull(material, "Material can not be null!");
		if (!material.isBlock()) throw new IllegalArgumentException("Material is not a Block!");
		if (data == null) return dataPreConfig.remove(material);
		if (!material.isValidData(data))
			throw new IllegalArgumentException("BlockData is not valid for Material: " + material.name());
		return dataPreConfig.put(material, data);
	}
}
