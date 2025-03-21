package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.FaceAttachable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreFaceAttachable extends CoreBlockData implements FaceAttachable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.FACE);
	}
	
	private AttachedFace face;
	
	public CoreFaceAttachable(BlockType type) {
		super(type);
		face = AttachedFace.WALL;
	}

	@Override
	public AttachedFace getAttachedFace() {
		return face;
	}

	@Override
	public void setAttachedFace(AttachedFace face) {
		if (face == null) 
			throw new IllegalArgumentException("AttachedFace can not be null!");
		this.face = face;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+face.ordinal();
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
