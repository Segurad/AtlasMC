package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface IntangibleProjectileComponent extends ItemComponent {
	
	public static final NBTCodec<IntangibleProjectileComponent>
	NBT_HANDLER = NBTCodec
					.builder(IntangibleProjectileComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.INTANGIBLE_PROJECTILE.getNamespacedKey())
					.endComponent()
					.build();
	
	IntangibleProjectileComponent clone();

	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
