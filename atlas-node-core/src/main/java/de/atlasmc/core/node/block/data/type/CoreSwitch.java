package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Switch;

public class CoreSwitch extends CoreDirectional4Faces implements Switch {
	
	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				PropertyType.POWERED,
				PropertyType.FACE);
	}
	
	private AttachedFace face;
	private boolean powered;
	
	public CoreSwitch(BlockType type) {
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
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				(powered?0:1)+
				getFaceValue()*2+
				face.ordinal()*8;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
