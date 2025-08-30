package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface UnbreakableComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<UnbreakableComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(UnbreakableComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.UNBREAKABLE.getNamespacedKey())
					.endComponent()
					.build();

	UnbreakableComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
