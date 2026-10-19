package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface MapColorComponent extends ItemComponent {

	@NotNull
	public static final NBTCodec<MapColorComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(MapColorComponent.class, Color.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(MapColorComponent::getColor)
				.setter(MapColorComponent::setColor)
				.build();
	
	public static final StreamCodec<MapColorComponent>
	STREAM_CODEC = StreamCodec
					.builder(MapColorComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(MapColorComponent::getColor, MapColorComponent::setColor, Color.STREAM_CODEC)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	@Override
	MapColorComponent clone();
	
	@Override
	default NBTCodec<? extends MapColorComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
