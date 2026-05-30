package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface TraderLlama extends Llama {

	@NotNull
	public static final NBTCodec<TraderLlama>
	NBT_CODEC = NBTCodec
					.builder(TraderLlama.class)
					.include(Llama.NBT_CODEC)
					.intField("DespawnDelay", TraderLlama::getDespawnDelay, TraderLlama::setDespawnDelay)
					.build();
	
	int getDespawnDelay();
	
	void setDespawnDelay(int delay);
	
	@Override
	default NBTCodec<? extends Llama> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
