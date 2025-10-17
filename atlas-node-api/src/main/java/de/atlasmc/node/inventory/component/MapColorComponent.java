package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface MapColorComponent extends ItemComponent {

	public static final NBTCodec<MapColorComponent>
	NBT_HANDLER = NBTCodec
					.builder(MapColorComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.color(ComponentType.MAP_COLOR.getNamespacedKey(), MapColorComponent::getColor, MapColorComponent::setColor)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	MapColorComponent clone();
	
	@Override
	default NBTCodec<? extends MapColorComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
