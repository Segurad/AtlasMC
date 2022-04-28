package de.atlascore.potion;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectGlowing extends CoreAbstractPotionEffect {

	public CoreEffectGlowing(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		super(type, amplifier, duration, reducedAmbient, particles, icon);
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
