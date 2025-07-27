package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface GliderComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:glider");
	
	public static final NBTSerializationHandler<GliderComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(GliderComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(COMPONENT_KEY)
					.endComponent()
					.build();
	
	GliderComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends GliderComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
