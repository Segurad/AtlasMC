package de.atlasmc.inventory.component;

import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EnchantmentComponent extends AbstractEnchantmentComponent {
	
	static final NBTSerializationHandler<EnchantmentComponent>
	NBT_HANDLER = NBTSerializationHandler
			.builder(EnchantmentComponent.class)
			.include(AbstractEnchantmentComponent.NBT_HANDLER)
			.compoundMapNamespaced2Int(ComponentType.ENCHANTMENTS, EnchantmentComponent::hasEnchants, EnchantmentComponent::getStoredEnchants, Enchantment::getEnchantment)
			.build();
	
	EnchantmentComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
