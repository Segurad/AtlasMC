package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectSlowness extends CoreAbstractAttributeModifierPotionEffect {

	public static final NamespacedKey MODIFIER_ID = NamespacedKey.literal("minecraft:slowness");
	
	
	public CoreEffectSlowness(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = -0.15d*(amplifier+1);
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
