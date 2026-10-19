package de.atlasmc.node.inventory.component;

import java.util.Map;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.CodecCollectionFieldCodec;
import de.atlasmc.nbt.codec.MapFieldNameToCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.map.MapIcon;
import de.atlasmc.util.annotation.NotNull;

public interface MapDecorationComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<MapDecorationComponent>
	NBT_CODEC = CodecCollectionFieldCodec
				.builder(MapDecorationComponent.class, new MapFieldNameToCodec<>(MapIcon.NBT_CODEC))
				.include(ItemComponent.NBT_CODEC)
				.hasData(MapDecorationComponent::hasDecoration)
				.getter(MapDecorationComponent::getDecorations)
				.build();
	
	@NotNull
	public static final StreamCodec<MapDecorationComponent>
	STREAM_CODEC = StreamCodec
					.builder(MapDecorationComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(NBT_CODEC)
					.build();
	
	Map<String, MapIcon> getDecorations();
	
	boolean hasDecoration();
	
	void setDecoration(String key, MapIcon icon);
	
	MapIcon getDecoration(String name);
	
	void removeDecoration(String name);
	
	@Override
	MapDecorationComponent clone();
	
	@Override
	default NBTCodec<? extends MapDecorationComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
