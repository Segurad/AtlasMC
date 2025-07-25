package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Witch extends Raider {
	
	public static final NBTSerializationHandler<Witch>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Witch.class)
					.include(Raider.NBT_HANDLER)
					.boolField("IsDrinkingPotion", Witch::isDrinkingPotion, Witch::setDrinkingPotion, false) // non standard
					.build();
	
	boolean isDrinkingPotion();
	
	void setDrinkingPotion(boolean drinking);
	
	@Override
	default NBTSerializationHandler<? extends Witch> getNBTHandler() {
		return NBT_HANDLER;
	}

}
