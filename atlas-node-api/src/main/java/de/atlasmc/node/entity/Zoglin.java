package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Zoglin extends Monster {
	
	@NotNull
	public static final NBTCodec<Zoglin>
	NBT_CODEC = NBTCodec
					.builder(Zoglin.class)
					.include(Monster.NBT_CODEC)
					.boolField("IsBaby", Zoglin::isBaby, Zoglin::setBaby, false)
					.build();
	
	boolean isBaby();

	void setBaby(boolean baby);
	
	@Override
	default NBTCodec<? extends LivingEntity> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
