package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface TraderLlama extends Llama {

	public static final NBTSerializationHandler<TraderLlama>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TraderLlama.class)
					.include(Llama.NBT_HANDLER)
					.intField("DespawnDelay", TraderLlama::getDespawnDelay, TraderLlama::setDespawnDelay)
					.build();
	
	int getDespawnDelay();
	
	void setDespawnDelay(int delay);
	
	@Override
	default NBTSerializationHandler<? extends Llama> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
