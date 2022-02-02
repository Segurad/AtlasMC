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
	
	protected int hash;
	
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

	@Override
	public int hashCode() {
		if (hash != 0)
			return hash;
		final int prime = 31;
		hash = 1;
		hash = prime * hash + ((data == null) ? 0 : data.hashCode());
		hash = prime * hash + ((dataInterface == null) ? 0 : dataInterface.hashCode());
		hash = prime * hash + ((meta == null) ? 0 : meta.hashCode());
		hash = prime * hash + ((metaInterface == null) ? 0 : metaInterface.hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassMetaDataFactory other = (ClassMetaDataFactory) obj;
		if (data != other.data || meta != other.meta || dataInterface != other.dataInterface || metaInterface != other.metaInterface)
			return false;
		return true;
	}
	
}
