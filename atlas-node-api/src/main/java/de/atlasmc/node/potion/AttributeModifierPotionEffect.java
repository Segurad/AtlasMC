package de.atlasmc.node.potion;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.util.annotation.NotNull;

/**
 * PotionEffect that modifies {@link Attribute}s
 */
public interface AttributeModifierPotionEffect extends PotionEffect {

	/**
	 * Returns the modifier id used for the effect
	 * @return
	 */
	@NotNull
	NamespacedKey getModifierID();
	
	void setModifierID(NamespacedKey id);
	
	@NotNull
	NamespacedKey getDefaultModifierID();
	
}
