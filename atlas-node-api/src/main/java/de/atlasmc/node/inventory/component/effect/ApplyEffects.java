package de.atlasmc.node.inventory.component.effect;

import java.util.List;

import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ApplyEffects extends ComponentEffect {
	
	public static final NBTSerializationHandler<ApplyEffects>
	NBT_HANDLER = NBTSerializationHandler
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
	default NBTSerializationHandler<? extends ApplyEffects> getNBTHandler() {
		return NBT_HANDLER;
	}

}
