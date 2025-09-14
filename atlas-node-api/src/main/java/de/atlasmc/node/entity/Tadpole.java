package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Tadpole extends Fish {
	
	public static final NBTSerializationHandler<Tadpole>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Tadpole.class)
					.include(Fish.NBT_HANDLER)
					.intField("Age", Tadpole::getAge, Tadpole::setAge, 0)
					.build();
	
	int getAge();
	
	void setAge(int age);
	
	@Override
	default NBTSerializationHandler<? extends Fish> getNBTHandler() {
		return NBT_HANDLER;
	}

}
