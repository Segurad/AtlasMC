package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.potion.AttributeModifierPotionEffect;
import de.atlasmc.node.potion.PotionEffectType;

public abstract class CoreAbstractAttributeModifierPotionEffect extends CoreAbstractPotionEffect implements AttributeModifierPotionEffect {

	protected NamespacedKey modifierID;
	
	public CoreAbstractAttributeModifierPotionEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		super(type, amplifier, duration, reducedAmbient, particles, icon, uuid);
		this.modifierID = getDefaultModifierID();
	}

	@Override
	public NamespacedKey getModifierID() {
		return modifierID;
	}

	@Override
	public void setModifierID(NamespacedKey id) {
		if (id == null) {
			this.modifierID = getDefaultModifierID();
		} else {
			this.modifierID = id;
		}
	}	

}
