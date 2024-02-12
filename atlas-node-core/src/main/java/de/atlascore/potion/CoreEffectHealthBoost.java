package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectHealthBoost extends CoreAbstractPotionEffect {

	protected static final String
	MODIFIER_NAME_HEALTH = "effect.healthBoost";
	protected static final UUID
	MODIFIER_UUID_HEALTH = UUID.fromString("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC");
	
	public CoreEffectHealthBoost(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = 2d*(amplifier+1);
		AttributeModifier modifier = new AttributeModifier(MODIFIER_UUID_HEALTH, MODIFIER_NAME_HEALTH, amount, Operation.ADD_NUMBER);
		entity.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, modifier);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(MODIFIER_UUID_HEALTH);
	}

}
