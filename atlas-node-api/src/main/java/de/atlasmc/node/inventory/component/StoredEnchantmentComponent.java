package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.enchantments.Enchantment;
import de.atlasmc.util.annotation.NotNull;

public interface StoredEnchantmentComponent extends AbstractEnchantmentComponent {
	
	@NotNull
	static final NBTCodec<StoredEnchantmentComponent>
	NBT_CODEC = NBTCodec
			.builder(StoredEnchantmentComponent.class)
			.include(AbstractEnchantmentComponent.NBT_CODEC)
			.mapNamespacedToInt(ComponentType.STORED_ENCHANTMENTS.getNamespacedKey(), StoredEnchantmentComponent::hasEnchants, StoredEnchantmentComponent::getStoredEnchants, Enchantment::getEnchantment)
			.build();
	
	@Override
	StoredEnchantmentComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
