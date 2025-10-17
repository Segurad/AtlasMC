package de.atlasmc.node.inventory.component;

import de.atlasmc.node.Nameable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface CustomNameComponent extends ItemComponent, Nameable {
	
	public static final NBTCodec<CustomNameComponent>
	NBT_HANDLER = NBTCodec
					.builder(CustomNameComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.chat(ComponentType.CUSTOM_NAME.getNamespacedKey(), CustomNameComponent::getCustomName, CustomNameComponent::setCustomName)
					.build();
	
	CustomNameComponent clone();
	
	@Override
	default NBTCodec<? extends CustomNameComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
