package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.node.potion.PotionEffectType;

/**
 * Implementation of {@link PotionEffect} that has currently implemented effect or is client side
 */
public class CoreEffectNoEffect extends CoreAbstractPotionEffect {

	public CoreEffectNoEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		// not required
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		// not required
	}

}
