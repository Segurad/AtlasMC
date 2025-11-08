package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.nbt.codec.NBTCodec;

public interface MapColorComponent extends ItemComponent {

	public static final NBTCodec<MapColorComponent>
	NBT_HANDLER = NBTCodec
					.builder(MapColorComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.MAP_COLOR.getNamespacedKey(), MapColorComponent::getColor, MapColorComponent::setColor, Color.NBT_CODEC)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	MapColorComponent clone();
	
	@Override
	default NBTCodec<? extends MapColorComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
