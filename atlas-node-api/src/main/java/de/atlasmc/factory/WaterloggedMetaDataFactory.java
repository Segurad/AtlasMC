package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Waterlogged;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.configuration.Configuration;

public class WaterloggedMetaDataFactory extends ClassMetaDataFactory {

	private final boolean waterlogged;
	
	public <I extends ItemMeta, W extends Waterlogged> WaterloggedMetaDataFactory(Class<I> metaInterface, Class<? extends I> meta, Class<W> dataInterface, Class<? extends W> data, boolean waterlogged) {
		super(metaInterface, meta, dataInterface, data);
		this.waterlogged = waterlogged;
	}
	
	public WaterloggedMetaDataFactory(Configuration cfg) throws ClassNotFoundException {
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
