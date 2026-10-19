package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecCollectionFieldCodec;
import de.atlasmc.nbt.codec.MapNamespacedToIntCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.enchantments.Enchantment;
import de.atlasmc.util.annotation.NotNull;

public interface EnchantmentComponent extends AbstractEnchantmentComponent {
	
	@NotNull
	static final NBTCodec<EnchantmentComponent>
	NBT_CODEC = CodecCollectionFieldCodec
				.builder(EnchantmentComponent.class, new MapNamespacedToIntCodec<>(Enchantment::getEnchantment))
				.include(AbstractEnchantmentComponent.NBT_CODEC)
				.hasData(EnchantmentComponent::hasEnchants)
				.getter(EnchantmentComponent::getStoredEnchants)
				.build();
	
	@Override
	EnchantmentComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
