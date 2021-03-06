package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlascore.inventory.meta.CoreBlockDataMeta;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.BlockDataMeta;
import de.atlasmc.inventory.meta.ItemMeta;

/**
 * Class based {@link MetaDataFactory} for Materials
 */
public class ClassMetaDataFactory implements MetaDataFactory {
	
	protected final Class<? extends ItemMeta> metaInterface, meta;
	protected final Class<? extends BlockData> dataInterface, data;
	
	public <B extends BlockData> ClassMetaDataFactory(Class<B> dataInterface, Class<? extends B> data) {
		this(BlockDataMeta.class, CoreBlockDataMeta.class, dataInterface, data);
	}
	
	/**
	 * 
	 * @param metaInterface
	 * @param meta class must have a constructor ({@link Material})
	 * @param dataInterface
	 * @param data class must have a constructor ({@link Material})
	 */
	public <I extends ItemMeta, B extends BlockData> ClassMetaDataFactory(Class<I> metaInterface, Class<? extends I> meta, Class<B> dataInterface, Class<? extends B> data) {
		if (metaInterface != null && !metaInterface.isAssignableFrom(meta)) 
			throw new IllegalArgumentException("MetaInterface is not assignable from Meta!");
		if (dataInterface != null && !dataInterface.isAssignableFrom(data))
			throw new IllegalArgumentException("DataInterface is not assignable from Data!");
		this.dataInterface = dataInterface;
		this.metaInterface = metaInterface;
		this.data = data;
		this.meta = meta;
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
		try {
			return meta.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public BlockData createData(Material material) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) 
			throw new IllegalArgumentException("Material is not a Block!");
		if (data == null) 
			return null;
		try {
			return data.getConstructor(Material.class).newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
