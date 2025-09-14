package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface SnowGolem extends AbstractGolem {
	
	public static final NBTSerializationHandler<SnowGolem>
	NBT_HANDLER = NBTSerializationHandler
					.builder(SnowGolem.class)
					.include(AbstractGolem.NBT_HANDLER)
					.boolField("Pumpkin", SnowGolem::hasPumpkinHat, SnowGolem::setPumkinHat, true)
					.build();
	
	boolean hasPumpkinHat();
	
	void setPumkinHat(boolean hat);

	@Override
	default NBTSerializationHandler<? extends SnowGolem> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
