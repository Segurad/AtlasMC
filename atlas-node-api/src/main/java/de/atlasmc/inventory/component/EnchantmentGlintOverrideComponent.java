package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface EnchantmentGlintOverrideComponent extends ItemComponent {

	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:enchantment_glint_override");
	
	boolean hasGlint();
	
	void setGlint(boolean glint);
	
	EnchantmentGlintOverrideComponent clone();
	
}
