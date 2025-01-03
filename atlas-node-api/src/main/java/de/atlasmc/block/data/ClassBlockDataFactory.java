package de.atlasmc.block.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Material;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.configuration.InvalidConfigurationException;
import de.atlasmc.util.factory.FactoryException;

/**
 * Class based {@link ItemMetaFactory} for Materials
 */
public class ClassBlockDataFactory implements BlockDataFactory, ConfigurationSerializeable {
	
	private final Constructor<? extends BlockData> constructor;
	protected final Class<? extends BlockData> dataInterface;
	protected final Class<? extends BlockData> data;
	
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
		try {
			this.constructor = data.getConstructor(Material.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new FactoryException("Error while fetching constructor for class: " + data.getName(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ClassBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
		if (cfg.contains("dataInterface")) {
			String val = cfg.getString("dataInterface");
			this.dataInterface = val == null ? BlockData.class : (Class<? extends BlockData>) Class.forName(val);
		} else {
			throw new InvalidConfigurationException("Data interface not defined!");
		}
		if (cfg.contains("data")) {
			String val = cfg.getString("data");
			this.data = (Class<? extends BlockData>) Class.forName(val);
		} else {
			throw new InvalidConfigurationException("Data ist not defined!");
		}
		try {
			this.constructor = data.getConstructor(Material.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new FactoryException("Error while fetching constructor for class: " + data.getName(), e);
		}
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
			return constructor.newInstance(material);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FactoryException("Error while creating BlockData!", e);
		}
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		if (dataInterface != null)
			configuration.set("dataInterface", dataInterface.getName());
		if (data != null)
			configuration.set("data", data.getName());
		return configuration;
	}
	
}
