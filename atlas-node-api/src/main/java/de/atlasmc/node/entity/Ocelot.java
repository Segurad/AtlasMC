package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Ocelot extends Animal {
	
	public static final NBTCodec<Ocelot>
	NBT_HANDLER = NBTCodec
					.builder(Ocelot.class)
					.include(Animal.NBT_HANDLER)
					.boolField("Trusting", Ocelot::isTrusting, Ocelot::setTrusting, false)
					.build();
	
	boolean isTrusting();
	
	void setTrusting(boolean trusting);

	@Override
	default NBTCodec<? extends AgeableMob> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
