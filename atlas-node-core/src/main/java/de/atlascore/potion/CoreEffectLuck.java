package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectLuck extends CoreAbstractAttributeModifierPotionEffect {

	public static final NamespacedKey MODIFIER_ID = NamespacedKey.literal("minecraft:luck");
	
	public CoreEffectLuck(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = 1*(getAmplifier()+1);
		AttributeModifier modifier = new AttributeModifier(modifierID, amount, Operation.ADD_VALUE);
		entity.addAttributeModifier(Attribute.GENERIC_LUCK, modifier);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_LUCK).removeModifier(modifierID);
	}

	@Override
	public NamespacedKey getDefaultModifierID() {
		return MODIFIER_ID;
	}

}
