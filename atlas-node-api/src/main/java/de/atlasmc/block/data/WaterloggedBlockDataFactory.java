package de.atlasmc.block.data;

import de.atlasmc.Material;
import de.atlasmc.util.configuration.Configuration;

public class WaterloggedBlockDataFactory extends ClassBlockDataFactory {

	private final boolean waterlogged;
	
	public <W extends Waterlogged> WaterloggedBlockDataFactory(Class<W> dataInterface, Class<? extends W> data, boolean waterlogged) {
		super(dataInterface, data);
		this.waterlogged = waterlogged;
	}
	
	public WaterloggedBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
		super(cfg);
		waterlogged = cfg.getBoolean("waterlogged");
	}
	
	@Override
	public BlockData createData(Material material) {
		Waterlogged data = (Waterlogged) super.createData(material);
		data.setWaterlogged(waterlogged);
		return data;
	}

}
