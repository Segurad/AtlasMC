package de.atlasmc.block.data;

import de.atlasmc.block.BlockType;
import de.atlasmc.util.configuration.ConfigurationSection;

public class LightableBlockDataFactory extends ClassBlockDataFactory {

	private final boolean lit;
	
	public <L extends Lightable> LightableBlockDataFactory(Class<L> dataInterface, Class<? extends L> data, boolean lit) {
		super(dataInterface, data);
		this.lit = lit;
	}
	
	public LightableBlockDataFactory(ConfigurationSection cfg) throws ClassNotFoundException {
		super(cfg);
		lit = cfg.getBoolean("lit");
	}
	
	@Override
	public BlockData createData(BlockType type) {
		Lightable data = (Lightable) super.createData(type);
		data.setLit(lit);
		return data;
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		super.toConfiguration(configuration);
		configuration.set("lit", lit);
		return configuration;
	}

}
