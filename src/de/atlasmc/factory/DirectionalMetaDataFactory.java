package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Directional;

public class DirectionalMetaDataFactory extends ClassMetaDataFactory {

	private final BlockFace direction;
	
	public <D extends Directional> DirectionalMetaDataFactory(Class<D> dataInterface, Class<? extends D> data, BlockFace direction) {
		super(dataInterface, data);
		if (direction == null)
			throw new IllegalArgumentException("Direction can not be null!");
		this.direction = direction;
	}
	
	@Override
	public BlockData createData(Material material) {
		Directional data = (Directional) super.createData(material);
		data.setFacing(direction);
		return data;
	}

}
