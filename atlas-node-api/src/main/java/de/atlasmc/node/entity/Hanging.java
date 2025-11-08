package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockFace;

public interface Hanging extends Entity {
	
	public static final NBTCodec<Hanging>
	NBT_HANDLER = NBTCodec
					.builder(Hanging.class)
					.include(Entity.NBT_CODEC)
					.codec("Facing", Hanging::getAttachedFace, Hanging::setFacingDirection, BlockFace.FACE_ID_NBT_CODEC, BlockFace.SOUTH)
					.build();
	
	BlockFace getAttachedFace();
	
	void setFacingDirection(BlockFace face);

}
