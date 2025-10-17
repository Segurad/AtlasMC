package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Witch extends Raider {
	
	public static final NBTCodec<Witch>
	NBT_HANDLER = NBTCodec
					.builder(Witch.class)
					.include(Raider.NBT_HANDLER)
					.boolField("IsDrinkingPotion", Witch::isDrinkingPotion, Witch::setDrinkingPotion, false) // non standard
					.build();
	
	boolean isDrinkingPotion();
	
	void setDrinkingPotion(boolean drinking);
	
	@Override
	default NBTCodec<? extends Witch> getNBTCodec() {
		return NBT_HANDLER;
	}

}
