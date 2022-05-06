package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Rotatable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRotatable extends CoreBlockData implements Rotatable {

	private BlockFace rotation;
	
	protected static final CharKey ROTATION = CharKey.of("rotation");
	
	static {
		NBT_FIELDS.setField(ROTATION, (holder, reader) -> {
			if (Rotatable.class.isInstance(holder)) {
				((Rotatable) holder).setRotation(CoreRotatable.getBlockFace(Integer.parseInt(reader.readStringTag())));
			} else reader.skipTag();
		});
	}
	
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
		if (rotation == null) throw new IllegalArgumentException("BlockFace can not be null!");
		if (rotation == BlockFace.UP || rotation == BlockFace.DOWN) throw new IllegalArgumentException("BlockFace is not valid: " + rotation.name());
		this.rotation = rotation;
	}
	
	protected int getRotationValue() {
		return getRotationValue(rotation);
	}
	
	protected static int getRotationValue(BlockFace rotation) {
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
	
	protected static BlockFace getBlockFace(int rotation) {
		switch(rotation) {
			case 12: return BlockFace.EAST;
			case 11: return BlockFace.EAST_NORTH_EAST;
			case 13: return BlockFace.EAST_SOUTH_EAST;
			case 8: return BlockFace.NORTH;
			case 10: return BlockFace.NORTH_EAST;
			case 9: return BlockFace.NORTH_NORTH_EAST;
			case 7: return BlockFace.NORTH_NORTH_WEST;
			case 6: return BlockFace.NORTH_WEST;
			case 0: return BlockFace.SOUTH;
			case 14: return BlockFace.SOUTH_EAST;
			case 15: return BlockFace.SOUTH_SOUTH_WEST;
			case 1: return BlockFace.SOUTH_SOUTH_WEST;
			case 2: return BlockFace.SOUTH_WEST;
			case 4: return BlockFace.WEST;
			case 5: return BlockFace.WEST_NORTH_WEST;
			case 3: return BlockFace.WEST_SOUTH_WEST;
			default: return BlockFace.SOUTH;
		}
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+getRotationValue();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(ROTATION, String.valueOf(getRotationValue()));
	}

}
