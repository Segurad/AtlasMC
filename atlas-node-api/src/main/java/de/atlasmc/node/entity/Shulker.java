package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.enums.EnumUtil;

public interface Shulker extends AbstractGolem {
	
	public static final NBTCodec<Shulker>
	NBT_HANDLER = NBTCodec
					.builder(Shulker.class)
					.include(AbstractGolem.NBT_HANDLER)
					.codec("AttachFace", Shulker::getAttachedFace, Shulker::setAttachedFace, BlockFace.FACE_ID_NBT_CODEC, BlockFace.DOWN)
					.codec("Color", Shulker::getColor, Shulker::setColor, EnumUtil.enumByteNBTCodec(DyeColor.class), DyeColor.MAGENTA)
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
