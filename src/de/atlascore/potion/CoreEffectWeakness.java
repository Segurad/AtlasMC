package de.atlascore.potion;

import java.util.UUID;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffectType;

public class CoreEffectWeakness extends CoreAbstractPotionEffect {

	protected static final String
	MODIFIER_NAME_WEAKNESS = "effect.weakness";
	protected static final UUID
	MODIFIER_UUID_WEAKNESS = UUID.fromString("55FCED67-E92A-486E-9800-B47F202C4386");
	
	public CoreEffectWeakness(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		super(type, amplifier, duration, reducedAmbient, particles, icon);
	}

	@Override
	public void addEffect(LivingEntity entity) {
		double amount = -4*(amplifier+1);
		AttributeModifier modifer = new AttributeModifier(MODIFIER_UUID_WEAKNESS, MODIFIER_NAME_WEAKNESS, amount, Operation.ADD_NUMBER);
		entity.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifer);
	}

	@Override
	public void removeEffect(LivingEntity entity) {
		entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).removeModifier(MODIFIER_UUID_WEAKNESS);
	}

}
