package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.potion.PotionEffectType;

public class CoreEffectInstantHealth extends CoreAbstractPotionEffect {

	public CoreEffectInstantHealth(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, -1, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		entity.setHealth((float) (entity.getHealth() + 4 * Math.pow(2, getAmplifier())));
		// TODO add damage to undead
	}
	
	@Override
	public boolean isOnlyOnApply() {
		return true;
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		// not required
	}
	
	@Override
	public int tick(LivingEntity entity, boolean active) {
		return -1;
	}

}
