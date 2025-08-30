package de.atlasmc.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface DyedColorComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<DyedColorComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(DyedColorComponent.class)
					.color(ComponentType.DYED_COLOR.getNamespacedKey(), DyedColorComponent::getColor, DyedColorComponent::setColor)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	DyedColorComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends DyedColorComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
