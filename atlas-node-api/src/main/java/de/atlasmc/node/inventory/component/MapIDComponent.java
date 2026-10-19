package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.PrimitiveFieldCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface MapIDComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<MapIDComponent>
	NBT_CODEC = PrimitiveFieldCodecs
				.buildInt(MapIDComponent.class)
				.include(ItemComponent.NBT_CODEC)
				.getter(MapIDComponent::getMapID)
				.setter(MapIDComponent::setMapID)
				.build();
	
	int getMapID();
	
	void setMapID(int id);
	
	@Override
	MapIDComponent clone();
	
	@Override
	default NBTCodec<? extends MapIDComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
