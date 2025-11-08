package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface TraderLlama extends Llama {

	public static final NBTCodec<TraderLlama>
	NBT_HANDLER = NBTCodec
					.builder(TraderLlama.class)
					.include(Llama.NBT_HANDLER)
					.intField("DespawnDelay", TraderLlama::getDespawnDelay, TraderLlama::setDespawnDelay)
					.build();
	
	int getDespawnDelay();
	
	void setDespawnDelay(int delay);
	
	@Override
	default NBTCodec<? extends Llama> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
