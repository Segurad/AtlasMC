package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.node.potion.PotionEffectType;
import de.atlasmc.util.annotation.NotNull;

public class CoreEffectStrength extends CoreAbstractAttributeModifierPotionEffect {

	@NotNull
	public static final NamespacedKey MODIFIER_ID = NamespacedKey.literal("minecraft:strength");
	
	public CoreEffectStrength(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}
	
	@Override
	public NamespacedKey getDefaultModifierID() {
		return MODIFIER_ID;
	}

	@Override
	public Attribute getAttribute() {
		return Attribute.GENERIC_ATTACK_DAMAGE;
	}

	@Override
	public double getValue() {
		return 1.5d*(getAmplifier()+1);
	}

}
