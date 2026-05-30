package de.atlasmc.node.inventory.component.effect;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.util.annotation.NotNull;

public interface ApplyEffects extends ComponentEffect {
	
	@NotNull
	public static final NBTCodec<ApplyEffects>
	NBT_CODEC = NBTCodec
					.builder(ApplyEffects.class)
					.include(ComponentEffect.NBT_CODEC)
					.codecList("effects", ApplyEffects::hasEffects, ApplyEffects::getEffects, PotionEffect.NBT_CODEC)
					.floatField("probability", ApplyEffects::getProbability, ApplyEffects::setProbability, 1)
					.build();
	
	@NotNull
	public static final StreamCodec<ApplyEffects>
	STREAM_CODEC = StreamCodec
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
	
	@Override
	ApplyEffects clone();
	
	@Override
	default NBTCodec<? extends ApplyEffects> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends ComponentEffect> getStreamCodec() {
		return STREAM_CODEC;
	}

}
