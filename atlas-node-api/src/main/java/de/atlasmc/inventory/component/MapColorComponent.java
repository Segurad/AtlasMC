package de.atlasmc.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface MapColorComponent extends ItemComponent {

	public static final NBTSerializationHandler<MapColorComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(MapColorComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.color(ComponentType.MAP_COLOR, MapColorComponent::getColor, MapColorComponent::setColor)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	MapColorComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends MapColorComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
