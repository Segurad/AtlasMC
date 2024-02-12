package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectSlowness extends CoreAbstractPotionEffect {

	protected static final String
	MODIFIER_NAME_SLOW = "effect.moveSlowdown";
	protected static final UUID
	MODIFIER_UUID_SLOW = UUID.fromString("7107DE5E-7CE8-4030-940E-514C1F160890");
	
	
	public CoreEffectSlowness(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = -0.15d*(amplifier+1);
		AttributeModifier modifier = new AttributeModifier(MODIFIER_UUID_SLOW, MODIFIER_NAME_SLOW, amount, Operation.MULTIPLY_SCALAR_1);
		entity.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(MODIFIER_UUID_SLOW);
	}

}
