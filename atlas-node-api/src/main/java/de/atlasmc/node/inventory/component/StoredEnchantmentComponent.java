package de.atlasmc.node.inventory.component;

import de.atlasmc.node.enchantments.Enchantment;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface StoredEnchantmentComponent extends AbstractEnchantmentComponent {
	
	static final NBTSerializationHandler<StoredEnchantmentComponent>
	NBT_HANDLER = NBTSerializationHandler
			.builder(StoredEnchantmentComponent.class)
			.include(AbstractEnchantmentComponent.NBT_HANDLER)
			.compoundMapNamespaced2Int(ComponentType.STORED_ENCHANTMENTS.getNamespacedKey(), StoredEnchantmentComponent::hasEnchants, StoredEnchantmentComponent::getStoredEnchants, Enchantment::getEnchantment)
			.build();
	
	StoredEnchantmentComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
