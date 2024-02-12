package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectUnluck extends CoreAbstractPotionEffect {

	protected static final String
	MODIFIER_NAME_LUCK = "effect.unluck";
	protected static final UUID
	MODIFIER_UUID_LUCK = UUID.fromString("CC5AF142-2BD2-4215-B636-2605AED11727");
	
	public CoreEffectUnluck(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = -1*(amplifier+1);
		AttributeModifier modifier = new AttributeModifier(MODIFIER_UUID_LUCK, MODIFIER_NAME_LUCK, amount, Operation.ADD_NUMBER);
		entity.addAttributeModifier(Attribute.GENERIC_LUCK, modifier);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_LUCK).removeModifier(MODIFIER_UUID_LUCK);
	}

}
