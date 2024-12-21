package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Rotatable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreRotatable extends CoreBlockData implements Rotatable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.ROTATION);
	}
	
	private BlockFace rotation;
	
	public CoreRotatable(Material material) {
		super(material);
		rotation = BlockFace.SOUTH;
	}

	@Override
	public BlockFace getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(BlockFace rotation) {
		if (rotation == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (rotation == BlockFace.UP || rotation == BlockFace.DOWN) 
			throw new IllegalArgumentException("BlockFace is not valid: " + rotation.name());
		this.rotation = rotation;
	}
	
	protected int getRotationValue() {
		return rotation == null ? 0 : rotation.getRotation();
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+getRotationValue();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
