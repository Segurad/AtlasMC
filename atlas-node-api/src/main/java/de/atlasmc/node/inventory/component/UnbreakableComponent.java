package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface UnbreakableComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<UnbreakableComponent>
	NBT_CODEC = NBTCodec
					.builder(UnbreakableComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.UNBREAKABLE.getNamespacedKey())
					.endComponent()
					.build();

	@Override
	UnbreakableComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
