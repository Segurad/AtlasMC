package de.atlasmc.node.inventory.component;

import java.util.List;
import java.util.Objects;

import de.atlasmc.node.potion.PotionEffect;

public interface AbstractPotionEffectComponent extends ItemComponent {

	List<PotionEffect> getEffects();
	
	boolean hasEffects();
	
	default boolean addEffect(PotionEffect effect) {
		Objects.requireNonNull(effect, "effect");
		return getEffects().add(effect);
	}

	default boolean removeEffect(PotionEffect effect) {
		Objects.requireNonNull(effect, "effect");
		if (hasEffects())
			return getEffects().remove(effect);
		return false;
	}
	
	@Override
	AbstractPotionEffectComponent clone();
	
}
