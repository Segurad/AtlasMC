package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Zoglin extends Monster {
	
	public static final NBTSerializationHandler<Zoglin>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Zoglin.class)
					.include(Monster.NBT_HANDLER)
					.boolField("IsBaby", Zoglin::isBaby, Zoglin::setBaby, false)
					.build();
	
	boolean isBaby();

	void setBaby(boolean baby);
	
	@Override
	default NBTSerializationHandler<? extends LivingEntity> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
