package de.atlasmc.node.entity;

import de.atlasmc.node.DyeColor;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Sheep extends Animal {
	
	public static final NBTCodec<Sheep>
	NBT_HANDLER = NBTCodec
					.builder(Sheep.class)
					.include(Animal.NBT_HANDLER)
					.enumByteField("Color", Sheep::getColor, Sheep::setColor, DyeColor.class, DyeColor.WHITE)
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
