package de.atlasmc.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface DyedColorComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:dyed_color");
	
	public static final NBTSerializationHandler<DyedColorComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(DyedColorComponent.class)
					.color(COMPONENT_KEY, DyedColorComponent::getColor, DyedColorComponent::setColor)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	DyedColorComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends DyedColorComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
