package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Directional;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.configuration.Configuration;

public class DirectionalMetaDataFactory extends ClassMetaDataFactory {

	private final BlockFace direction;
	
	public <I extends ItemMeta, D extends Directional> DirectionalMetaDataFactory(Class<I> metaInterface, Class<? extends I> meta, Class<D> dataInterface, Class<? extends D> data, BlockFace direction) {
		super(metaInterface, meta, dataInterface, data);
		if (direction == null)
			throw new IllegalArgumentException("Direction can not be null!");
		this.direction = direction;
	}
	
	public DirectionalMetaDataFactory(Configuration cfg) throws ClassNotFoundException {
		super(cfg);
		direction = BlockFace.valueOf(cfg.getString("direction"));
	}
	
	@Override
	public BlockData createData(Material material) {
		Directional data = (Directional) super.createData(material);
		data.setFacing(direction);
		return data;
	}

}
