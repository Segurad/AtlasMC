package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.node.attribute.AttributeModifier.Operation;
import de.atlasmc.node.potion.PotionEffectType;
import de.atlasmc.util.annotation.NotNull;

public class CoreEffectSpeed extends CoreAbstractAttributeModifierPotionEffect {

	@NotNull
	public static final NamespacedKey MODIFIER_ID = NamespacedKey.literal("minecraft:speed");
	
	public CoreEffectSpeed(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

	@Override
	public NamespacedKey getDefaultModifierID() {
		return MODIFIER_ID;
	}
	
	@Override
	public Operation getOperation() {
		return Operation.ADD_MULTIPLIED_TOTAL;
	}

	@Override
	public Attribute getAttribute() {
		return Attribute.GENERIC_MOVEMENT_SPEED;
	}

	@Override
	public double getValue() {
		return 0.2d*(getAmplifier()+1);
	}

}
