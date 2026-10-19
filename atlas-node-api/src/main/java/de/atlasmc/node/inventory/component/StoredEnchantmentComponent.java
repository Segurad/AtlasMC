package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.MapNamespacedToIntCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.CodecCollectionFieldCodec;
import de.atlasmc.node.enchantments.Enchantment;
import de.atlasmc.util.annotation.NotNull;

public interface StoredEnchantmentComponent extends AbstractEnchantmentComponent {
	
	@NotNull
	static final NBTCodec<StoredEnchantmentComponent>
	NBT_CODEC = CodecCollectionFieldCodec
			.builder(StoredEnchantmentComponent.class, new MapNamespacedToIntCodec<>(Enchantment::getEnchantment))
			.include(AbstractEnchantmentComponent.NBT_CODEC)
			.hasData(StoredEnchantmentComponent::hasEnchants)
			.getter(StoredEnchantmentComponent::getStoredEnchants)
			.build();
	
	@Override
	StoredEnchantmentComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
