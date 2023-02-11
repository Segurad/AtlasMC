package de.atlasmc.factory;

import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.MultipleFacing;

public class MultipleFacingMetaDataFactory extends ClassMetaDataFactory {

	private final Set<BlockFace> faces;
	
	public <M extends MultipleFacing> MultipleFacingMetaDataFactory(Class<M> dataInterface, Class<? extends M> data, Set<BlockFace> faces) {
		super(dataInterface, data);
		this.faces = faces;
	}
	
	@Override
	public BlockData createData(Material material) {
		MultipleFacing data = (MultipleFacing) super.createData(material);
		for (BlockFace face : faces)
			data.setFace(face, true);
		return data;
	}

}
