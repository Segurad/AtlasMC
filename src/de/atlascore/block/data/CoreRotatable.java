package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Rotatable;
import de.atlasmc.util.Validate;

public class CoreRotatable extends CoreBlockData implements Rotatable {

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
		Validate.notNull(rotation, "BlockFace can not be null!");
		Validate.isFalse(rotation == BlockFace.UP || rotation == BlockFace.DOWN, "BlockFace is not valid: " + rotation.name());
		this.rotation = rotation;
	}
	
	protected int getRotationValue() {
		switch(rotation) {
			case EAST: return 12;
			case EAST_NORTH_EAST: return 11;
			case EAST_SOUTH_EAST: return 13;
			case NORTH: return 8;
			case NORTH_EAST: return 10;
			case NORTH_NORTH_EAST: return 9;
			case NORTH_NORTH_WEST: return 7;
			case NORTH_WEST: return 6;
			case SOUTH: return 0;
			case SOUTH_EAST: return 14;
			case SOUTH_SOUTH_EAST: return 15;
			case SOUTH_SOUTH_WEST: return 1;
			case SOUTH_WEST: return 2;
			case WEST: return 4;
			case WEST_NORTH_WEST: return 5;
			case WEST_SOUTH_WEST: return 3;
			default: return 0;
		}
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+getRotationValue();
	}

}
