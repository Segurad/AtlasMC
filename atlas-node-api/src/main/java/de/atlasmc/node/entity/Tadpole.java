package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface Tadpole extends Fish {
	
	public static final NBTCodec<Tadpole>
	NBT_HANDLER = NBTCodec
					.builder(Tadpole.class)
					.include(Fish.NBT_HANDLER)
					.intField("Age", Tadpole::getAge, Tadpole::setAge, 0)
					.build();
	
	int getAge();
	
	void setAge(int age);
	
	@Override
	default NBTCodec<? extends Fish> getNBTCodec() {
		return NBT_HANDLER;
	}

}
