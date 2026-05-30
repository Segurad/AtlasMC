package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Tadpole extends Fish {
	
	@NotNull
	public static final NBTCodec<Tadpole>
	NBT_CODEC = NBTCodec
					.builder(Tadpole.class)
					.include(Fish.NBT_CODEC)
					.intField("Age", Tadpole::getAge, Tadpole::setAge, 0)
					.build();
	
	int getAge();
	
	void setAge(int age);
	
	@Override
	default NBTCodec<? extends Fish> getNBTCodec() {
		return NBT_CODEC;
	}

}
