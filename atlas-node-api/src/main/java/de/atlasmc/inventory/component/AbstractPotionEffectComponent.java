package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.potion.PotionEffect;

public interface AbstractPotionEffectComponent extends ItemComponent {

	List<PotionEffect> getEffects();
	
	boolean hasEffects();
	
	default boolean addEffect(PotionEffect effect) {
		if (effect == null)
			throw new IllegalArgumentException("Effect can not be null!");
		return getEffects().add(effect);
	}

	default boolean removeEffect(PotionEffect effect) {
		if (effect == null)
			throw new IllegalArgumentException("Effect can not be null!");
		if (hasEffects())
			return getEffects().remove(effect);
		return false;
	}
	
	AbstractPotionEffectComponent clone();
	
}
