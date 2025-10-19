package de.atlasmc.node.entity;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Hanging extends Entity {
	
	public static final NBTCodec<Hanging>
	NBT_HANDLER = NBTCodec
					.builder(Hanging.class)
					.include(Entity.NBT_HANDLER)
					.objectByteField("Facing", Hanging::getAttachedFace, Hanging::setFacingDirection, BlockFace::getByFaceID, BlockFace::getFaceID, BlockFace.SOUTH)
					.build();
	
	BlockFace getAttachedFace();
	
	void setFacingDirection(BlockFace face);

}
