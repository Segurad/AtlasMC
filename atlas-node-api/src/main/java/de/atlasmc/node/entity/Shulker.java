package de.atlasmc.node.entity;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Shulker extends AbstractGolem {
	
	public static final NBTCodec<Shulker>
	NBT_HANDLER = NBTCodec
					.builder(Shulker.class)
					.include(AbstractGolem.NBT_HANDLER)
					.objectByteField("AttachFace", Shulker::getAttachedFace, Shulker::setAttachedFace, BlockFace::getByFaceID, BlockFace::getFaceID, BlockFace.DOWN)
					.enumByteField("Color", Shulker::getColor, Shulker::setColor, DyeColor.class, DyeColor.MAGENTA)
					.byteField("Peek", Shulker::getShieldHeight, Shulker::setShieldHeight, (byte) 16)
					.build();
	
	@NotNull
	BlockFace getAttachedFace();
	
	void setAttachedFace(@NotNull BlockFace attached);
	
	int getShieldHeight();
	
	void setShieldHeight(int height);
	
	@Nullable
	DyeColor getColor();

	void setColor(@Nullable DyeColor color);
	
	@Override
	default NBTCodec<? extends Shulker> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
