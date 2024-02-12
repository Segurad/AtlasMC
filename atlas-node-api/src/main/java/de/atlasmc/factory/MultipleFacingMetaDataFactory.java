package de.atlasmc.factory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.MultipleFacing;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.configuration.Configuration;

/**
 * ClassMetaDataFactory implementation for {@link MultipleFacing} Blocks that applies default faces.
 */
public class MultipleFacingMetaDataFactory extends ClassMetaDataFactory {

	private final Set<BlockFace> faces;
	
	public <I extends ItemMeta, M extends MultipleFacing> MultipleFacingMetaDataFactory(Class<I> metaInterface, Class<? extends I> meta, Class<M> dataInterface, Class<? extends M> data, Set<BlockFace> faces) {
		super(metaInterface, meta, dataInterface, data);
		if (faces == null)
			throw new IllegalArgumentException("Faces can not be null!");
		this.faces = faces;
	}
	
	public MultipleFacingMetaDataFactory(Configuration cfg) throws ClassNotFoundException {
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
