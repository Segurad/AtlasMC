package de.atlasmc.block.data;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.util.FactoryException;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSerializeable;

/**
 * Class based {@link ItemMetaFactory} for Materials
 */
public class ClassBlockDataFactory implements BlockDataFactory, ConfigurationSerializeable {
	
	protected final Class<? extends BlockData> dataInterface, data;
	
	/**
	 * 
	 * @param dataInterface
	 * @param data class must have a constructor ({@link Material})
	 */
	public <B extends BlockData> ClassBlockDataFactory(Class<B> dataInterface, Class<? extends B> data) {
		if (dataInterface != null && !dataInterface.isAssignableFrom(data))
			throw new IllegalArgumentException("DataInterface is not assignable from Data!");
		this.dataInterface = dataInterface;
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	public ClassBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
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
	public boolean isValidData(BlockData data) {
		if (data == null || dataInterface == null) 
			return false;
		return dataInterface.isInstance(data); 
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
