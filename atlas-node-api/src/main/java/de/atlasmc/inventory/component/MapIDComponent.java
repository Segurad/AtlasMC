package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface MapIDComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<MapIDComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(MapIDComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.intField(ComponentType.MAP_ID.getNamespacedKey(), MapIDComponent::getMapID, MapIDComponent::setMapID)
					.build();
	
	int getMapID();
	
	void setMapID(int id);
	
	MapIDComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends MapIDComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
