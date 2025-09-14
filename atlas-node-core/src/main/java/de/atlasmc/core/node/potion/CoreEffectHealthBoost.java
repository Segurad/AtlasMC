package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.node.attribute.AttributeModifier;
import de.atlasmc.node.attribute.AttributeModifier.Operation;
import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.potion.PotionEffectType;

public class CoreEffectHealthBoost extends CoreAbstractAttributeModifierPotionEffect {

	public static final NamespacedKey MODIFIER_ID = NamespacedKey.literal("minecraft:health_boost");
	
	public CoreEffectHealthBoost(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = 2d*(getAmplifier()+1);
		AttributeModifier modifier = new AttributeModifier(modifierID, amount, Operation.ADD_VALUE);
		entity.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, modifier);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(modifierID);
	}

	@Override
	public NamespacedKey getDefaultModifierID() {
		return MODIFIER_ID;
	}

}
