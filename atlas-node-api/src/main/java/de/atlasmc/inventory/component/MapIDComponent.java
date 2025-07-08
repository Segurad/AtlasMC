package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface MapIDComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:map_id");
	
	public static final NBTSerializationHandler<MapIDComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(MapIDComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.intField(COMPONENT_KEY, MapIDComponent::getMapID, MapIDComponent::setMapID)
					.build();
	
	int getMapID();
	
	void setMapID(int id);
	
	MapIDComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends MapIDComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
