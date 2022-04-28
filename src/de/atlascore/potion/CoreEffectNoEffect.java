package de.atlascore.potion;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

/**
 * Implementation of {@link PotionEffect} that has currently implemented effect or is client side
 */
public class CoreEffectNoEffect extends CoreAbstractPotionEffect {

	public CoreEffectNoEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		super(type, amplifier, duration, reducedAmbient, particles, icon);
	}

	@Override
	public void addEffect(LivingEntity entity) {}

	@Override
	public void removeEffect(LivingEntity entity) {}

}
