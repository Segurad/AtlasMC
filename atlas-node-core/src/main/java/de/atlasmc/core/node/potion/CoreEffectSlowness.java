package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.node.attribute.AttributeModifier;
import de.atlasmc.node.attribute.AttributeModifier.Operation;
import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.potion.PotionEffectType;

public class CoreEffectSlowness extends CoreAbstractAttributeModifierPotionEffect {

	public static final NamespacedKey MODIFIER_ID = NamespacedKey.literal("minecraft:slowness");
	
	
	public CoreEffectSlowness(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = -0.15d*(getAmplifier()+1);
		AttributeModifier modifier = new AttributeModifier(modifierID, amount, Operation.ADD_MULTIPLIED_TOTAL);
		entity.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(modifierID);
	}

	@Override
	public NamespacedKey getDefaultModifierID() {
		return MODIFIER_ID;
	}

}
