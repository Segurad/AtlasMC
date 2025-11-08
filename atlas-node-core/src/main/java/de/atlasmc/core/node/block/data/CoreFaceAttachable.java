package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.FaceAttachable;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreFaceAttachable extends CoreBlockData implements FaceAttachable {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.FACE);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
