package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.enchantments.Enchantment;
import de.atlasmc.util.annotation.NotNull;

public interface EnchantmentComponent extends AbstractEnchantmentComponent {
	
	@NotNull
	static final NBTCodec<EnchantmentComponent>
	NBT_CODEC = NBTCodec
			.builder(EnchantmentComponent.class)
			.include(AbstractEnchantmentComponent.NBT_CODEC)
			.mapNamespacedToInt(ComponentType.ENCHANTMENTS.getNamespacedKey(), EnchantmentComponent::hasEnchants, EnchantmentComponent::getStoredEnchants, Enchantment::getEnchantment)
			.build();
	
	@Override
	EnchantmentComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
