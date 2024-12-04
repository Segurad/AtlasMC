package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface FoodComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:food");
	
	int getNutrition();
	
	void setNutrition(int nutrition);
	
	float getSaturation();
	
	void setSaturation(float saturation);
	
	boolean isAlwaysEatable();
	
	void setAlwaysEatable(boolean eatable);
	
	FoodComponent clone();

}
