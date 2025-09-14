package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.potion.PotionEffectType;

public class CoreEffectGlowing extends CoreAbstractPotionEffect {

	public CoreEffectGlowing(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		entity.setGlowing(true);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.setGlowing(false);
	}

}
