package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public interface MapColorComponent extends ItemComponent {

	public static final NBTCodec<MapColorComponent>
	NBT_CODEC = NBTCodec
					.builder(MapColorComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.MAP_COLOR.getNamespacedKey(), MapColorComponent::getColor, MapColorComponent::setColor, Color.NBT_CODEC)
					.build();
	
	public static final StreamCodec<MapColorComponent>
	STREAM_CODEC = StreamCodec
					.builder(MapColorComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(MapColorComponent::getColor, MapColorComponent::setColor, Color.STREAM_CODEC)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	MapColorComponent clone();
	
	@Override
	default NBTCodec<? extends MapColorComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
