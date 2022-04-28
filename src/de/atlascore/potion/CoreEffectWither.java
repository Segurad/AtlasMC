package de.atlascore.potion;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectWither extends CoreAbstractPotionEffect {

	private float damage;
	
	public CoreEffectWither(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		super(type, amplifier, duration, reducedAmbient, particles, icon);
		switch (amplifier) {
		case 0:
			damage = 0.25f / 20;
			break;
		case 1:
			damage = 0.5f / 20;
			break;
		case 2:
			damage = 1.0f / 20;
			break;
		case 3:
			damage = 2.0f / 20;
			break;
		case 4:
			damage = 5.0f / 20;
			break;
		default:
			damage = 10.0f / 20;
			break;
		}
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
