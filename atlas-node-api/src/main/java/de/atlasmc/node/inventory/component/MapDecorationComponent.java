package de.atlasmc.node.inventory.component;

import java.util.Map;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.map.MapIcon;

public interface MapDecorationComponent extends ItemComponent {
	
	public static final NBTCodec<MapDecorationComponent>
	NBT_CODEC = NBTCodec
					.builder(MapDecorationComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.mapFieldNameToCodec(ComponentType.MAP_DECORATIONS.getNamespacedKey(), MapDecorationComponent::hasDecoration, MapDecorationComponent::getDecorations, MapIcon.NBT_CODEC)
					.build();
	
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
	
	MapDecorationComponent clone();
	
	@Override
	default NBTCodec<? extends MapDecorationComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
