package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.util.enums.EnumUtil;

public interface Sheep extends Animal {
	
	public static final NBTCodec<Sheep>
	NBT_HANDLER = NBTCodec
					.builder(Sheep.class)
					.include(Animal.NBT_CODEC)
					.codec("Color", Sheep::getColor, Sheep::setColor, EnumUtil.enumByteNBTCodec(DyeColor.class), DyeColor.WHITE)
					.boolField("Sheared", Sheep::isSheared, Sheep::setSheared, false)
					.build();
	
	DyeColor getColor();
	
	void setColor(DyeColor color);
	
	boolean isSheared();

	void setSheared(boolean sheared);
	
	@Override
	default NBTCodec<? extends Sheep> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
