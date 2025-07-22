package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Bat extends AmbientCreature {
	
	public static final NBTSerializationHandler<Bat>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Bat.class)
					.include(AmbientCreature.NBT_HANDLER)
					.boolField("BatFlags", Bat::isHanging, Bat::setHanging, false)
					.build();
	
	boolean isHanging();

	void setHanging(boolean hanging);

	@Override
	default NBTSerializationHandler<? extends Bat> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
