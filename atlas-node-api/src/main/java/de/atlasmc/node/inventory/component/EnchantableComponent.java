package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public interface EnchantableComponent extends ItemComponent {
	
	public static final NBTCodec<EnchantableComponent>
	NBT_CODEC = NBTCodec
					.builder(EnchantableComponent.class)
					.intField(ComponentType.ENCHANTABLE.getNamespacedKey(), EnchantableComponent::getValue, EnchantableComponent::setValue)
					.build();
	
	public static final StreamCodec<EnchantableComponent>
	STREAM_CODEC = StreamCodec
					.builder(EnchantableComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.varInt(EnchantableComponent::getValue, EnchantableComponent::setValue)
					.build();
	
	int getValue();
	
	void setValue(int value);
	
	EnchantableComponent clone();
	
	@Override
	default NBTCodec<? extends EnchantableComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends EnchantableComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
