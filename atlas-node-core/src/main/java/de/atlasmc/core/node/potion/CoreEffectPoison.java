package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.potion.PotionEffectType;

public class CoreEffectPoison extends CoreAbstractPotionEffect {

	private float damage;
	
	public CoreEffectPoison(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
		switch (amplifier) {
		case 0:
			damage = 0.4f / 20;
			break;
		case 1:
			damage = 0.83f / 20;
			break;
		case 2:
			damage = 0.166f / 20;
			break;
		case 3:
			damage = 3.33f / 20;
			break;
		default:
			damage = 10.0f / 20;
			break;
		}
	}

	@Override
	public void addEffect(LivingEntity entity) {
		// not required
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		// not required
	}

	@Override
	public int tick(LivingEntity entity, boolean active) {
		entity.damage(damage);
		return super.tick(entity, active);
	}
	
}
