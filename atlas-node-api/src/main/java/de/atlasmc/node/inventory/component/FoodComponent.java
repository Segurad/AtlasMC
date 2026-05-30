package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface FoodComponent extends ItemComponent {

	@NotNull
	public static final NBTCodec<FoodComponent> 
	NBT_CODEC = NBTCodec
					.builder(FoodComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.FOOD.getNamespacedKey())
					.intField("nutrition", FoodComponent::getNutrition, FoodComponent::setNutrition, 0)
					.floatField("saturation", FoodComponent::getSaturation, FoodComponent::setSaturation, 0)
					.boolField("can_always_eat", FoodComponent::isAlwaysEatable, FoodComponent::setAlwaysEatable, false)
					.endComponent()
					.build();
	
	@NotNull
	public static final StreamCodec<FoodComponent>
	STREAM_CODEC = StreamCodec
					.builder(FoodComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.varInt(FoodComponent::getNutrition, FoodComponent::setNutrition)
					.floatValue(FoodComponent::getSaturation, FoodComponent::setSaturation)
					.booleanValue(FoodComponent::isAlwaysEatable, FoodComponent::setAlwaysEatable)
					.build();
	
	int getNutrition();
	
	void setNutrition(int nutrition);
	
	float getSaturation();
	
	void setSaturation(float saturation);
	
	boolean isAlwaysEatable();
	
	void setAlwaysEatable(boolean eatable);
	
	@Override
	FoodComponent clone();
	
	@Override
	default NBTCodec<? extends FoodComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends FoodComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
