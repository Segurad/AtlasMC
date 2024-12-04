package de.atlasmc.inventory.component.effect;

import java.util.List;

import de.atlasmc.potion.PotionEffect;

public interface ApplyEffects extends ComponentEffect {
	
	List<PotionEffect> getEffects();
	
	boolean hasEffects();
	
	void addEffect(PotionEffect effect);
	
	void removeEffect(PotionEffect effect);
	
	float getProbability();
	
	void setProbability(float probability);
	
	@Override
	default ConsumeEffectType getType() {
		return ConsumeEffectType.APPLY_EFFECTS;
	}

}
