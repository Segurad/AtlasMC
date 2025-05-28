package de.atlasmc.block.data;

import de.atlasmc.block.BlockType;
import de.atlasmc.util.configuration.ConfigurationSection;

public class WaterloggedBlockDataFactory extends ClassBlockDataFactory {

	private final boolean waterlogged;
	
	public <W extends Waterlogged> WaterloggedBlockDataFactory(Class<W> dataInterface, Class<? extends W> data, boolean waterlogged) {
		super(dataInterface, data);
		this.waterlogged = waterlogged;
	}
	
	public WaterloggedBlockDataFactory(ConfigurationSection cfg) throws ClassNotFoundException {
		super(cfg);
		waterlogged = cfg.getBoolean("waterlogged");
	}
	
	@Override
	public BlockData createData(BlockType type) {
		Waterlogged data = (Waterlogged) super.createData(type);
		data.setWaterlogged(waterlogged);
		return data;
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("waterlogged", waterlogged);
		return super.toConfiguration(configuration);
	}

}
