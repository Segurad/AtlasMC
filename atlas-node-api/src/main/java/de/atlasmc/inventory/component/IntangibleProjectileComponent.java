package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface IntangibleProjectileComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<IntangibleProjectileComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(IntangibleProjectileComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.INTANGIBLE_PROJECTILE.getNamespacedKey())
					.endComponent()
					.build();
	
	IntangibleProjectileComponent clone();

	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
