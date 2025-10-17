package de.atlasmc.node.inventory.component;

import de.atlasmc.node.enchantments.Enchantment;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface EnchantmentComponent extends AbstractEnchantmentComponent {
	
	static final NBTCodec<EnchantmentComponent>
	NBT_HANDLER = NBTCodec
			.builder(EnchantmentComponent.class)
			.include(AbstractEnchantmentComponent.NBT_HANDLER)
			.compoundMapNamespaced2Int(ComponentType.ENCHANTMENTS.getNamespacedKey(), EnchantmentComponent::hasEnchants, EnchantmentComponent::getStoredEnchants, Enchantment::getEnchantment)
			.build();
	
	EnchantmentComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
