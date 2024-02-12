package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.configuration.Configuration;

/**
 * Class based {@link MetaDataFactory} for Materials
 */
public class ClassMetaDataFactory implements MetaDataFactory {
	
	protected final Class<? extends ItemMeta> metaInterface, meta;
	protected final Class<? extends BlockData> dataInterface, data;
	
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
	
	@SuppressWarnings("unchecked")
	public ClassMetaDataFactory(Configuration cfg) throws ClassNotFoundException {
		if (cfg.contains("metaInterface")) {
			String val = cfg.getString("metaInterface");
			this.metaInterface = val == null ? ItemMeta.class : (Class<? extends ItemMeta>) Class.forName(val);
		} else this.metaInterface = null;
		if (cfg.contains("meta")) {
			String val = cfg.getString("meta");
			this.meta = (Class<? extends ItemMeta>) Class.forName(val);
		} else this.meta = null;
		if (cfg.contains("dataInterface")) {
			String val = cfg.getString("dataInterface");
			this.dataInterface = val == null ? BlockData.class : (Class<? extends BlockData>) Class.forName(val);
		} else this.dataInterface = null;
		if (cfg.contains("data")) {
			String val = cfg.getString("data");
			this.data = (Class<? extends BlockData>) Class.forName(val);
		} else this.data = null;
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
			throw new FactoryException("Error while creating meta", e);
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
			throw new FactoryException("Error while creating data", e);
		}
	}
	
}
