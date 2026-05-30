package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface MapIDComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<MapIDComponent>
	NBT_HANDLER = NBTCodec
					.builder(MapIDComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.intField(ComponentType.MAP_ID.getNamespacedKey(), MapIDComponent::getMapID, MapIDComponent::setMapID)
					.build();
	
	int getMapID();
	
	void setMapID(int id);
	
	@Override
	MapIDComponent clone();
	
	@Override
	default NBTCodec<? extends MapIDComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
