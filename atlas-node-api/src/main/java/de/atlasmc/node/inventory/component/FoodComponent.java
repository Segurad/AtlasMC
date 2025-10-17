package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface FoodComponent extends ItemComponent {

	public static final NBTCodec<FoodComponent> 
	NBT_HANDLER = NBTCodec
					.builder(FoodComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.FOOD.getNamespacedKey())
					.intField("nutrition", FoodComponent::getNutrition, FoodComponent::setNutrition, 0)
					.floatField("saturation", FoodComponent::getSaturation, FoodComponent::setSaturation, 0)
					.boolField("can_always_eat", FoodComponent::isAlwaysEatable, FoodComponent::setAlwaysEatable, false)
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
	default NBTCodec<FoodComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
