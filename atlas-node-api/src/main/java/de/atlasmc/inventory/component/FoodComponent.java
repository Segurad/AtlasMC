package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface FoodComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:food");
	
	public static final NBTSerializationHandler<FoodComponent> 
	NBT_HANDLER = NBTSerializationHandler
					.builder(FoodComponent.class)
					.beginComponent(COMPONENT_KEY.toString())
					.intTag("nutrition", FoodComponent::getNutrition, FoodComponent::setNutrition, 0)
					.floatTag("saturation", FoodComponent::getSaturation, FoodComponent::setSaturation, 0)
					.boolTag("can_always_eat", FoodComponent::isAlwaysEatable, FoodComponent::setAlwaysEatable, false)
					.endComponent()
					.build();
	
	int getNutrition();
	
	void setNutrition(int nutrition);
	
	float getSaturation();
	
	void setSaturation(float saturation);
	
	boolean isAlwaysEatable();
	
	void setAlwaysEatable(boolean eatable);
	
	FoodComponent clone();
	
	@Override
	default NBTSerializationHandler<FoodComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
