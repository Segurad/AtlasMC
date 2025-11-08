package de.atlasmc.node.inventory.component.effect;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.potion.PotionEffect;

public interface ApplyEffects extends ComponentEffect {
	
	public static final NBTCodec<ApplyEffects>
	NBT_HANDLER = NBTCodec
					.builder(ApplyEffects.class)
					.include(ComponentEffect.NBT_HANDLER)
					.codecList("effects", ApplyEffects::hasEffects, ApplyEffects::getEffects, PotionEffect.NBT_CODEC)
					.floatField("probability", ApplyEffects::getProbability, ApplyEffects::setProbability, 1)
					.build();
	
	public static final StreamCodec<ApplyEffects>
	NBT_CODEC = StreamCodec
				.builder(ApplyEffects.class)
				.include(ComponentEffect.STREAM_CODEC)
				.listCodec(ApplyEffects::hasEffects, ApplyEffects::getEffects, PotionEffect.STREAM_CODEC)
				.floatValue(ApplyEffects::getProbability, ApplyEffects::setProbability)
				.build();
	
	List<PotionEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(PotionEffect effect);
	
	void removeEffect(PotionEffect effect);
	
	float getProbability();
	
	void setProbability(float probability);
	
	ApplyEffects clone();
	
	@Override
	default NBTCodec<? extends ApplyEffects> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends ComponentEffect> getStreamCodec() {
		return STREAM_CODEC;
	}

}
