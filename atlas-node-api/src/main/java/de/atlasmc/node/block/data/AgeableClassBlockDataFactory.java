package de.atlasmc.node.block.data;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.util.configuration.ConfigurationSection;

/**
 * Class based {@link ItemMetaFactory} for {@link Ageable} BlockData
 */
public class AgeableClassBlockDataFactory extends ClassBlockDataFactory {

	private final int maxage;
	
	public <A extends Ageable> AgeableClassBlockDataFactory(Class<A> dataInterface, Class<? extends A> data, int maxage) {
		super(dataInterface, data, BlockType.class, int.class);
		this.maxage = maxage;
	}
	
	public AgeableClassBlockDataFactory(ConfigurationSection cfg) throws ClassNotFoundException {
		super(cfg, BlockType.class, int.class);
		maxage = cfg.getInt("maxage");
	}
	
	@Override
	public BlockData createData(BlockType type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		return create(type, maxage);
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		super.toConfiguration(configuration);
		configuration.set("maxage", maxage);
		return configuration;
	}

}
