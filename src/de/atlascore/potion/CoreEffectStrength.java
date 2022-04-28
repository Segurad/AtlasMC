package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectStrength extends CoreAbstractPotionEffect {

	protected static final String
	MODIFIER_NAME_STRENGTH = "effect.damageBoost";
	protected static final UUID
	MODIFIER_UUID_STRENGTH = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	
	public CoreEffectStrength(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		super(type, amplifier, duration, reducedAmbient, particles, icon);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = 1.5d*(amplifier+1);
		AttributeModifier modifer = new AttributeModifier(MODIFIER_UUID_STRENGTH, MODIFIER_NAME_STRENGTH, amount, Operation.ADD_NUMBER);
		entity.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifer);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(MODIFIER_UUID_STRENGTH);
	}

}
