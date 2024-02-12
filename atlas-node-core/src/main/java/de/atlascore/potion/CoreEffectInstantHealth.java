package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectInstantHealth extends CoreAbstractPotionEffect {

	public CoreEffectInstantHealth(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, -1, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		entity.setHealth((float) (entity.getHealth() + 4 * Math.pow(2, amplifier)));
		// TODO add damage to undead
	}
	
	@Override
	public boolean isOnlyOnApply() {
		return true;
	}

	@Override
	public void removeEffect(LivingEntity entity) {}
	
	@Override
	public int tick(LivingEntity entity, boolean active) {
		return -1;
	}

}
