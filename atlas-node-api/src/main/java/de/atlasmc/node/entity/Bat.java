package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface Bat extends AmbientCreature {
	
	public static final NBTCodec<Bat>
	NBT_HANDLER = NBTCodec
					.builder(Bat.class)
					.include(AmbientCreature.NBT_HANDLER)
					.boolField("BatFlags", Bat::isHanging, Bat::setHanging, false)
					.build();
	
	boolean isHanging();

	void setHanging(boolean hanging);

	@Override
	default NBTCodec<? extends Bat> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
