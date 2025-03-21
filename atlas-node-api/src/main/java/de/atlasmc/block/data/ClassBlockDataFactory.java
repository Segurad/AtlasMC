package de.atlasmc.block.data;

import de.atlasmc.block.BlockType;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.factory.BaseClassFactory;

/**
 * Class based {@link ItemMetaFactory} for Materials
 */
public class ClassBlockDataFactory extends BaseClassFactory<BlockData> implements BlockDataFactory, ConfigurationSerializeable {
	
	protected final Class<? extends BlockData> dataInterface;
	
	/**
	 * 
	 * @param dataInterface
	 * @param data class must have a constructor ({@link BlockType})
	 */
	public <B extends BlockData> ClassBlockDataFactory(Class<B> dataInterface, Class<? extends B> data) {
		this(dataInterface, data, BlockType.class);
	}
	
	/**
	 * 
	 * @param dataInterface
	 * @param data class must have a constructor ({@link BlockType})
	 */
	protected <B extends BlockData> ClassBlockDataFactory(Class<B> dataInterface, Class<? extends B> data, Class<?>... parameterTypes) {
		super(data);
		if (dataInterface == null)
			throw new IllegalArgumentException("DataInterface can not be null!");
		if (!dataInterface.isAssignableFrom(data))
			throw new IllegalArgumentException("DataInterface is not assignable from Data!");
		this.dataInterface = dataInterface;
	}
	
	protected ClassBlockDataFactory(ConfigurationSection cfg, Class<?>... parameterTypes) throws ClassNotFoundException {
		this(getClass(cfg.getString("dataInterface")), getClass(cfg.getString("data")), parameterTypes);
	}
	
	public ClassBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
		this(cfg, BlockType.class);
	}

	@Override
	public boolean isValidData(BlockData data) {
		if (data == null || dataInterface == null) 
			return false;
		return dataInterface.isInstance(data); 
	}
	
	@Override
	public BlockData createData(BlockType material) {
		if (material == null) 
			throw new IllegalArgumentException("Type can not be null!");
		return super.create(material);
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("dataInterface", dataInterface.getName());
		configuration.set("data", clazz.getName());
		return configuration;
	}
	
}
