package de.atlasmc.node.block.data;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.factory.ClassFactory;

/**
 * Class based {@link ItemMetaFactory} for Materials
 */
public class ClassBlockDataFactory extends ClassFactory<BlockData> implements BlockDataFactory, ConfigurationSerializable {
	
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
		super(data, parameterTypes);
		if (dataInterface == null)
			throw new IllegalArgumentException("DataInterface can not be null!");
		if (!dataInterface.isAssignableFrom(data))
			throw new IllegalArgumentException("DataInterface (" + dataInterface + ") is not assignable from Data (" + data + ")!");
		this.dataInterface = dataInterface;
	}
	
	protected ClassBlockDataFactory(ConfigurationSection cfg, Class<?>... parameterTypes) {
		this(getClass(cfg.getString("dataInterface")), getClass(cfg.getString("data")), parameterTypes);
	}
	
	public ClassBlockDataFactory(ConfigurationSection cfg) {
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
