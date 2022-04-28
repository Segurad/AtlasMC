package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectLuck extends CoreAbstractPotionEffect {

	protected static final String
	MODIFIER_NAME_LUCK = "effect.luck";
	protected static final UUID
	MODIFIER_UUID_LUCK = UUID.fromString("03C3C89D-7037-4B42-869F-B146BCB64D2E");
	
	public CoreEffectLuck(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		super(type, amplifier, duration, reducedAmbient, particles, icon);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = 1*(amplifier+1);
		AttributeModifier modifier = new AttributeModifier(MODIFIER_UUID_LUCK, MODIFIER_NAME_LUCK, amount, Operation.ADD_NUMBER);
		entity.addAttributeModifier(Attribute.GENERIC_LUCK, modifier);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_LUCK).removeModifier(MODIFIER_UUID_LUCK);
	}

}
