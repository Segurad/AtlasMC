package de.atlasmc.inventory.component;

import java.util.Map;

import de.atlasmc.map.MapIcon;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface MapDecorationComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<MapDecorationComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(MapDecorationComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.compoundMapString2Type(ComponentType.MAP_DECORATIONS, MapDecorationComponent::hasDecoration, MapDecorationComponent::getDecorations, MapIcon.NBT_HANDLER)
					.build();
	
	Map<String, MapIcon> getDecorations();
	
	boolean hasDecoration();
	
	void setDecoration(String key, MapIcon icon);
	
	MapIcon getDecoration(String name);
	
	void removeDecoration(String name);
	
	MapDecorationComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends MapDecorationComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
