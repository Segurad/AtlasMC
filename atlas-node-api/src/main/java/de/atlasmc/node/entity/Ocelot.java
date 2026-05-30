package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Ocelot extends Animal {
	
	@NotNull
	public static final NBTCodec<Ocelot>
	NBT_CODEC = NBTCodec
					.builder(Ocelot.class)
					.include(Animal.NBT_CODEC)
					.boolField("Trusting", Ocelot::isTrusting, Ocelot::setTrusting, false)
					.build();
	
	boolean isTrusting();
	
	void setTrusting(boolean trusting);

	@Override
	default NBTCodec<? extends AgeableMob> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
