package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.enchantments.Enchantment;

public interface StoredEnchantmentComponent extends AbstractEnchantmentComponent {
	
	static final NBTCodec<StoredEnchantmentComponent>
	NBT_HANDLER = NBTCodec
			.builder(StoredEnchantmentComponent.class)
			.include(AbstractEnchantmentComponent.NBT_CODEC)
			.mapNamespacedToInt(ComponentType.STORED_ENCHANTMENTS.getNamespacedKey(), StoredEnchantmentComponent::hasEnchants, StoredEnchantmentComponent::getStoredEnchants, Enchantment::getEnchantment)
			.build();
	
	StoredEnchantmentComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
