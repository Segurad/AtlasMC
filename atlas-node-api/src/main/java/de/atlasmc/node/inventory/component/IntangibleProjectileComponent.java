package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface IntangibleProjectileComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<IntangibleProjectileComponent>
	NBT_CODEC = NBTCodec
					.builder(IntangibleProjectileComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.INTANGIBLE_PROJECTILE.getNamespacedKey())
					.endComponent()
					.build();
	
	@Override
	IntangibleProjectileComponent clone();

	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
