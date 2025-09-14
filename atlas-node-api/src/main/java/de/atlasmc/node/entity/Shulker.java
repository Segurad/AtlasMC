package de.atlasmc.node.entity;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Shulker extends AbstractGolem {
	
	public static final NBTSerializationHandler<Shulker>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Shulker.class)
					.include(AbstractGolem.NBT_HANDLER)
					.enumByteField("AttachFace", Shulker::getAttachedFace, Shulker::setAttachedFace, BlockFace::getByFaceID, BlockFace::getFaceID, BlockFace.DOWN)
					.enumByteField("Color", Shulker::getColor, Shulker::setColor, DyeColor::getByID, DyeColor::getID, DyeColor.MAGENTA)
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
	default NBTSerializationHandler<? extends Shulker> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
