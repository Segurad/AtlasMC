package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Bat extends AmbientCreature {
	
	@NotNull
	public static final NBTCodec<Bat>
	NBT_CODEC = NBTCodec
					.builder(Bat.class)
					.include(AmbientCreature.NBT_CODEC)
					.boolField("BatFlags", Bat::isHanging, Bat::setHanging, false)
					.build();
	
	boolean isHanging();

	void setHanging(boolean hanging);

	@Override
	default NBTCodec<? extends Bat> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
