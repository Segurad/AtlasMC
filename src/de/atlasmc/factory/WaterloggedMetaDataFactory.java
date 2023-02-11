package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Waterlogged;

public class WaterloggedMetaDataFactory extends ClassMetaDataFactory {

	private final boolean waterlogged;
	
	public <W extends Waterlogged> WaterloggedMetaDataFactory(Class<W> dataInterface, Class<? extends W> data, boolean waterlogged) {
		super(dataInterface, data);
		this.waterlogged = waterlogged;
	}
	
	@Override
	public BlockData createData(Material material) {
		Waterlogged data = (Waterlogged) super.createData(material);
		data.setWaterlogged(waterlogged);
		return data;
	}

}
