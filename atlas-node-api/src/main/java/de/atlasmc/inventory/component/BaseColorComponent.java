package de.atlasmc.inventory.component;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BaseColorComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:base_color");
	
	public static final NBTSerializationHandler<BaseColorComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BaseColorComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.enumStringField(COMPONENT_KEY, BaseColorComponent::getColor, BaseColorComponent::setColor, DyeColor::getByName, null)
					.build();
	
	BaseColorComponent clone();
	
	DyeColor getColor();
	
	void setColor(DyeColor color);
	
	@Override
	default NBTSerializationHandler<? extends BaseColorComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
