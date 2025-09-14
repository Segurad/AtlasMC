package de.atlasmc.node.entity;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Hanging extends Entity {
	
	public static final NBTSerializationHandler<Hanging>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Hanging.class)
					.include(Entity.NBT_HANDLER)
					.enumByteField("Facing", Hanging::getAttachedFace, Hanging::setFacingDirection, BlockFace::getByFaceID, BlockFace::getFaceID, BlockFace.SOUTH)
					.build();
	
	BlockFace getAttachedFace();
	
	void setFacingDirection(BlockFace face);

}
