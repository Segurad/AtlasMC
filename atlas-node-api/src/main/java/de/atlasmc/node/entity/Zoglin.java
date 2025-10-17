package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Zoglin extends Monster {
	
	public static final NBTCodec<Zoglin>
	NBT_HANDLER = NBTCodec
					.builder(Zoglin.class)
					.include(Monster.NBT_HANDLER)
					.boolField("IsBaby", Zoglin::isBaby, Zoglin::setBaby, false)
					.build();
	
	boolean isBaby();

	void setBaby(boolean baby);
	
	@Override
	default NBTCodec<? extends LivingEntity> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
