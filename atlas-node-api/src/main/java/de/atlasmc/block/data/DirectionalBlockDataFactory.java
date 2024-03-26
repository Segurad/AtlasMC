package de.atlasmc.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.util.configuration.Configuration;

public class DirectionalBlockDataFactory extends ClassBlockDataFactory {

	private final BlockFace direction;
	
	public <D extends Directional> DirectionalBlockDataFactory(Class<D> dataInterface, Class<? extends D> data, BlockFace direction) {
		super(dataInterface, data);
		if (direction == null)
			throw new IllegalArgumentException("Direction can not be null!");
		this.direction = direction;
	}
	
	public DirectionalBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
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
