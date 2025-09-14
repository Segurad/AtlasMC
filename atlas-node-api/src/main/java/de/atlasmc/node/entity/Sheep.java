package de.atlasmc.node.entity;

import de.atlasmc.node.DyeColor;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Sheep extends Animal {
	
	public static final NBTSerializationHandler<Sheep>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Sheep.class)
					.include(Animal.NBT_HANDLER)
					.enumByteField("Color", Sheep::getColor, Sheep::setColor, DyeColor::getByID, DyeColor::getID, DyeColor.WHITE)
					.boolField("Sheared", Sheep::isSheared, Sheep::setSheared, false)
					.build();
	
	DyeColor getColor();
	
	void setColor(DyeColor color);
	
	boolean isSheared();

	void setSheared(boolean sheared);
	
	@Override
	default NBTSerializationHandler<? extends Sheep> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
