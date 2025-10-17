package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface EnchantableComponent extends ItemComponent {
	
	public static final NBTCodec<EnchantableComponent>
	NBT_HANDLER = NBTCodec
					.builder(EnchantableComponent.class)
					.intField(ComponentType.ENCHANTABLE.getNamespacedKey(), EnchantableComponent::getValue, EnchantableComponent::setValue)
					.build();
	
	int getValue();
	
	void setValue(int value);
	
	EnchantableComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
