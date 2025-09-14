package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface GliderComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<GliderComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(GliderComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.GLIDER.getNamespacedKey())
					.endComponent()
					.build();
	
	GliderComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends GliderComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
