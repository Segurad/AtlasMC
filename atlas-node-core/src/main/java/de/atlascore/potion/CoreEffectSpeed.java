package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectSpeed extends CoreAbstractPotionEffect {

	protected static final String
	MODIFIER_NAME_SPEED = "effect.moveSpeed";
	protected static final UUID
	MODIFIER_UUID_SPEED = UUID.fromString("91AEAA56-376B-4498-935B-2F7F68070635");
	
	public CoreEffectSpeed(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = 0.2d*(amplifier+1);
		AttributeModifier modifier = new AttributeModifier(MODIFIER_UUID_SPEED, MODIFIER_NAME_SPEED, amount, Operation.MULTIPLY_SCALAR_1);
		entity.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(MODIFIER_UUID_SPEED);
	}

}
