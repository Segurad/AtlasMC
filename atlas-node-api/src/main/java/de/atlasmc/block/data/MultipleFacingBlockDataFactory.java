package de.atlasmc.block.data;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.util.configuration.Configuration;

/**
 * ClassMetaDataFactory implementation for {@link MultipleFacing} Blocks that applies default faces.
 */
public class MultipleFacingBlockDataFactory extends ClassBlockDataFactory {

	private final Set<BlockFace> faces;
	
	public <M extends MultipleFacing> MultipleFacingBlockDataFactory(Class<M> dataInterface, Class<? extends M> data, Set<BlockFace> faces) {
		super(dataInterface, data);
		if (faces == null)
			throw new IllegalArgumentException("Faces can not be null!");
		this.faces = faces;
	}
	
	public MultipleFacingBlockDataFactory(Configuration cfg) throws ClassNotFoundException {
		super(cfg);
		List<String> rawFaces = cfg.getStringList("faces");
		List<BlockFace> faces = new ArrayList<>(rawFaces.size());
		for (String s : rawFaces)
			faces.add(BlockFace.getByName(s));
		this.faces = EnumSet.copyOf(faces);
	}
	
	@Override
	public BlockData createData(Material material) {
		MultipleFacing data = (MultipleFacing) super.createData(material);
		if (faces.isEmpty())
			return data;
		for (BlockFace face : faces)
			data.setFace(face, true);
		return data;
	}

}
