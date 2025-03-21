package de.atlasmc.block.data;

import de.atlasmc.block.BlockType;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;

/**
 * Class based {@link ItemMetaFactory} for {@link Ageable} BlockData
 */
public class LevelledClassBlockDataFactory extends ClassBlockDataFactory {

	private final int maxlevel;
	
	public <L extends Levelled> LevelledClassBlockDataFactory(Class<L> dataInterface, Class<? extends L> data, int maxlevel) {
		super(dataInterface, data, BlockType.class, int.class);
		this.maxlevel = maxlevel;
	}
	
	public LevelledClassBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
		super(cfg, BlockType.class, int.class);
		maxlevel = cfg.getInt("maxlevel");
	}
	
	@Override
	public BlockData createData(BlockType type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		return create(type, maxlevel);
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		super.toConfiguration(configuration);
		configuration.set("maxlevel", maxlevel);
		return configuration;
	}

}
