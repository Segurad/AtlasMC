package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface RarityComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:rarity");
	
	Rarity getRarity();
	
	void setRarity(Rarity rarity);
	
	RarityComponent clone();
	
	public static enum Rarity {
		
		COMMON,
		UNCOMMON,
		RARE,
		EPIC
		
	}

}
