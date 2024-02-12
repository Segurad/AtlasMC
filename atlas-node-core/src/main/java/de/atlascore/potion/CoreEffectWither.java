package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectWither extends CoreAbstractPotionEffect {

	private float damage;
	
	public CoreEffectWither(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
		damage = 0.25f;
		for (int i = 1; i < amplifier; i++)
			damage *= 2;	
		damage /= 20;
	}

	@Override
	public void addEffect(LivingEntity entity) {}

	@Override
	public void removeEffect(LivingEntity entity) {}

	@Override
	public int tick(LivingEntity entity, boolean active) {
		entity.damage(damage);
		return super.tick(entity, active);
	}
	
}
