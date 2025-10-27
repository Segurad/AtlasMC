package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface EnchantmentGlintOverrideComponent extends ItemComponent {

	public static final NBTCodec<EnchantmentGlintOverrideComponent>
	NBT_CODEC = NBTCodec
					.builder(EnchantmentGlintOverrideComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.boolField(ComponentType.ENCHANTMENT_GLINT_OVERRIDE.getNamespacedKey(), EnchantmentGlintOverrideComponent::hasGlint, EnchantmentGlintOverrideComponent::setGlint)
					.build();
	
	public static final StreamCodec<EnchantmentGlintOverrideComponent>
	STREAM_CODEC = StreamCodec
					.builder(EnchantmentGlintOverrideComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.booleanValue(EnchantmentGlintOverrideComponent::hasGlint, EnchantmentGlintOverrideComponent::setGlint)
					.build();
	
	boolean hasGlint();
	
	void setGlint(boolean glint);
	
	EnchantmentGlintOverrideComponent clone();
	
	@Override
	default NBTCodec<? extends EnchantmentGlintOverrideComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends EnchantmentGlintOverrideComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
