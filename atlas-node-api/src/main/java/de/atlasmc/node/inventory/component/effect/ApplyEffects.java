package de.atlasmc.node.inventory.component.effect;

import java.util.List;

import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ApplyEffects extends ComponentEffect {
	
	public static final NBTCodec<ApplyEffects>
	NBT_HANDLER = NBTCodec
					.builder(ApplyEffects.class)
					.include(ComponentEffect.NBT_HANDLER)
					.typeList("effects", ApplyEffects::hasEffects, ApplyEffects::getEffects, PotionEffect.NBT_HANDLER)
					.floatField("probability", ApplyEffects::getProbability, ApplyEffects::setProbability, 1)
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

}
