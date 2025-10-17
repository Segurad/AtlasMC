package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface UnbreakableComponent extends ItemComponent {
	
	public static final NBTCodec<UnbreakableComponent>
	NBT_HANDLER = NBTCodec
					.builder(UnbreakableComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.UNBREAKABLE.getNamespacedKey())
					.endComponent()
					.build();

	UnbreakableComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
