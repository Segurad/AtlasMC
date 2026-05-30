package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Witch extends Raider {
	
	@NotNull
	public static final NBTCodec<Witch>
	NBT_CODEC = NBTCodec
					.builder(Witch.class)
					.include(Raider.NBT_CODEC)
					.boolField("IsDrinkingPotion", Witch::isDrinkingPotion, Witch::setDrinkingPotion, false) // non standard
					.build();
	
	boolean isDrinkingPotion();
	
	void setDrinkingPotion(boolean drinking);
	
	@Override
	default NBTCodec<? extends Witch> getNBTCodec() {
		return NBT_CODEC;
	}

}
