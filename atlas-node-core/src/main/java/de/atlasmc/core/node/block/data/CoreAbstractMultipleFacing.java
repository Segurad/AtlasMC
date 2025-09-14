package de.atlasmc.core.node.block.data;

import java.util.HashSet;
import java.util.Set;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.MultipleFacing;

public abstract class CoreAbstractMultipleFacing extends CoreBlockData implements MultipleFacing {
	
	private Set<BlockFace> faces;
	
	public CoreAbstractMultipleFacing(BlockType type) {
		this(type, 6);
	}
	
	protected CoreAbstractMultipleFacing(BlockType type, int faces) {
		super(type);
		this.faces = new HashSet<>(faces);
	}

	@Override
	public abstract Set<BlockFace> getAllowedFaces();

	@Override
	public Set<BlockFace> getFaces() {
		return faces;
	}

	@Override
	public boolean hasFace(BlockFace face) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		return faces.contains(face);
	}

	@Override
	public void setFace(BlockFace face, boolean has) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (!isValid(face)) 
			throw new IllegalArgumentException("BlockFace is not valid: " + face.name());
		if (has) {
			faces.add(face);
		} else faces.remove(face);
	}
	
	@Override
	public abstract int getStateID();
	
	@Override
	public abstract boolean isValid(BlockFace face);

}
