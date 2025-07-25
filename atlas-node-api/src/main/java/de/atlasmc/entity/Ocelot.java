package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Ocelot extends Animal {
	
	public static final NBTSerializationHandler<Ocelot>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Ocelot.class)
					.include(Animal.NBT_HANDLER)
					.boolField("Trusting", Ocelot::isTrusting, Ocelot::setTrusting, false)
					.build();
	
	boolean isTrusting();
	
	void setTrusting(boolean trusting);

	@Override
	default NBTSerializationHandler<? extends AgeableMob> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
