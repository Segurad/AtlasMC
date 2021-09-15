package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlascore.inventory.meta.CoreBlockDataMeta;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.BlockDataMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.Validate;

/**
 * Class based {@link MetaDataFactory} for Materials
 */
public class ClassMetaDataFactory extends MetaDataFactory {
	
	protected final Class<? extends ItemMeta> metaInterface, meta;
	protected final Class<? extends BlockData> dataInterface, data;
	
	public ClassMetaDataFactory(Class<? extends BlockData> dataInterface, Class<? extends BlockData> data) {
		this(BlockDataMeta.class, CoreBlockDataMeta.class, dataInterface, data);
	}
	
	/**
	 * 
	 * @param metaInterface
	 * @param meta class must have a constructor ({@link Material})
	 * @param dataInterface
	 * @param data class must have a constructor ({@link Material})
	 */
	public ClassMetaDataFactory(Class<? extends ItemMeta> metaInterface, Class<? extends ItemMeta> meta, Class<? extends BlockData> dataInterface, Class<? extends BlockData> data) {
		if (metaInterface != null) Validate.isTrue(metaInterface.isAssignableFrom(meta), "MetaInterface is not assignable from Meta!");
		if (dataInterface != null) Validate.isTrue(dataInterface.isAssignableFrom(data), "DataInterface is not assignable from Data!");
		this.dataInterface = dataInterface;
		this.metaInterface = metaInterface;
		this.data = data;
		this.meta = meta;
	}
	
	public boolean isValidMeta(ItemMeta meta) {
		if (meta == null) return false;
		return metaInterface.isInstance(meta);
	}
	
	public boolean isValidData(BlockData data) {
		if (data == null) return false;
		return dataInterface.isInstance(data); 
	}
	
	public ItemMeta createMeta(Material material, boolean preConfig) {
		Validate.notNull(material, "Material can not be null!");
		Validate.isTrue(material.isItem(), "Material is not a Item!");
		if (preConfig) {
			ItemMeta im = getMetaPreConfig(material);
			if (im != null) return im.clone();
		}
		if (meta == null) return null;
		try {
			return meta.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public BlockData createData(Material material, boolean preConfig) {
		Validate.notNull(material, "Material can not be null!");
		Validate.isTrue(material.isBlock(), "Material is not a Block!");
		if (preConfig) {
			BlockData bd = getDataPreConfig(material);
			if (bd != null) return bd.clone();
		}
		if (data == null) return null;
		try {
			return data.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
