package de.atlasmc.node.inventory.component;

import java.util.Map;

import de.atlasmc.node.map.MapIcon;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface MapDecorationComponent extends ItemComponent {
	
	public static final NBTCodec<MapDecorationComponent>
	NBT_HANDLER = NBTCodec
					.builder(MapDecorationComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.compoundMapString2Type(ComponentType.MAP_DECORATIONS.getNamespacedKey(), MapDecorationComponent::hasDecoration, MapDecorationComponent::getDecorations, MapIcon.NBT_HANDLER)
					.build();
	
	Map<String, MapIcon> getDecorations();
	
	boolean hasDecoration();
	
	void setDecoration(String key, MapIcon icon);
	
	MapIcon getDecoration(String name);
	
	void removeDecoration(String name);
	
	MapDecorationComponent clone();
	
	@Override
	default NBTCodec<? extends MapDecorationComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
