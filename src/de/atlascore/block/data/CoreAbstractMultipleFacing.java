package de.atlascore.block.data;

import java.util.HashSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.MultipleFacing;
import de.atlasmc.util.Validate;

public abstract class CoreAbstractMultipleFacing extends CoreBlockData implements MultipleFacing {

	private Set<BlockFace> faces;
	
	public CoreAbstractMultipleFacing(Material material) {
		this(material, 6);
	}
	
	protected CoreAbstractMultipleFacing(Material material, int faces) {
		super(material);
		this.faces = new HashSet<BlockFace>(faces);
	}

	@Override
	public abstract Set<BlockFace> getAllowedFaces();

	@Override
	public Set<BlockFace> getFaces() {
		return faces;
	}

	@Override
	public boolean hasFace(BlockFace face) {
		Validate.notNull(face, "BlockFace can not be null!");
		return faces.contains(face);
	}

	@Override
	public void setFace(BlockFace face, boolean has) {
		Validate.notNull(face, "BlockFace can not be null!");
		Validate.isTrue(isValid(face), "BlockFace is not valid: " + face.name());
		if (has) {
			faces.add(face);
		} else faces.remove(face);
	}
	
	@Override
	public abstract int getStateID();
	
	@Override
	public abstract boolean isValid(BlockFace face);

}
