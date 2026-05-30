package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.annotation.NotNull;

public interface Hanging extends Entity {
	
	@NotNull
	public static final NBTCodec<Hanging>
	NBT_CODEC = NBTCodec
					.builder(Hanging.class)
					.include(Entity.NBT_CODEC)
					.codec("Facing", Hanging::getAttachedFace, Hanging::setFacingDirection, BlockFace.FACE_ID_NBT_CODEC, BlockFace.SOUTH)
					.build();
	
	BlockFace getAttachedFace();
	
	void setFacingDirection(BlockFace face);

	@Override
	default NBTCodec<? extends Hanging> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
