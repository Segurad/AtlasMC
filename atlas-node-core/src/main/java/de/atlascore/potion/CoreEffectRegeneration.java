package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectRegeneration extends CoreAbstractPotionEffect {

	private float reg;
	
	public CoreEffectRegeneration(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
		switch (amplifier) {
		case 0:
			reg = 0.2f / 20;
			break;
		case 1:
			reg = 0.4f / 20;
			break;
		case 2:
			reg = 0.835f / 20;
			break;
		case 3:
			reg = 1.667f / 20;
			break;
		case 4:
			reg = 3.335f / 20;
			break;
		default:
			reg = 10.0f / 20;
			break;
		}
	}

	@Override
	public void addEffect(LivingEntity entity) {}

	@Override
	public void removeEffect(LivingEntity entity) {}

	@Override
	public int tick(LivingEntity entity, boolean active) {
		entity.setHealth(entity.getHealth() + reg);
		return super.tick(entity, active);
	}
	
}
